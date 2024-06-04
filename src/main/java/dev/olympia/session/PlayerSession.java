package dev.olympia.session;

import dev.olympia.handlers.InboundGamePacketHandler;
import dev.olympia.handlers.OutboundGamePacketHandler;
import dev.olympia.session.types.Effect;
import dev.olympia.session.types.EffectIds;
import dev.olympia.utils.protocol.handler.PacketHandler;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.packet.MobEffectPacket;
import org.cloudburstmc.protocol.bedrock.packet.MovePlayerPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlaySoundPacket;

import java.util.LinkedHashMap;

public class PlayerSession {
    protected ProxiedPlayer player;
    protected long entityId = 0;
    protected Vector3f position;
    protected Vector3f rotation;
    protected boolean immobile = false;
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

    public long getEntityId() { return entityId; }
    public void setEntityId(long eid) { entityId = eid; }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }


    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public LinkedHashMap<Integer, Effect> getEffects() {
        return effects;
    }

    public void setEffects(LinkedHashMap<Integer, Effect> effects) {
        this.effects = effects;
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

    public boolean isImmobile() {
        return immobile;
    }

    public void setImmobile(boolean value) {
        this.immobile = value;
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
}
