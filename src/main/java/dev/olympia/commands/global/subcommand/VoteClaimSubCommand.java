package dev.olympia.commands.global.subcommand;

import dev.olympia.Loader;
import dev.olympia.session.PlayerSession;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.protocol.commands.SubCommand;
import dev.waterdog.waterdogpe.utils.config.JsonConfig;

public class VoteClaimSubCommand extends SubCommand {
    public VoteClaimSubCommand() {
        super("claim");
    }

    @Override
    public void prepare() {
        this.setOnlyPlayer(true);
    }

    @Override
    public void onSessionRun(PlayerSession session, String[] args) {
        String key = session.getPlayer().getName().toLowerCase() + "." + session.getPlayer().getServerInfo().getServerName().toLowerCase();
        JsonConfig data = Loader.getInstance().getVoteData();
        Boolean hasVote = data.getBoolean(key, false);
        if(!hasVote) {
            session.getPlayer().sendMessage(GlobalConstants.PREFIX + "§cVous avez aucun vote à récupéré sur ce serveur!");
            return;
        }

        session.sendToServer("vote", "yes");
        data.setBoolean(key, false);
        session.getPlayer().sendMessage(GlobalConstants.PREFIX + "§aVous avez bien récupéré vos récompenses!");
    }
}
