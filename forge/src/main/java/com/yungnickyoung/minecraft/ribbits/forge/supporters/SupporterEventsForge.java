package com.yungnickyoung.minecraft.ribbits.forge.supporters;

import com.yungnickyoung.minecraft.ribbits.network.payload.RequestSupporterHatStatePayload;
import com.yungnickyoung.minecraft.ribbits.platform.PlatformHelper;
import com.yungnickyoung.minecraft.ribbits.supporters.SupportersListServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.List;
import java.util.UUID;

public class SupporterEventsForge {
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        // Get the server's current list of players with the supporter hat enabled
        List<UUID> playersWithSupporterHat = SupportersListServer.getPlayersWithSupporterHat().stream().toList();

        // Send the list of players with the supporter hat enabled to the new player, and request their own supporter hat state
        PlatformHelper.sendToPlayer(serverPlayer, new RequestSupporterHatStatePayload(playersWithSupporterHat));
    }
}
