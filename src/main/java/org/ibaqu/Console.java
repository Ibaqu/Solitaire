package org.ibaqu;

public class Console {

    public static final String RESET = "\u001B[0m";

    public static final String BOLD = "\u001B[1m";

    public static final String RED = "\u001B[31m";
    public static final String BLACK = "\u001B[30m";

    public static final String WHITE_BACKGROUND = "\u001B[47m";

    public static final String HEARTS = WHITE_BACKGROUND + RED + "♥" + RESET;
    public static final String DIAMONDS = WHITE_BACKGROUND + BLACK + "♦" + RESET;
    public static final String CLUBS = WHITE_BACKGROUND + RED + "♣" + RESET;
    public static final String SPADES = WHITE_BACKGROUND + BLACK + "♠" + RESET;

}
