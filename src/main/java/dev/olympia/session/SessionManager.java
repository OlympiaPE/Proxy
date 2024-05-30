package dev.olympia.session;

import dev.waterdog.waterdogpe.player.ProxiedPlayer;

import java.util.HashMap;

public class SessionManager {
    protected static SessionManager instance;
    final private HashMap<ProxiedPlayer, PlayerSession> sessions = new HashMap<>();

    public static SessionManager getInstance() {
        return instance;
    }

    public static void setInstance(SessionManager instance) {
        SessionManager.instance = instance;
    }

    public SessionManager()
    {
        setInstance(this);
    }

    public PlayerSession getSession(ProxiedPlayer player)
    {
        return sessions.getOrDefault(player, null);
    }

    public void addSession(ProxiedPlayer player, PlayerSession session)
    {
        sessions.put(player, session);
    }

    public void removeSession(ProxiedPlayer player)
    {
        sessions.remove(player);
    }
}
