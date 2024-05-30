package dev.olympia.utils.protocol.commands.arguments;

import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;

public class TargetArgument extends Argument {
    public TargetArgument(String name, boolean optional)
    {
        super(name, optional);
    }

    @Override
    public CommandParam getType() {
        return CommandParam.TARGET;
    }
}
