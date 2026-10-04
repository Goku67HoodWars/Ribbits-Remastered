package com.yungnickyoung.minecraft.ribbits.forge.client;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.client.RibbitsCommonClient;
import com.yungnickyoung.minecraft.ribbits.client.model.SupporterHatModel;
import com.yungnickyoung.minecraft.ribbits.client.particle.RibbitSpellParticle;
import com.yungnickyoung.minecraft.ribbits.client.render.RibbitRenderer;
import com.yungnickyoung.minecraft.ribbits.client.screen.RibbitsConfigScreen;
import com.yungnickyoung.minecraft.ribbits.module.EntityTypeModule;
import com.yungnickyoung.minecraft.ribbits.module.ParticleTypeModule;
import com.yungnickyoung.minecraft.ribbits.network.ClientNetworkHandler;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class RibbitsForgeClient {

    public static void init(BusGroup modBus, ModContainer container) {
        container.registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (minecraft, parent) -> RibbitsConfigScreen.create(parent)));

        FMLClientSetupEvent.getBus(modBus).addListener(event -> RibbitsCommonClient.init());
        EntityRenderersEvent.RegisterRenderers.BUS.addListener(RibbitsForgeClient::registerRenderers);
        EntityRenderersEvent.RegisterLayerDefinitions.BUS.addListener(RibbitsForgeClient::registerLayers);
        RegisterParticleProvidersEvent.BUS.addListener(RibbitsForgeClient::registerParticleFactories);

        // Fabric wires these through ClientEntityEvents.ENTITY_LOAD / ClientPlayConnectionEvents.DISCONNECT.
        EntityJoinLevelEvent.BUS.addListener(event -> {
            if (event.getLevel().isClientSide()) {
                ClientNetworkHandler.onEntityLoad(event.getEntity());
            }
        });
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeModule.RIBBIT.get(), RibbitRenderer::new);
    }

    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SupporterHatModel.LAYER_LOCATION, SupporterHatModel::getTexturedModelData);
    }

    private static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleTypeModule.SPELL.get(), RibbitSpellParticle.Factory::new);
    }
}
