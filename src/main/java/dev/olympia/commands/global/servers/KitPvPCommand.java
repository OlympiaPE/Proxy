package dev.olympia.commands.global.servers;

import dev.olympia.session.PlayerSession;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;

public class KitPvPCommand extends BaseCommand {
    public KitPvPCommand() {
        super("kitpvp", CommandSettings.builder()
                .setDescription("Aller sur le serveur kitpvp")
                .build());
    }

    @Override
    public void prepare() {
        setOnlyPlayer(true);
    }

    @Override
    public void onSessionRun(PlayerSession session, String[] args) {
        String currentServer = session.getPlayer().getServerInfo().getServerName();
        if(currentServer.equalsIgnoreCase("kitpvp")) {
            session.getPlayer().sendMessage(GlobalConstants.PREFIX + "§cVous êtes déjà sur le serveur kitpvp.");
            return;
        }

        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo("kitpvp");
        session.getPlayer().connect(serverInfo);
    }
}
