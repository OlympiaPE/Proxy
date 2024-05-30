package dev.olympia.utils.protocol.commands.arguments;

import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;

public class EnumArgument extends Argument {
    protected String[] data;

    public EnumArgument(String name, boolean optional, String[] data)
    {
        super(name, optional);
        this.data = data;
    }

    public String[] getData() {
        return data;
    }

    @Override
    public CommandParam getType() {
        return CommandParam.HAS_PROPERTY_PARAM_ENUM_VALUE;
    }
}
