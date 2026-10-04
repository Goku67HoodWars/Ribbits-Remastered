package com.yungnickyoung.minecraft.ribbits.forge;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity;
import com.yungnickyoung.minecraft.ribbits.forge.module.EntityDataSerializerModuleForge;
import com.yungnickyoung.minecraft.ribbits.forge.module.NetworkModuleForge;
import com.yungnickyoung.minecraft.ribbits.forge.supporters.SupporterEventsForge;
import com.yungnickyoung.minecraft.ribbits.module.EntityTypeModule;
import com.yungnickyoung.minecraft.ribbits.player.PlayerInstrumentTracker;
import com.yungnickyoung.minecraft.ribbits.registry.DeferredRegistry;
import com.yungnickyoung.minecraft.ribbits.registry.RibbitsRegistries;
import com.yungnickyoung.minecraft.ribbits.forge.client.RibbitsForgeClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;

@Mod(RibbitsCommon.MOD_ID)
public class RibbitsForge {

    public RibbitsForge(FMLJavaModLoadingContext context) {
        BusGroup modBus = context.getModBusGroup();

        RibbitsRegistries.bootstrap();

        RegisterEvent.getBus(modBus).addListener(RibbitsForge::onRegister);
        EntityAttributeCreationEvent.BUS.addListener(RibbitsForge::onEntityAttributeCreation);
        // Registries are only populated once RegisterEvent has run for each of them.
        FMLCommonSetupEvent.getBus(modBus).addListener(event -> event.enqueueWork(RibbitsCommon::init));

        EntityDataSerializerModuleForge.register(modBus);
        NetworkModuleForge.register();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            RibbitsForgeClient.init(modBus, context.getContainer());
        }

        TickEvent.ServerTickEvent.Pre.BUS.addListener(event -> PlayerInstrumentTracker.onServerTick());
        PlayerEvent.PlayerLoggedInEvent.BUS.addListener(SupporterEventsForge::onPlayerJoin);
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
        registry.forEach((id, value) -> event.register(registry.registryKey(), id, () -> value.get()));
    }

    private static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(EntityTypeModule.RIBBIT.get(), RibbitEntity.createRibbitAttributes().build());
    }
}
