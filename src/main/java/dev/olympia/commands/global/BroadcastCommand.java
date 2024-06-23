package dev.olympia.commands.global;

import dev.olympia.utils.ChatUtil;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.constants.Permissions;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.olympia.utils.protocol.commands.arguments.EnumArgument;
import dev.olympia.utils.protocol.commands.arguments.StringArgument;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;

import java.util.Arrays;

public class BroadcastCommand extends BaseCommand {
    public BroadcastCommand() {
        super("broadcast", CommandSettings.builder()
                .setPermission(Permissions.BROADCAST_COMMAND.name())
                .setDescription("Envoyer un message à tous les joueurs")
                .setAliases("say")
                .build());
    }

    @Override
    public void prepare() {
        this.registerArgument(0, new EnumArgument("type", false, new String[]{"chat", "title", "tip"}));
        this.registerArgument(1, new StringArgument("content", false));
    }

    @Override
    public void onRun(CommandSender commandSender, String[] args) {
        String type = args[0];

        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        switch (type.toLowerCase()) {
            case "chat" -> ChatUtil.broadcastMessage(message);
            case "title" -> ChatUtil.broadcastTitle(message);
            case "tip" -> ChatUtil.broadcastTip(message);
            default -> {
                commandSender.sendMessage(GlobalConstants.PREFIX + "§cLe type que vous avez envoyé est incorrect.");
                return;
            }
        }

        commandSender.sendMessage(GlobalConstants.PREFIX + "§aVous avez bien envoyé le message à tout les serveurs.");
    }
}
