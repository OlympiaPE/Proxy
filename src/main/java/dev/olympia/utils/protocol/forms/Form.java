package dev.olympia.utils.protocol.forms;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import org.cloudburstmc.protocol.bedrock.packet.ModalFormRequestPacket;

import java.util.HashMap;

public class Form {
    public static HashMap<String, Form> forms = new HashMap<>();

    protected JsonObject data = new JsonObject();
    protected Object response = null;

    protected int id;
    protected Runnable callable = null;

    Form(int id)
    {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public JsonObject getData() {
        return data;
    }

    public Runnable getCallable() {
        return callable;
    }

    public void setCallable(Runnable callable) {
        this.callable = callable;
    }

    public Object getResponse() {
        return response;
    }

    public void handleResponse(ProxiedPlayer player, Object data)
    {
        Object newData = processData(data);
        if(newData != null) {
            response = newData;
            if(callable != null) callable.run();
            Form.forms.remove(player.getName());
        }
    }

    public void send(ProxiedPlayer player)
    {
        Form.forms.put(player.getName(), this);
        ModalFormRequestPacket packet = new ModalFormRequestPacket();
        packet.setFormId(getId());
        packet.setFormData(serialize());
        player.sendPacket(packet);
    }

    public Object processData(Object data) { return true; }

    public String serialize()
    {
        return new Gson().toJson(data);
    }
}
