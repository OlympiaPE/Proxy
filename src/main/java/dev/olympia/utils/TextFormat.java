package dev.olympia.utils;

public final class TextFormat {
    public static final String ESCAPE = "§"; // §
    public static final String EOL = "\n";

    public static final String BLACK = ESCAPE + "0";
    public static final String DARK_BLUE = ESCAPE + "1";
    public static final String DARK_GREEN = ESCAPE + "2";
    public static final String DARK_AQUA = ESCAPE + "3";
    public static final String DARK_RED = ESCAPE + "4";
    public static final String DARK_PURPLE = ESCAPE + "5";
    public static final String GOLD = ESCAPE + "6";
    public static final String GRAY = ESCAPE + "7";
    public static final String DARK_GRAY = ESCAPE + "8";
    public static final String BLUE = ESCAPE + "9";
    public static final String GREEN = ESCAPE + "a";
    public static final String AQUA = ESCAPE + "b";
    public static final String RED = ESCAPE + "c";
    public static final String LIGHT_PURPLE = ESCAPE + "d";
    public static final String YELLOW = ESCAPE + "e";
    public static final String WHITE = ESCAPE + "f";
    public static final String MINECOIN_GOLD = ESCAPE + "g";

    public static final String[] COLORS = {BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE, MINECOIN_GOLD};

    public static final String OBFUSCATED = ESCAPE + "k";
    public static final String BOLD = ESCAPE + "l";
    public static final String STRIKETHROUGH = ESCAPE + "m";
    public static final String UNDERLINE = ESCAPE + "n";
    public static final String ITALIC = ESCAPE + "o";

    public static final String[] FORMATS = {OBFUSCATED, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC};

    public static final String RESET = ESCAPE + "r";

    public static String colorize(String string) {
        return string.replaceAll("&([0-9a-fklmnor])", ESCAPE + "$1");
    }

    public static String clean(String string, boolean removeFormat) {
        string = string.replaceAll("\u00A7([0-9a-fklmnor])", "");
        if (removeFormat) {
            string = string.replaceAll("\u00A7[0-9a-fklmnor]", "");
        }
        return string;
    }
}
