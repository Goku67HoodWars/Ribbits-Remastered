package com.yungnickyoung.minecraft.ribbits.forge.module;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.network.ClientNetworkHandler;
import com.yungnickyoung.minecraft.ribbits.network.ServerNetworkHandler;
import com.yungnickyoung.minecraft.ribbits.network.payload.RequestSupporterHatStatePayload;
import com.yungnickyoung.minecraft.ribbits.network.payload.RibbitStartMusicAllPayload;
import com.yungnickyoung.minecraft.ribbits.network.payload.RibbitStartMusicSinglePayload;
import com.yungnickyoung.minecraft.ribbits.network.payload.RibbitStopMusicSinglePayload;
import com.yungnickyoung.minecraft.ribbits.network.payload.StartHearingMaracaPayload;
import com.yungnickyoung.minecraft.ribbits.network.payload.StopHearingMaracaPayload;
import com.yungnickyoung.minecraft.ribbits.network.payload.ToggleSupporterHatPayloadC2S;
import com.yungnickyoung.minecraft.ribbits.network.payload.ToggleSupporterHatPayloadS2C;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;

/**
 * Forge routes every payload through one channel, declaring both directions up front.
 * <p>
 * The payload codecs are written against {@link net.minecraft.network.FriendlyByteBuf}, so they are
 * cast to the play protocol's registry-aware buffer. The clientbound handlers only ever run on a
 * client, so the client-only handler class is not loaded on a dedicated server.
 */
public class NetworkModuleForge {
    private static final int PROTOCOL_VERSION = 1;

    private static Channel<CustomPacketPayload> channel;

    public static void register() {
        channel = ChannelBuilder.named(RibbitsCommon.id("main"))
                .networkProtocolVersion(PROTOCOL_VERSION)
                .optional()
                .payloadChannel()
                .play()
                .clientbound(flow -> flow
                        .addMain(RibbitStartMusicSinglePayload.TYPE, RibbitStartMusicSinglePayload.STREAM_CODEC.cast(),
                                (payload, context) -> ClientNetworkHandler.handleStartMusicSingleS2C(payload))
                        .addMain(RibbitStopMusicSinglePayload.TYPE, RibbitStopMusicSinglePayload.STREAM_CODEC.cast(),
                                (payload, context) -> ClientNetworkHandler.handleStopMusicSingleS2C(payload))
                        .addMain(RibbitStartMusicAllPayload.TYPE, RibbitStartMusicAllPayload.STREAM_CODEC.cast(),
                                (payload, context) -> ClientNetworkHandler.handleStartMusicAllS2C(payload))
                        .addMain(StartHearingMaracaPayload.TYPE, StartHearingMaracaPayload.STREAM_CODEC.cast(),
                                (payload, context) -> ClientNetworkHandler.handleStartHearingMaracaS2C(payload))
                        .addMain(StopHearingMaracaPayload.TYPE, StopHearingMaracaPayload.STREAM_CODEC.cast(),
                                (payload, context) -> ClientNetworkHandler.handleStopHearingMaracaS2C(payload))
                        .addMain(RequestSupporterHatStatePayload.TYPE, RequestSupporterHatStatePayload.STREAM_CODEC.cast(),
                                (payload, context) -> ClientNetworkHandler.handleRequestSupporterHatStateS2C(payload))
                        .addMain(ToggleSupporterHatPayloadS2C.TYPE, ToggleSupporterHatPayloadS2C.STREAM_CODEC.cast(),
                                (payload, context) -> ClientNetworkHandler.handleToggleSupporterHatS2C(payload)))
                .serverbound()
                .addMain(ToggleSupporterHatPayloadC2S.TYPE, ToggleSupporterHatPayloadC2S.STREAM_CODEC.cast(),
                        (payload, context) -> ServerNetworkHandler.handleToggleSupporterHatC2S(payload))
                .build();
    }

    public static Channel<CustomPacketPayload> channel() {
        if (channel == null) {
            throw new IllegalStateException("Ribbits network channel was used before it was registered");
        }
        return channel;
    }

    public static <T extends CustomPacketPayload> void sendToServer(T payload) {
        channel().send(payload, PacketDistributor.SERVER.noArg());
    }
}
