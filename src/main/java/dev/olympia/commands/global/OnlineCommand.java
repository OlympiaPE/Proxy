package dev.olympia.commands.global;

import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.olympia.utils.protocol.commands.arguments.EnumArgument;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;

import java.util.ArrayList;

public class OnlineCommand extends BaseCommand {
    public OnlineCommand() {
        super("online", CommandSettings.builder()
                .setDescription("Liste des joueurs connectés.")
                .setAliases("list")
                .build());
    }

    @Override
    public void prepare() {
        this.registerArgument(0, new EnumArgument("type", false, new String[]{"KitMap", "KitPvP", "FFA", "Build", "All"}));
    }

    @Override
    public void onRun(CommandSender commandSender, String[] args) {
        String type = args[0].toLowerCase();

        if(type.equalsIgnoreCase("all")) {
            ArrayList<String> players = new ArrayList<>();
            for(var server : ProxyServer.getInstance().getServers()) {
                server.getPlayers().forEach((p) -> {
                    players.add(p.getName());
                });
            }


            String[] content = new String[players.size()];
            content = players.toArray(content);

            commandSender.sendMessage(GlobalConstants.PREFIX + "§aVoici la liste des joueurs connectés sur tout le réseau  §e(" + players.size() + ")§a:\n§7" + String.join(", ", content));
            return;
        }

        ServerInfo serverInfo = ProxyServer.getInstance().getServer(type);
        if(serverInfo == null) {
            commandSender.sendMessage(GlobalConstants.PREFIX + "§cImpossible de trouvé le serveur saisie.");
            return;
        }
        ArrayList<String> players = new ArrayList<>();
        serverInfo.getPlayers().forEach((p) -> players.add(p.getName()));

        String[] content = new String[players.size()];
        content = players.toArray(content);

        commandSender.sendMessage(GlobalConstants.PREFIX + "§aVoici la liste des joueurs connectés sur le serveur §e" + type + " (" + players.size() + ")§a:\n§7" + String.join(", ", content));
    }
}
