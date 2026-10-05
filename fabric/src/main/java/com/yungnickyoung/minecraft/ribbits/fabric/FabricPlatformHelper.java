package com.yungnickyoung.minecraft.ribbits.fabric;

import com.yungnickyoung.minecraft.ribbits.platform.IPlatformHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

import java.nio.file.Path;
import java.util.List;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public void setBlockAsFlammable(Block block, int igniteChance, int burnChance) {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        fireBlock.setFlammable(block, igniteChance, burnChance);
    }

    @Override
    public void addCompostableItem(ItemLike item, float chance) {
        // TODO(26.3): composting is now a data-driven Compostable component (ResolvableInt layers via
        // ContextIntProvider), not a float chance. Deferred — ribbit plants aren't compostable on Fabric yet.
    }

    @Override
    public MinecraftServer getCurrentServer() {
        return RibbitsFabric.getCurrentServer();
    }

    @Override
    public String getPlatformName() {
        return "fabric";
    }

    @Override
    public Path getConfigFolder() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public boolean isServer() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    @Override
    public <T extends CustomPacketPayload> void sendToPlayer(ServerPlayer player, T payload) {
        ServerPlayNetworking.send(player, payload);
    }

    @Override
    public <T extends CustomPacketPayload> void sendToPlayers(List<ServerPlayer> players, T payload) {
        for (ServerPlayer player : players) {
            ServerPlayNetworking.send(player, payload);
        }
    }

    @Override
    public <T extends CustomPacketPayload> void sendToServer(T payload) {
        ClientPlayNetworking.send(payload);
    }
}
