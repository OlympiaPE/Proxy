package dev.olympia.session;

import dev.olympia.Loader;
import dev.olympia.handlers.InboundGamePacketHandler;
import dev.olympia.handlers.OutboundGamePacketHandler;
import dev.olympia.session.types.Effect;
import dev.olympia.session.types.EffectIds;
import dev.olympia.utils.protocol.handler.PacketHandler;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import lombok.Getter;
import lombok.Setter;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.packet.MobEffectPacket;
import org.cloudburstmc.protocol.bedrock.packet.MovePlayerPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlaySoundPacket;
import org.cloudburstmc.protocol.bedrock.packet.ScriptMessagePacket;

import java.util.LinkedHashMap;

public class PlayerSession {
    protected ProxiedPlayer player;
    @Getter
    @Setter
    protected long entityId = 0;
    @Setter
    @Getter
    protected Vector3f position;
    @Setter
    @Getter
    protected Vector3f rotation;
    @Getter
    @Setter
    protected boolean immobile = false;
    @Setter
    @Getter
    protected LinkedHashMap<Integer, Effect> effects = new LinkedHashMap<>();

    protected InboundGamePacketHandler inboundGamePacketHandler;
    protected OutboundGamePacketHandler outboundGamePacketHandler;

    public PlayerSession(ProxiedPlayer player)
    {
        this.player = player;
        this.inboundGamePacketHandler = new InboundGamePacketHandler(this);
        this.outboundGamePacketHandler = new OutboundGamePacketHandler(this);
        this.player.getPluginPacketHandlers().add(new PacketHandler(this));
    }

    public ProxiedPlayer getPlayer() {
        return player;
    }

    public InboundGamePacketHandler getInboundGamePacketHandler() {
        return inboundGamePacketHandler;
    }

    public OutboundGamePacketHandler getOutboundGamePacketHandler() {
        return outboundGamePacketHandler;
    }


    public void addEffect(Effect effect)
    {
        effects.put(effect.getId(), effect);
        MobEffectPacket pk = new MobEffectPacket();
        pk.setRuntimeEntityId(getEntityId());
        pk.setEffectId(effect.getId());
        pk.setEvent(MobEffectPacket.Event.ADD);
        pk.setAmplifier(effect.getAmplifier());
        pk.setDuration(effect.getDuration());
        pk.setParticles(effect.isVisible());
        pk.setTick(0L);
        getPlayer().sendPacket(pk);
    }

    public void removeEffect(Effect effect)
    {
        effects.remove(effect.getId());
        MobEffectPacket pk = new MobEffectPacket();
        pk.setRuntimeEntityId(getEntityId());
        pk.setEffectId(effect.getId());
        pk.setEvent(MobEffectPacket.Event.REMOVE);
        pk.setTick(0L);
        getPlayer().sendPacket(pk);
    }

    public void removeEffectWithId(EffectIds effectIds)
    {
        effects.remove(effectIds.id);
        MobEffectPacket pk = new MobEffectPacket();
        pk.setRuntimeEntityId(getEntityId());
        pk.setEffectId(effectIds.id);
        pk.setEvent(MobEffectPacket.Event.REMOVE);
        pk.setTick(0L);
        getPlayer().sendPacket(pk);
    }

    public boolean hasEffect(EffectIds effectIds)
    {
        return effects.containsKey(effectIds.id);
    }

    public void addSound(String sound, Vector3f position, float volume, float pitch)
    {
        PlaySoundPacket pk = new PlaySoundPacket();
        pk.setSound(sound);
        pk.setPosition(position);
        pk.setVolume(volume);
        pk.setPitch(pitch);
        getPlayer().sendPacket(pk);
    }

    public void addSound(String sound, float volume, float pitch)
    {
        addSound(sound, getPosition(), volume, pitch);
    }

    public void teleport(Vector3f position) {
        MovePlayerPacket packet = new MovePlayerPacket();
        packet.setPosition(position);
        packet.setRuntimeEntityId(getEntityId());
        packet.setRotation(getRotation());
        packet.setMode(MovePlayerPacket.Mode.TELEPORT);
        packet.setTeleportationCause(MovePlayerPacket.TeleportationCause.UNKNOWN);
        getPlayer().sendPacket(packet);
    }

    public void sendToServer(String packetName, String content)
    {
        ScriptMessagePacket messagePacket = new ScriptMessagePacket();
        messagePacket.setChannel(packetName.toUpperCase());
        messagePacket.setMessage(content);
        getPlayer().getDownstreamConnection().sendPacket(messagePacket);
    }

    public void onSpawn()
    {
        boolean nightVision = (boolean) Loader.getInstance().getNightVision().get(this.getPlayer().getName().toLowerCase(), false);
        if(nightVision) addEffect(new Effect(EffectIds.NIGHT_VISION.id, 255, 999999999, false));
    }
}
