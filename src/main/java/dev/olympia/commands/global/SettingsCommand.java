package dev.olympia.commands.global;

import dev.olympia.Loader;
import dev.olympia.session.PlayerSession;
import dev.olympia.utils.ChatUtil;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.constants.Permissions;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.olympia.utils.protocol.commands.arguments.EnumArgument;
import dev.olympia.utils.protocol.commands.arguments.StringArgument;
import dev.olympia.utils.protocol.forms.CustomForm;
import dev.olympia.utils.protocol.forms.SimpleForm;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.utils.config.JsonConfig;

import java.util.Arrays;

public class SettingsCommand extends BaseCommand {
    public SettingsCommand() {
        super("settings", CommandSettings.builder()
                .setDescription("Modifié vos paramètres")
                .build());
    }

    @Override
    public void prepare() {
        setOnlyPlayer(true);
    }

    @Override
    public void onSessionRun(PlayerSession session, String[] args) {
        CustomForm form = new CustomForm(900);

        JsonConfig settings = Loader.getInstance().getSettings();
        form.setCallable(() -> {
            settings.setBoolean(session.getPlayer().getName().toLowerCase() + ".cps", form.getResponse().get("cps").getAsBoolean());
            settings.setBoolean(session.getPlayer().getName().toLowerCase() + ".xyz", form.getResponse().get("xyz").getAsBoolean());
        });

        form.setTitle("Paramètres");
        form.addToggle("CPS", settings.getBoolean(session.getPlayer().getName().toLowerCase() + ".cps", true), "cps");
        form.addToggle("Coordonnées", settings.getBoolean(session.getPlayer().getName().toLowerCase() + ".xyz", false), "xyz");
        form.send(session);
    }
}
