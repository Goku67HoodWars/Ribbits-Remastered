package com.yungnickyoung.minecraft.ribbits.neoforge;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity;
import com.yungnickyoung.minecraft.ribbits.module.EntityTypeModule;
import com.yungnickyoung.minecraft.ribbits.neoforge.module.EntityDataSerializerModuleNeoForge;
import com.yungnickyoung.minecraft.ribbits.network.payload.RequestSupporterHatStatePayload;
import com.yungnickyoung.minecraft.ribbits.player.PlayerInstrumentTracker;
import com.yungnickyoung.minecraft.ribbits.registry.DeferredRegistry;
import com.yungnickyoung.minecraft.ribbits.registry.RibbitsRegistries;
import com.yungnickyoung.minecraft.ribbits.supporters.SupportersListServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.List;
import java.util.UUID;

@Mod(value = RibbitsCommon.MOD_ID)
public class RibbitsNeoForge {

    public RibbitsNeoForge(IEventBus eventBus, ModContainer container) {
        // Line up every module's static entries before the register event drains them.
        RibbitsRegistries.bootstrap();

        eventBus.addListener(RibbitsNeoForge::onRegister);
        eventBus.addListener(RibbitsNeoForge::onEntityAttributeCreation);
        // Registries are only populated once RegisterEvent has run for each of them, so the
        // post-registration data init (instruments/professions/umbrellas, config, network) runs here.
        eventBus.addListener((FMLCommonSetupEvent event) -> event.enqueueWork(RibbitsCommon::init));

        EntityDataSerializerModuleNeoForge.DATA_SERIALIZERS.register(eventBus);

        NeoForge.EVENT_BUS.addListener(RibbitsNeoForge::onServerTickStart);
        NeoForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedInEvent e) -> {
            if (!(e.getEntity() instanceof ServerPlayer serverPlayer)) return;
            List<UUID> playersWithSupporterHat = SupportersListServer.getPlayersWithSupporterHat().stream().toList();
            PacketDistributor.sendToPlayer(serverPlayer, new RequestSupporterHatStatePayload(playersWithSupporterHat));
        });
    }

    private static void onRegister(RegisterEvent event) {
        for (DeferredRegistry<?> registry : RibbitsRegistries.all()) {
            register(event, registry);
        }
    }

    /**
     * Captures the registry's element type so the entries can be handed to the event, which ignores
     * everything whose registry key does not match the one currently being filled.
     */
    private static <T> void register(RegisterEvent event, DeferredRegistry<T> registry) {
        registry.forEach((id, value) -> event.register(registry.registryKey(), id, value::get));
    }

    /**
     * Attributes cannot be registered imperatively on NeoForge; they are contributed through this
     * event. Only one living entity exists (the Ribbit), so it is wired directly rather than routed
     * through the platform helper.
     */
    private static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(EntityTypeModule.RIBBIT.get(), RibbitEntity.createRibbitAttributes().build());
    }

    private static void onServerTickStart(ServerTickEvent.Pre event) {
        PlayerInstrumentTracker.onServerTick();
    }
}
