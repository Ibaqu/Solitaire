package org.ibaqu;

public enum Rank {
    ACE("A"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("11"),
    QUEEN("12"),
    KING("13");

    // TODO : Changing the rank to integers is probably better down the line
    private final String value;

    Rank(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
