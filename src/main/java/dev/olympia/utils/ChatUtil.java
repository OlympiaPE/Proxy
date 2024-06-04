package dev.olympia.utils;

import dev.olympia.utils.constants.GlobalConstants;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

import java.util.function.Predicate;

public class ChatUtil {
    public static void broadcastMessage(String str, boolean prefixed) {
        str = TextFormat.colorize(str);
        String message = prefixed ? (GlobalConstants.PREFIX + str) : str;

        for(var server : ProxyServer.getInstance().getServers()) {
            server.getPlayers().forEach((p) -> {
                if(p.isConnected())
                    p.sendMessage(message);
            });
        }
        ProxyServer.getInstance().getLogger().info(str);
    }

    public static void broadcastMessage(String str) {
        broadcastMessage(str, true);
    }

    public static void broadcastMessage(String str, String permission, boolean prefixed) {
        str = TextFormat.colorize(str);
        String message = prefixed ? (GlobalConstants.PREFIX + str) : str;

        for(var server : ProxyServer.getInstance().getServers()) {
            server.getPlayers().forEach((p) -> {
                if(p.isConnected() && p.hasPermission(permission))
                    p.sendMessage(message);
            });
        }
        ProxyServer.getInstance().getLogger().info(str);
    }

    public static void broadcastMessage(String str, String permission) {
        broadcastMessage(str, permission, true);
    }

    public static void broadcastMessage(String str, Predicate<ProxiedPlayer> condition, boolean prefixed) {
        str = TextFormat.colorize(str);
        String message = prefixed ? (GlobalConstants.PREFIX + str) : str;

        for(var server : ProxyServer.getInstance().getServers()) {
            server.getPlayers().stream().filter(condition).forEach((p) -> {
                if(p.isConnected())
                    p.sendMessage(message);
            });
        }
        ProxyServer.getInstance().getLogger().info(TextFormat.clean(str, true));
    }

    public static void broadcastMessage(String str, Predicate<ProxiedPlayer> condition) {
        broadcastMessage(str, condition, true);
    }

    public static void broadcastTitle(String str) {
        str = TextFormat.colorize(str);
        String message = str;

        for(var server : ProxyServer.getInstance().getServers()) {
            server.getPlayers().forEach((p) -> {
                if(p.isConnected())
                    p.sendTitle("§a", message, 10, 10, 10);
            });
        }
        ProxyServer.getInstance().getLogger().info(TextFormat.clean(str, true));
    }

    public static void broadcastTip(String str) {
        str = TextFormat.colorize(str);
        String message = str;

        for(var server : ProxyServer.getInstance().getServers()) {
            server.getPlayers().forEach((p) -> {
                if(p.isConnected())
                    p.sendTip(message);
            });
        }
        ProxyServer.getInstance().getLogger().info(TextFormat.clean(str, true));
    }
}
