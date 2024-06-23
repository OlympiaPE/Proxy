package dev.olympia.commands.global;

import dev.olympia.session.PlayerSession;
import dev.olympia.task.LinkAsyncTask;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.olympia.utils.protocol.commands.arguments.StringArgument;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.scheduler.WaterdogScheduler;

public class LinkCommand extends BaseCommand {
    public LinkCommand() {
        super("link", CommandSettings.builder()
                .setDescription("Liée sont compte à discord")
                .build());
    }

    @Override
    public void prepare() {
        setOnlyPlayer(true);
        this.registerArgument(0, new StringArgument("code", false));
    }

    @Override
    public void onSessionRun(PlayerSession session, String[] args) {
        WaterdogScheduler.getInstance().scheduleAsync(new LinkAsyncTask(GlobalConstants.LINK_KEY, session.getPlayer().getName(), args[0], "joueur"));
    }
}
