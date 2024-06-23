package dev.olympia.utils.protocol.commands;

import dev.olympia.session.PlayerSession;
import dev.olympia.session.SessionManager;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.protocol.commands.arguments.Argument;
import dev.olympia.utils.protocol.commands.arguments.EnumArgument;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import org.cloudburstmc.protocol.bedrock.data.command.*;

import java.util.*;

abstract public class BaseCommand extends Command {
    protected String noPermission = GlobalConstants.PREFIX + "§cYou are not allowed to use this command.";
    protected String forgottenArgument = GlobalConstants.PREFIX + "§cVous avez oublié quelques arguments.";
    protected String cannotUseConsole = GlobalConstants.PREFIX + "§cVous ne pouvez pas exécuter cette commande à partir de la console.";
    protected String invalidUsage = GlobalConstants.PREFIX + "§cUsage de la commande invalide.";

    protected boolean onlyPlayer = false;
    protected LinkedHashMap<Integer, Argument> arguments = new LinkedHashMap<>();
    protected LinkedHashMap<String, SubCommand> subCommands = new LinkedHashMap<>();

    public BaseCommand(String name, CommandSettings settings) {
        super(name, settings);
        this.prepare();
    }

    @Override
    protected CommandOverloadData[] buildCommandOverloads() {
        List<CommandOverloadData> overloadDataList = new ArrayList<>();

        for (Map.Entry<String, SubCommand> entry : getSubCommands().entrySet()) {
            String name = entry.getKey();
            SubCommand subCommand = entry.getValue();

            List<CommandParamData> paramDataList = new ArrayList<>();
            CommandParamData commandParamData = new CommandParamData();
            commandParamData.setName(subCommand.name);
            commandParamData.setOptional(true);
            commandParamData.setType(CommandParam.HAS_PROPERTY_PARAM_ENUM_VALUE);
            commandParamData.setEnumData(new CommandEnumData(subCommand.name, Map.of(subCommand.name, new HashSet<>()), false));
            paramDataList.add(commandParamData);

            for (Map.Entry<Integer, Argument> entry2 : subCommand.getArguments().entrySet()) {
                Argument argument = entry2.getValue();

                if (argument instanceof EnumArgument enumArgument) {
                    CommandParamData enumParamData = new CommandParamData();
                    enumParamData.setName(enumArgument.getName());
                    enumParamData.setOptional(enumArgument.isOptional());
                    enumParamData.setType(argument.getType());

                    Map<String, Set<CommandEnumConstraint>> args = new HashMap<>();
                    String[] enumValues = enumArgument.getData();
                    for (String value : enumValues) {
                        args.put(value.toLowerCase(), new HashSet<>());
                    }

                    enumParamData.setEnumData(new CommandEnumData(enumArgument.getName(), args, false));
                    paramDataList.add(enumParamData);
                }
                else {
                    CommandParamData stringParamData = new CommandParamData();
                    stringParamData.setName(argument.getName());
                    stringParamData.setOptional(argument.isOptional());
                    stringParamData.setType(argument.getType());
                    paramDataList.add(stringParamData);
                }
            }

            CommandOverloadData overloadData = new CommandOverloadData(false, paramDataList.toArray(new CommandParamData[]{}));
            overloadDataList.add(overloadData);
        }

        List<CommandParamData> mainParamDataList = new ArrayList<>();
        for (Map.Entry<Integer, Argument> entry : getArguments().entrySet()) {
            Argument argument = entry.getValue();

            if (argument instanceof EnumArgument enumArgument) {
                CommandParamData enumParamData = new CommandParamData();
                enumParamData.setName(enumArgument.getName());
                enumParamData.setOptional(enumArgument.isOptional());
                enumParamData.setType(CommandParam.HAS_PROPERTY_PARAM_ENUM_VALUE);

                String[] enumValues = enumArgument.getData();
                Map<String, Set<CommandEnumConstraint>> enumData = new HashMap<>();
                for (String value : enumValues) {
                    enumData.put(value.toLowerCase(), new HashSet<>());
                }

                enumParamData.setEnumData(new CommandEnumData(
                        enumArgument.getName(), enumData, false
                ));
                mainParamDataList.add(enumParamData);
            }
            else {
                CommandParamData stringParamData = new CommandParamData();
                stringParamData.setName(argument.getName());
                stringParamData.setOptional(argument.isOptional());
                stringParamData.setType(argument.getType());
                mainParamDataList.add(stringParamData);
            }
        }

        CommandOverloadData mainOverloadData = new CommandOverloadData(false, mainParamDataList.toArray(new CommandParamData[]{}));
        overloadDataList.add(mainOverloadData);

        return overloadDataList.toArray(new CommandOverloadData[]{});
    }

