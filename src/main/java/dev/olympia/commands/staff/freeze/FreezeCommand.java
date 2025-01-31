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

public class FreezeCommand extends BaseCommand {
    public FreezeCommand() {
        super("freeze", CommandSettings.builder()
                .setPermission(Permissions.FREEZE_COMMAND.name())
                .setDescription("Rendre immobile un joueur.")
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
        session.setImmobile(true);
        target.sendMessage("Vous avez été freeze par " + commandSender.getName() + ", veuillez vous rendre sur notre serveur discord!");
        /*SimpleForm form = new SimpleForm(1001);
        form.setTitle("Vous êtes freeze");
        form.setContent("Vous avez été freeze par " + commandSender.getName() + ", veuillez vous rendre sur notre serveur discord!");
        form.setCloseCallable(() -> {
            if(session.isImmobile())
                form.send(session);
        });
        form.send(session);*/
        commandSender.sendMessage(GlobalConstants.PREFIX + "§aVous avez bien rendu immobile §e" + target.getName() + "§a.");
    }
}
