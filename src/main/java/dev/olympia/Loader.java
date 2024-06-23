package dev.olympia;

import dev.olympia.commands.global.BroadcastCommand;
import dev.olympia.commands.global.LinkCommand;
import dev.olympia.commands.global.OnlineCommand;
import dev.olympia.commands.global.VisionCommand;
import dev.olympia.commands.global.servers.*;
import dev.olympia.commands.staff.freeze.FreezeCommand;
import dev.olympia.commands.staff.freeze.UnFreezeCommand;
import dev.olympia.commands.staff.punishment.BanCommand;
import dev.olympia.commands.staff.punishment.LatencyCommand;
import dev.olympia.events.packets.PacketReceiveEvent;
import dev.olympia.events.packets.PacketSendEvent;
import dev.olympia.listeners.EventListener;
import dev.olympia.listeners.PacketListener;
import dev.olympia.listeners.SessionListener;
import dev.olympia.session.SessionManager;
import dev.olympia.utils.protocol.ProtocolUpdater;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectedEvent;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.event.defaults.ServerTransferEvent;
import dev.waterdog.waterdogpe.network.protocol.ProtocolCodecs;
import dev.waterdog.waterdogpe.plugin.Plugin;

public class  Loader extends Plugin {
    protected static Loader instance;
    protected ProtocolUpdater updater = new ProtocolUpdater();

    public static Loader getInstance()
    {
        return instance;
    }

    public static void setInstance(Loader loader)
    {
        instance = loader;
    }

    @Override
    public void onEnable() {
        setInstance(this);

        ProtocolCodecs.addUpdater(updater);

        new SessionManager();
        this.loadListeners();
        this.loadCommands();
    }

    public void loadListeners()
    {
        getProxy().getEventManager().subscribe(PlayerLoginEvent.class, EventListener::onLogin);
        getProxy().getEventManager().subscribe(PlayerDisconnectedEvent.class, EventListener::onDisconnect);

        getProxy().getEventManager().subscribe(PacketReceiveEvent.class, PacketListener::onPacketReceive);
        getProxy().getEventManager().subscribe(PacketSendEvent.class, PacketListener::onPacketSend);

        getProxy().getEventManager().subscribe(ServerTransferEvent.class, SessionListener::onTransfer);
    }

    public void loadCommands()
    {
        // Global commands
        registerCommand(new LatencyCommand());
        registerCommand(new OnlineCommand());
        registerCommand(new VisionCommand());

        registerCommand(new LinkCommand());

         // Servers
        registerCommand(new BuildCommand());
        registerCommand(new FFACommand());
        registerCommand(new KitMapCommand());
        registerCommand(new KitPvPCommand());
        registerCommand(new LobbyCommand());
        registerCommand(new ServersCommand());

        // Staff Commands
        registerCommand(new BroadcastCommand());
        registerCommand(new FreezeCommand());
        registerCommand(new UnFreezeCommand());

        registerCommand(new BanCommand());
    }

    public void registerCommand(BaseCommand command)
    {
        getProxy().getCommandMap().registerCommand(command);
    }
}
