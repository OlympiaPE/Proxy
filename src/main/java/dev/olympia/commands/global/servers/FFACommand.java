package dev.olympia.commands.global.servers;

import dev.olympia.session.PlayerSession;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;

public class FFACommand extends BaseCommand {
    public FFACommand() {
        super("ffa", CommandSettings.builder()
                .setDescription("Aller sur le serveur ffa\"")
                .build());
    }

    @Override
    public void prepare() {
        setOnlyPlayer(true);
    }

    @Override
    public void onSessionRun(PlayerSession session, String[] args) {
        String currentServer = session.getPlayer().getServerInfo().getServerName();
        if(currentServer.equalsIgnoreCase("ffa\"")) {
            session.getPlayer().sendMessage(GlobalConstants.PREFIX + "§cVous êtes déjà sur le serveur ffa\".");
            return;
        }

        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo("ffa");
        session.getPlayer().connect(serverInfo);
    }
}
