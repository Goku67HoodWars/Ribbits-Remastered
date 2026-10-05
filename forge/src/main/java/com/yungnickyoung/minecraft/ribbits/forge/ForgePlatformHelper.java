package com.yungnickyoung.minecraft.ribbits.forge;

import com.yungnickyoung.minecraft.ribbits.forge.module.NetworkModuleForge;
import com.yungnickyoung.minecraft.ribbits.platform.IPlatformHelper;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.nio.file.Path;
import java.util.List;

public class ForgePlatformHelper implements IPlatformHelper {
    @Override
    public void setBlockAsFlammable(Block block, int igniteChance, int burnChance) {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        fireBlock.setFlammable(block, igniteChance, burnChance);
    }

    @Override
    public void addCompostableItem(ItemLike item, float chance) {
        // TODO(26.3): ComposterBlock.COMPOSTABLES was removed; composting is now the Compostable data
        // component (ContextIntProvider layers). Deferred — ribbit plants aren't compostable on Forge yet.
    }

    @Override
    public MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    @Override
    public String getPlatformName() {
        return "forge";
    }

    @Override
    public Path getConfigFolder() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLEnvironment.production;
    }

    @Override
    public boolean isClient() {
        return FMLEnvironment.dist == Dist.CLIENT;
    }

    @Override
    public boolean isServer() {
        return FMLEnvironment.dist == Dist.DEDICATED_SERVER;
    }

    @Override
    public <T extends CustomPacketPayload> void sendToPlayer(ServerPlayer player, T payload) {
        NetworkModuleForge.channel().send(payload, PacketDistributor.PLAYER.with(player));
    }

    @Override
    public <T extends CustomPacketPayload> void sendToPlayers(List<ServerPlayer> players, T payload) {
        for (ServerPlayer player : players) {
            this.sendToPlayer(player, payload);
        }
    }

    @Override
    public <T extends CustomPacketPayload> void sendToServer(T payload) {
        NetworkModuleForge.sendToServer(payload);
    }
}