    abstract public void prepare();

    public boolean isOnlyPlayer() {
        return onlyPlayer;
    }

    public void setOnlyPlayer(boolean onlyPlayer) {
        this.onlyPlayer = onlyPlayer;
    }

    public LinkedHashMap<String, SubCommand> getSubCommands() {
        return subCommands;
    }

    public void registerSubCommand(SubCommand subCommand)
    {
        this.subCommands.put(subCommand.name, subCommand);
    }

    public void registerArgument(int number, Argument argument)
    {
        this.arguments.put(number, argument);
    }

    public LinkedHashMap<Integer, Argument> getArguments() {
        return arguments;
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] strings) {
        if(!commandSender.hasPermission(getPermission())) {
            commandSender.sendMessage(noPermission);
            return true;
        }

        if(getSubCommands().size() > 0) {
            if(strings.length >= 1) {
                String[] args = new String[strings.length - 1];
                System.arraycopy(strings, 1, args, 0, strings.length - 1);

                for (Map.Entry<String, SubCommand> entry : getSubCommands().entrySet()) {
                    String name = entry.getKey();
                    SubCommand subCommand = entry.getValue();
                    if(strings[0] != null && strings[0].equalsIgnoreCase(name)) {
                        if(!subCommand.getPermission().trim().isEmpty()) {
                            if(!commandSender.hasPermission(subCommand.getPermission())) {
                                commandSender.sendMessage(noPermission);
                                return true;
                            }
                        }

                        for (Map.Entry<Integer, Argument> entry2 : subCommand.getArguments().entrySet()) {
                            int index = entry2.getKey();
                            Argument argument = entry2.getValue();
                            if (!argument.isOptional() && (strings.length <= index || strings[index] == null)) {
                                commandSender.sendMessage(forgottenArgument);
                                return true;
                            }
                        }

                        if(subCommand.isOnlyPlayer()) {
                            if(!commandSender.isPlayer()) {
                                commandSender.sendMessage(cannotUseConsole);
                                return true;
                            }

                            var session = SessionManager.getInstance().getSession((ProxiedPlayer) commandSender);
                            subCommand.onSessionRun(session, args);
                            return true;
                        }

                        subCommand.onRun(commandSender, args);
                        return true;
                    }
                }
            }
        }

        if(getArguments().size() > 0) {
            for (Map.Entry<Integer, Argument> entry : getArguments().entrySet()) {
                int index = entry.getKey();
                Argument argument = entry.getValue();
                if (!argument.isOptional() && (strings.length <= index || strings[index] == null)) {
                    commandSender.sendMessage(forgottenArgument);
                    return true;
                }
            }

            if(this.isOnlyPlayer()) {
                if(!commandSender.isPlayer()) {
                    commandSender.sendMessage(cannotUseConsole);
                    return  true;
                }

                var session = SessionManager.getInstance().getSession((ProxiedPlayer) commandSender);
                this.onSessionRun(session, strings);
                return true;
            }

            this.onRun(commandSender, strings);
            return true;
        }

        if(this.isOnlyPlayer()) {
            if(!commandSender.isPlayer()) {
                commandSender.sendMessage(cannotUseConsole);
                return  true;
            }

            var session = SessionManager.getInstance().getSession((ProxiedPlayer) commandSender);
            this.onSessionRun(session, strings);
            return true;
        }

        this.onRun(commandSender, strings);
        return true;
    }

    public void onRun(CommandSender commandSender, String[] args)
    {
    }

    public void onSessionRun(PlayerSession session, String[] args)
    {
    }
}
