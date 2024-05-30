package dev.olympia.utils.constants;

public enum Permissions {
    BROADCAST_COMMAND("olympia.proxy.broadcast.command"),
    BAN_COMMAND("olympia.proxy.ban.command"),
    ;

    String name;
    Permissions(String name) {
        this.name = name;
    }
}
