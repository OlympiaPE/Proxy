package dev.olympia.handlers;

import dev.olympia.session.PlayerSession;
import dev.olympia.session.types.Effect;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.MobEffectPacket;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;

public class OutboundGamePacketHandler {
    protected PlayerSession session;

    public OutboundGamePacketHandler(PlayerSession playerSession)
    {
        this.session = playerSession;
    }

    public boolean handle(BedrockPacket packet) {
        if(packet instanceof MobEffectPacket pk) return handle(pk);
        if(packet instanceof TextPacket pk) return handle(pk);
        return true;
    }

    public boolean handle(MobEffectPacket packet)
    {
        if(packet.getRuntimeEntityId() == session.getEntityId()) {
            if(packet.getEvent().equals(MobEffectPacket.Event.REMOVE)) {
                session.getEffects().remove(packet.getEffectId());
            } else if(packet.getEvent().equals(MobEffectPacket.Event.ADD) || packet.getEvent().equals(MobEffectPacket.Event.MODIFY)) {
                session.getEffects().put(packet.getEffectId(), new Effect(packet.getEffectId(), packet.getAmplifier(), packet.getDuration(), packet.isParticles()));
            }
        }
        return true;
    }

    public boolean handle(TextPacket packet)
    {
        if(
                packet.getMessage().equalsIgnoreCase("%multiplayer.player.joined") ||
                packet.getMessage().equalsIgnoreCase("%multiplayer.player.leaved")
        ) return false;
        return true;
    }
}
