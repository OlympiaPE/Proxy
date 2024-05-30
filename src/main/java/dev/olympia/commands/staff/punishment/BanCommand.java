package dev.olympia.commands.staff.punishment;

import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.constants.Permissions;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.olympia.utils.protocol.commands.arguments.EnumArgument;
import dev.olympia.utils.protocol.commands.arguments.StringArgument;
import dev.olympia.utils.protocol.commands.arguments.TargetArgument;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;

public class BanCommand extends BaseCommand {
    public BanCommand() {
        super("ban", CommandSettings.builder()
                .setPermission(Permissions.BAN_COMMAND.name())
                .setDescription("Suspendre de l'accès du serveur a un joueur.")
                .build());
    }

    @Override
    public void prepare() {
        this.registerArgument(0, new TargetArgument("target", false));
        this.registerArgument(1, new StringArgument("time", false));
        this.registerArgument(2, new EnumArgument("reason", false, new String[]{"Cheat", "Trash"}));
        this.registerArgument(3, new EnumArgument("server", false, new String[]{"KitMap", "KitPvP", "All"}));
    }

    @Override
    public void onRun(CommandSender commandSender, String[] args) {
        if(args.length < 4) {
            commandSender.sendMessage(invalidUsage);
            return;
        }

        String target = args[0];
        String time = args[1]; // Parse time
        String reason = args[2];
        String server = args[3];

        commandSender.sendMessage(GlobalConstants.PREFIX + "§aVous avez bien banni §e" + target + "§a pendant §e" + time + "§a pour §e" + reason + "§a sur " + (server.equalsIgnoreCase("all") ? "§etout les serveurs" : "le serveur §e" + server));
    }
}
