package dev.olympia.listeners;

import dev.olympia.session.PlayerSession;
import dev.olympia.session.SessionManager;
import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectedEvent;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;

public class EventListener {
    public static void onLogin(PlayerLoginEvent event)
    {
        SessionManager.getInstance().addSession(event.getPlayer(), new PlayerSession(event.getPlayer()));
    }

    public static void onDisconnect(PlayerDisconnectedEvent event)
    {
        SessionManager.getInstance().removeSession(event.getPlayer());
    }
}
