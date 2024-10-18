package org.ibaqu;

public enum Suit {
    HEARTS(Console.HEARTS, Console.RED),
    DIAMONDS(Console.DIAMONDS, Console.RED),
    CLUBS(Console.CLUBS, Console.BLACK),
    SPADES(Console.SPADES, Console.BLACK);

    private final String value;
    private final String color;

    Suit(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }
}
