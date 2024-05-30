package dev.olympia.listeners;

import dev.olympia.events.packets.PacketReceiveEvent;
import dev.olympia.events.packets.PacketSendEvent;
import dev.olympia.handlers.InboundGamePacketHandler;
import dev.olympia.handlers.OutboundGamePacketHandler;

public class PacketListener {
    public static void onPacketReceive(PacketReceiveEvent event)
    {
        InboundGamePacketHandler handler = event.getSession().getInboundGamePacketHandler();
        if(!handler.handle(event.getPacket())) {
            event.setCancelled();
        }
    }

    public static void onPacketSend(PacketSendEvent event)
    {
        OutboundGamePacketHandler handler = event.getSession().getOutboundGamePacketHandler();
        if(!handler.handle(event.getPacket())) {
            event.setCancelled();
        }
    }
}
