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
import dev.olympia.utils.protocol.forms.SimpleForm;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import org.apache.logging.log4j.util.Strings;

import java.net.InetSocketAddress;
import java.util.Arrays;

public class ServersCommand extends BaseCommand {
    public ServersCommand() {
        super("servers", CommandSettings.builder()
                .setDescription("Choisir sur quel serveur vous voulez aller")
                .build());
    }

    @Override
    public void prepare() {
        setOnlyPlayer(true);
    }

    @Override
    public void onSessionRun(PlayerSession session, String[] args) {
        SimpleForm form = new SimpleForm(1000);
        form.setTitle("Serveur");
        form.setContent("Veuillez séléctionner sur quel serveur vous voulez allez.");
        form.addButton("KitMap", "kitmap");
        form.addButton("kitpvp", "kitpvp");
        form.addButton("Build", "build");
        form.addButton("FFA", "ffa");
        form.addButton("Lobby", "lobby");
        form.setCallable(() -> {
            ServerInfo currentServerInfo = session.getPlayer().getServerInfo();
            if(currentServerInfo.getServerName().equals(form.getResponse())) {
                session.getPlayer().sendMessage(GlobalConstants.PREFIX + "§cVous êtes déjà sur ce serveur!");
                return;
            }

            ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(form.getResponse().toString());
            session.getPlayer().connect(serverInfo);
        });
        form.send(session);
    }
}
