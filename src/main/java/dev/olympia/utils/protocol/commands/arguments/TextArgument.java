package dev.olympia.utils.protocol.commands.arguments;

import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;

public class TextArgument extends Argument {
    public TextArgument(String name, boolean optional)
    {
        super(name, optional);
    }

    @Override
    public CommandParam getType() {
        return CommandParam.MESSAGE;
    }
}
