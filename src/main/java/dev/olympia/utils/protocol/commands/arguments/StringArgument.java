package dev.olympia.utils.protocol.commands.arguments;

import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;

public class StringArgument extends Argument {
    protected String[] data;

    public StringArgument(String name, boolean optional)
    {
        super(name, optional);
    }

    @Override
    public CommandParam getType() {
        return CommandParam.STRING;
    }
}
