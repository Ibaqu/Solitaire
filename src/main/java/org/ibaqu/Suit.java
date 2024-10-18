package org.ibaqu;

public enum Suit {
    HEARTS(Console.HEARTS),
    DIAMONDS(Console.DIAMONDS),
    CLUBS(Console.CLUBS),
    SPADES(Console.SPADES);

    private final String value;

    Suit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
