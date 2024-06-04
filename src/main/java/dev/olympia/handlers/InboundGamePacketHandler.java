package dev.olympia.handlers;

import dev.olympia.session.PlayerSession;
import dev.olympia.utils.protocol.forms.Form;
import org.cloudburstmc.protocol.bedrock.packet.*;

public class InboundGamePacketHandler {
    protected PlayerSession session;

    public InboundGamePacketHandler(PlayerSession playerSession)
    {
        this.session = playerSession;
    }

    public boolean handle(BedrockPacket packet) {
        if(packet instanceof ModalFormResponsePacket pk) return handle(pk);
        if(packet instanceof PlayerAuthInputPacket pk) return handle(pk);
        if(packet instanceof SetLocalPlayerAsInitializedPacket pk) return handle(pk);
        return true;
    }

    public boolean handle(ModalFormResponsePacket packet)
    {
        Form form = Form.forms.getOrDefault(session.getPlayer().getName(), null);
        if(form != null && packet.getFormId() == form.getId()) {
            if(packet.getCancelReason().isEmpty()) {
                form.handleResponse(session.getPlayer(), packet.getFormData());
            } else form.close(session.getPlayer());
            return false;
        }

        return true;
    }

    public boolean handle(PlayerAuthInputPacket packet)
    {
        session.setPosition(packet.getPosition());
        return true;
    }

    public boolean handle(SetLocalPlayerAsInitializedPacket packet)
    {
        session.setEntityId(packet.getRuntimeEntityId());
        return true;
    }
}
