package dev.olympia.commands.global.servers;

import dev.olympia.session.PlayerSession;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;

public class KitMapCommand extends BaseCommand {
    public KitMapCommand() {
        super("kitmap", CommandSettings.builder()
                .setDescription("Aller sur le serveur kitmap")
                .build());
    }

    @Override
    public void prepare() {
        setOnlyPlayer(true);
    }

    @Override
    public void onSessionRun(PlayerSession session, String[] args) {
        String currentServer = session.getPlayer().getServerInfo().getServerName();
        if(currentServer.equalsIgnoreCase("kitmap")) {
            session.getPlayer().sendMessage(GlobalConstants.PREFIX + "§cVous êtes déjà sur le serveur kitmap.");
            return;
        }

        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo("kitmap");
        session.getPlayer().connect(serverInfo);
    }
}
