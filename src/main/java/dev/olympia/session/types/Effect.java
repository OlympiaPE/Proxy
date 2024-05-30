package dev.olympia.session.types;

public class Effect {
    protected int id;
    protected int amplifier;
    protected int duration;
    protected boolean visible;

    public Effect(int id, int amplifier, int duration, boolean visible)
    {
        this.id = id;
        this.amplifier = amplifier;
        this.duration = duration;
        this.visible = visible;
    }

    public int getId() {
        return id;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isVisible() {
        return visible;
    }
}
