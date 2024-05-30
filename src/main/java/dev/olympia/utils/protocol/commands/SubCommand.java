package dev.olympia.utils.protocol.commands;

import dev.olympia.session.PlayerSession;
import dev.olympia.utils.protocol.commands.arguments.Argument;
import dev.waterdog.waterdogpe.command.CommandSender;

import java.util.LinkedHashMap;

public abstract class SubCommand {
    protected String name;
    protected String permission = "";
    protected LinkedHashMap<Integer, Argument> arguments = new LinkedHashMap<>();

    protected boolean onlyPlayer = false;

    public SubCommand(String name)
    {
        this.name = name;
        this.prepare();
    }

    abstract public void prepare();

    public boolean isOnlyPlayer() {
        return onlyPlayer;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void registerArgument(int number, Argument argument)
    {
        this.arguments.put(number, argument);
    }

    public LinkedHashMap<Integer, Argument> getArguments() {
        return arguments;
    }

    public void setOnlyPlayer(boolean onlyPlayer) {
        this.onlyPlayer = onlyPlayer;
    }

    public void onRun(CommandSender commandSender, String[] args)
    {
    }

    public void onSessionRun(PlayerSession session, String[] args)
    {
    }
}
