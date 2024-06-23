package dev.olympia.commands.global;

import dev.olympia.commands.global.subcommand.VoteClaimSubCommand;
import dev.olympia.session.PlayerSession;
import dev.olympia.task.VoteAsyncTask;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.scheduler.WaterdogScheduler;

public class VoteCommand extends BaseCommand {
    public VoteCommand() {
        super("vote", CommandSettings.builder()
                .setDescription("Voté pour le serveur.")
                .build());
    }

    @Override
    public void prepare() {
        setOnlyPlayer(true);
        registerSubCommand(new VoteClaimSubCommand());
    }



    @Override
    public void onSessionRun(PlayerSession session, String[] args) {
        WaterdogScheduler.getInstance().scheduleAsync(new VoteAsyncTask(GlobalConstants.VOTE_KEY, session.getPlayer().getName()));
    }
}
