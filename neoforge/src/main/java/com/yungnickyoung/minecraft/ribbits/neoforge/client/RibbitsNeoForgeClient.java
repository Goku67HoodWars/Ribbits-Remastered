package com.yungnickyoung.minecraft.ribbits.neoforge.client;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.client.RibbitsCommonClient;
import com.yungnickyoung.minecraft.ribbits.client.model.SupporterHatModel;
import com.yungnickyoung.minecraft.ribbits.client.particle.RibbitSpellParticle;
import com.yungnickyoung.minecraft.ribbits.client.render.RibbitRenderer;
import com.yungnickyoung.minecraft.ribbits.client.screen.RibbitsConfigScreen;
import com.yungnickyoung.minecraft.ribbits.module.EntityTypeModule;
import com.yungnickyoung.minecraft.ribbits.module.ParticleTypeModule;
import com.yungnickyoung.minecraft.ribbits.network.ClientNetworkHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = RibbitsCommon.MOD_ID, value = Dist.CLIENT)
public class RibbitsNeoForgeClient {

    @SubscribeEvent
    private static void clientSetup(final FMLClientSetupEvent event) {
        RibbitsCommonClient.init();
        var modContainer = event.getContainer();
        modContainer.registerExtensionPoint(
                IConfigScreenFactory.class,
                (container, parent) -> RibbitsConfigScreen.create(parent)
        );

        // Game-bus hooks matching the Fabric client (ClientEntityEvents.ENTITY_LOAD +
        // ClientPlayConnectionEvents.DISCONNECT).
        NeoForge.EVENT_BUS.addListener((EntityJoinLevelEvent e) -> {
            if (e.getLevel().isClientSide()) {
                ClientNetworkHandler.onEntityLoad(e.getEntity());
            }
        });
        NeoForge.EVENT_BUS.addListener((ClientPlayerNetworkEvent.LoggingOut e) ->
                ClientNetworkHandler.clearPendingActions());

        // Block render layers are no longer assigned by hand: since 26.2 the renderer picks
        // the layer per quad from the model's material.
    }

    @SubscribeEvent
    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeModule.RIBBIT.get(), RibbitRenderer::new);
    }

    @SubscribeEvent
    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SupporterHatModel.LAYER_LOCATION, SupporterHatModel::getTexturedModelData);
    }

    @SubscribeEvent
    private static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleTypeModule.SPELL.get(), RibbitSpellParticle.Factory::new);
    }
}
