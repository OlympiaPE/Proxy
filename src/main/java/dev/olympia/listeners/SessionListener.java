package dev.olympia.listeners;

import dev.olympia.session.PlayerSession;
import dev.olympia.session.SessionManager;
import dev.waterdog.waterdogpe.event.defaults.ServerTransferEvent;

public class SessionListener {
    public static void onTransfer(ServerTransferEvent event)
    {
        PlayerSession session = SessionManager.getInstance().getSession(event.getPlayer());
        event.getTargetServer().getPlayers().forEach(player -> player.sendMessage("§f[§a+§f] §a" + session.getPlayer().getName()));
        event.getSourceServer().getPlayers().forEach(player -> player.sendMessage("§f[§c-§f] §c" + session.getPlayer().getName()));
    }
}
