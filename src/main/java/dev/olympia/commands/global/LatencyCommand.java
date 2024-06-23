package dev.olympia.commands.staff.punishment;

import dev.olympia.session.PlayerSession;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.olympia.utils.protocol.commands.arguments.TargetArgument;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

public class LatencyCommand extends BaseCommand {
    public LatencyCommand() {
        super("latency", CommandSettings.builder()
                .setDescription("Voir le temps de latence du joueur.")
                .setAliases("ping")
                .build());
    }

    @Override
    public void prepare() {
        this.setOnlyPlayer(true);
        this.registerArgument(0, new TargetArgument("target", true));
    }

    @Override
    public void onSessionRun(PlayerSession session, String[] args) {
        if(args.length >= 1) {
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if(target == null) {
                session.getPlayer().sendMessage(GlobalConstants.PREFIX + "§cLe joueur que vous avez saisi n'est pas connecté!");
                return;
            }

            session.getPlayer().sendMessage(GlobalConstants.PREFIX + "§e" + target.getName() + "§a a actuellement §e" + target.getPing() + "ms§a.");
            session.addSound("note.harp", 0.5f, 1f);
            return;
        }
        session.getPlayer().sendMessage(GlobalConstants.PREFIX + "§aVous avez actuellement §e" + session.getPlayer().getPing() + "ms§a.");
        session.addSound("note.harp", 0.5f, 1f);
    }
}
