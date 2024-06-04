package dev.olympia.commands.global.servers;

import dev.olympia.session.PlayerSession;
import dev.olympia.utils.ChatUtil;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.constants.Permissions;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.olympia.utils.protocol.commands.arguments.EnumArgument;
import dev.olympia.utils.protocol.commands.arguments.StringArgument;
import dev.olympia.utils.protocol.commands.arguments.TargetArgument;
import dev.olympia.utils.protocol.commands.arguments.TextArgument;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import org.apache.logging.log4j.util.Strings;

import java.net.InetSocketAddress;
import java.util.Arrays;

public class LobbyCommand extends BaseCommand {
    public LobbyCommand() {
        super("lobby", CommandSettings.builder()
                .setDescription("Aller sur le serveur lobby")
                .build());
    }

    @Override
    public void prepare() {
        setOnlyPlayer(true);
    }

    @Override
    public void onSessionRun(PlayerSession session, String[] args) {
        String currentServer = session.getPlayer().getServerInfo().getServerName();
        if(currentServer.equalsIgnoreCase("lobby")) {
            session.getPlayer().sendMessage(GlobalConstants.PREFIX + "§cVous êtes déjà sur le serveur lobby.");
            return;
        }

        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo("lobby");
        session.getPlayer().connect(serverInfo);
    }
}
