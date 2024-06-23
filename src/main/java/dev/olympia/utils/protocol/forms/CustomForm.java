package dev.olympia.utils.protocol.forms;

import com.google.gson.*;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

import java.util.HashMap;
import java.util.Objects;

public class CustomForm extends Form {
    protected HashMap<Integer, String> labelMap = new HashMap<>();

    protected JsonObject response = null;

    public CustomForm(int id) {
        super(id);
        data.add("type", new JsonPrimitive("custom_form"));
        data.add("title", new JsonPrimitive(""));
        data.add("content", new JsonArray());
    }

    @Override
    public JsonObject getResponse() {
        return response;
    }

    @Override
    public JsonObject processData(Object data) {
        JsonArray array = JsonParser.parseString(data.toString()).getAsJsonArray();
        JsonObject object = new JsonObject();

        int i = 0;
        for (JsonElement jsonElement : array) {
            object.add(labelMap.getOrDefault(i, String.valueOf(i)), jsonElement);
            i++;
        }

        return object;
    }

    @Override
    public void handleResponse(ProxiedPlayer player, Object data)
    {
        JsonObject newData = processData(data);
        if(newData != null) {
            response = newData;
            if(callable != null) callable.run();
            Form.forms.remove(player.getName());
        }
    }

    public CustomForm setTitle(String title)
    {
        data.add("title", new JsonPrimitive(title));
        return this;
    }

    private void addContent(JsonObject content)
    {
        this.data.get("content").getAsJsonArray().add(content);
    }

    public void addLabel(String text)
    {
        JsonObject object = new JsonObject();
        object.add("type", new JsonPrimitive("label"));
        object.add("text", new JsonPrimitive(text));
        this.addContent(object);
    }

    public void addToggle(String text, boolean def, String label)
    {
        JsonObject object = new JsonObject();
        object.add("type", new JsonPrimitive("toggle"));
        object.add("text", new JsonPrimitive(text));
        object.add("default", new JsonPrimitive(def));

        if(!label.trim().isEmpty()) {
            labelMap.put(labelMap.size(), label);
        }

        this.addContent(object);
    }

    public void addInput(String text, String placeholder, String def, String label)
    {
        JsonObject object = new JsonObject();
        object.add("type", new JsonPrimitive("input"));
        object.add("text", new JsonPrimitive(text));
        object.add("placeholder", new JsonPrimitive(placeholder));
        object.add("default", new JsonPrimitive(def));

        if(!label.trim().isEmpty()) {
            labelMap.put(labelMap.size(), label);
        }

        this.addContent(object);
    }
}
