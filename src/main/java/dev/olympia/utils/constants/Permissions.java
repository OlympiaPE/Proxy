package dev.olympia.utils.constants;

public enum Permissions {
    BROADCAST_COMMAND("olympia.proxy.broadcast.command"),
    BAN_COMMAND("olympia.proxy.ban.command"),
    FREEZE_COMMAND("olympia.proxy.freeze.command"),
    UNFREEZE_COMMAND("olympia.proxy.unfreeze.command"),
    ;

    String name;
    Permissions(String name) {
        this.name = name;
    }
}
