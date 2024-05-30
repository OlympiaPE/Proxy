package dev.olympia.utils.protocol.commands.arguments;

public class BoolArgument extends EnumArgument {
    public BoolArgument(String name, boolean optional) {
        super(name, optional, new String[]{"true", "false"});
    }
}
