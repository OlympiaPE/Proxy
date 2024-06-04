package dev.olympia.commands.staff.freeze;

import dev.olympia.session.PlayerSession;
import dev.olympia.session.SessionManager;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.constants.Permissions;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.olympia.utils.protocol.commands.arguments.TargetArgument;
import dev.olympia.utils.protocol.forms.SimpleForm;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

public class UnFreezeCommand extends BaseCommand {
    public UnFreezeCommand() {
        super("unfreeze", CommandSettings.builder()
                .setPermission(Permissions.UNFREEZE_COMMAND.name())
                .setDescription("Rendre mobile un joueur.")
                .build());
    }

    @Override
    public void prepare() {
        this.registerArgument(0, new TargetArgument("target", false));
    }

    @Override
    public void onRun(CommandSender commandSender, String[] args) {
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
        if(target == null) {
            commandSender.sendMessage(GlobalConstants.PREFIX + "§cLe joueur que vous avez saisi n'est pas connecté!");
            return;
        }

        PlayerSession session = SessionManager.getInstance().getSession(target);
        session.setImmobile(false);
        commandSender.sendMessage(GlobalConstants.PREFIX + "§aVous avez bien rendu mobile §e" + target.getName() + "§a.");
    }
}
