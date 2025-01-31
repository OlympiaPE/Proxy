package dev.olympia.session.types;

public enum EffectIds {
    SPEED(1),
    SLOWNESS(2),
    HASTE(3),
    MINING_FATIGUE(4),
    STRENGTH(5),
    INSTANT_HEALTH(6),
    INSTANT_DAMAGE(7),
    JUMP_BOOST(8),
    NAUSEA(9),
    REGENERATION(10),
    RESISTANCE(11),
    FIRE_RESISTANCE(12),
    WATER_BREATHING(13),
    INVISIBILITY(14),
    BLINDNESS(15),
    NIGHT_VISION(16),
    HUNGER(17),
    WEAKNESS(18),
    POISON(19),
    WITHER(20),
    HEALTH_BOOST(21),
    ABSORPTION(22),
    SATURATION(23),
    LEVITATION(24),
    FATAL_POISON(25),
    CONDUIT_POWER(26),
    SLOW_FALLING(27),
    BAD_OMEN(28),
    VILLAGE_HERO(29),
    DARKNESS(30);

    public final int id;
    EffectIds(int id) {
        this.id = id;
    }
}
