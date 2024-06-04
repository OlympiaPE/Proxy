package dev.olympia.commands.global.servers;

import dev.olympia.session.PlayerSession;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;

public class BuildCommand extends BaseCommand {
    public BuildCommand() {
        super("build", CommandSettings.builder()
                .setDescription("Aller sur le serveur build")
                .build());
    }

    @Override
    public void prepare() {
        setOnlyPlayer(true);
    }

    @Override
    public void onSessionRun(PlayerSession session, String[] args) {
        String currentServer = session.getPlayer().getServerInfo().getServerName();
        if(currentServer.equalsIgnoreCase("build")) {
            session.getPlayer().sendMessage(GlobalConstants.PREFIX + "§cVous êtes déjà sur le serveur build.");
            return;
        }

        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo("build");
        session.getPlayer().connect(serverInfo);
    }
}
