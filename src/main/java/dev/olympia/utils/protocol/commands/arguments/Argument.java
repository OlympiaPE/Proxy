package dev.olympia.utils.protocol.commands.arguments;

import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;

abstract public class Argument {
    protected String name;
    protected boolean optional;

    public Argument(String name, boolean optional)
    {
        this.name = name;
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public boolean isOptional() {
        return optional;
    }

    abstract public CommandParam getType();
}
