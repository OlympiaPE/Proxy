package dev.olympia.events.packets;

import dev.olympia.session.PlayerSession;
import dev.waterdog.waterdogpe.event.CancellableEvent;
import dev.waterdog.waterdogpe.event.Event;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;

public class PacketReceiveEvent extends Event implements CancellableEvent {
    protected BedrockPacket packet;
    protected PlayerSession session;

    public PacketReceiveEvent(BedrockPacket packet, PlayerSession session) {
        this.packet = packet;
        this.session = session;
    }

    public BedrockPacket getPacket()
    {
        return this.packet;
    }

    public PlayerSession getSession()
    {
        return this.session;
    }
}
