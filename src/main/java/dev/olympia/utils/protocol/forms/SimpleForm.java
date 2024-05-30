package dev.olympia.utils.protocol.forms;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.Objects;

public class SimpleForm extends Form {
    protected HashMap<Integer, String> labelMap = new HashMap<>();

    public SimpleForm(int id) {
        super(id);
        data.add("type", new JsonPrimitive("form"));
        data.add("title", new JsonPrimitive(""));
        data.add("content", new JsonPrimitive(""));
        data.add("buttons", new JsonArray());
    }

    @Override
    public Object processData(Object data) {
        String value = data.toString().replace("\n", "");
        int count = this.data.get("buttons").getAsJsonArray().size();

        try {
            int button = Integer.parseInt(value);
            if(button >= count || button < 0) {
                return null;
            }

            data = Objects.requireNonNullElse(labelMap.getOrDefault(button, null), button);
        } catch (NumberFormatException e) {
            return null;
        }

        return data;
    }

    public SimpleForm setTitle(String title)
    {
        data.add("title", new JsonPrimitive(title));
        return this;
    }

    public SimpleForm setContent(String content)
    {
        data.add("content", new JsonPrimitive(content));
        return this;
    }

    public SimpleForm addButton(String text, int imageType, String imagePath, String label)
    {
        JsonObject button = new JsonObject();
        button.add("text", new JsonPrimitive(text));

        if(imageType != -1) {
            JsonObject image = new JsonObject();
            image.add("type", new JsonPrimitive(imageType == 0 ? "path" : "url"));
            image.add("data", new JsonPrimitive(imagePath));
            button.add("image", image);
        }

        if(!label.trim().isEmpty()) {
            labelMap.put(labelMap.size(), label);
        }

        data.get("buttons").getAsJsonArray().add(button);
        return this;
    }

    public SimpleForm addButton(String text)
    {
        return addButton(text, -1, "", "");
    }

    public SimpleForm addButton(String text, String label)
    {
        return addButton(text, -1, "", label);
    }

    public SimpleForm addButton(String text, int imageType, String imagePath)
    {
        return addButton(text, imageType, imagePath, "");
    }
}
