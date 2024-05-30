package dev.olympia.utils.protocol;

import dev.waterdog.waterdogpe.network.protocol.updaters.ProtocolCodecUpdater;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketDefinition;
import org.cloudburstmc.protocol.bedrock.packet.*;

public class ProtocolUpdater implements ProtocolCodecUpdater {
    @Override
    public BedrockCodec.Builder updateCodec(BedrockCodec.Builder builder, BedrockCodec baseCodec) {
        BedrockPacketDefinition<PlaySoundPacket> playSoundPacketBedrockPacketDefinition = baseCodec.getPacketDefinition(PlaySoundPacket.class);
        builder.registerPacket(PlaySoundPacket::new, playSoundPacketBedrockPacketDefinition.getSerializer(), playSoundPacketBedrockPacketDefinition.getId(), playSoundPacketBedrockPacketDefinition.getRecipient());
        return builder;
    }
}
