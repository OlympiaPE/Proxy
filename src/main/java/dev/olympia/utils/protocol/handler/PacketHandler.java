package dev.olympia.utils.protocol.handler;

import dev.olympia.events.packets.PacketReceiveEvent;
import dev.olympia.events.packets.PacketSendEvent;
import dev.olympia.session.PlayerSession;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.network.protocol.Signals;
import dev.waterdog.waterdogpe.network.protocol.handler.PluginPacketHandler;
import org.cloudburstmc.protocol.bedrock.PacketDirection;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.common.PacketSignal;

public class PacketHandler implements PluginPacketHandler {
    protected PlayerSession session;
    public PacketHandler(PlayerSession session)
    {
        this.session = session;
    }

    @Override
    public PacketSignal handlePacket(BedrockPacket packet, PacketDirection direction) {
        if(direction.equals(PacketDirection.SERVER_BOUND)) {
            PacketReceiveEvent event = new PacketReceiveEvent(packet, session);
            ProxyServer.getInstance().getEventManager().callEvent(event);
            if(event.isCancelled()) {
                return Signals.CANCEL;
            }
        } else if (direction.equals(PacketDirection.CLIENT_BOUND)) {
            PacketSendEvent event = new PacketSendEvent(packet, session);
            ProxyServer.getInstance().getEventManager().callEvent(event);
            if(event.isCancelled()) {
                return Signals.CANCEL;
            }
        }
        return PacketSignal.UNHANDLED;
    }
}
