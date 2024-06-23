package dev.olympia.commands.global;

import dev.olympia.session.PlayerSession;
import dev.olympia.session.types.Effect;
import dev.olympia.session.types.EffectIds;
import dev.olympia.utils.constants.GlobalConstants;
import dev.olympia.utils.protocol.commands.BaseCommand;
import dev.waterdog.waterdogpe.command.CommandSettings;

public class VisionCommand extends BaseCommand {
    public VisionCommand() {
        super("vision", CommandSettings.builder()
                .setDescription("Voir dans l'obscurité ou non.")
                .setAliases("nightvision", "nv")
                .build());
    }

    @Override
    public void prepare() {
        setOnlyPlayer(true);
    }

    @Override
    public void onSessionRun(PlayerSession session, String[] args) {
        if(session.hasEffect(EffectIds.NIGHT_VISION)) {
            session.removeEffectWithId(EffectIds.NIGHT_VISION);
            session.getPlayer().sendMessage(GlobalConstants.PREFIX + "§aVous avez bien §edésactivé§a la §evision nocturne§a.");
            return;
        }

        session.addEffect(new Effect(EffectIds.NIGHT_VISION.id, 255, 999999999, false));
        session.getPlayer().sendMessage(GlobalConstants.PREFIX + "§aVous avez bien §eactivé§a la §evision nocturne§a.");
    }
}
