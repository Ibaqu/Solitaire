package org.ibaqu;

public class Card {
    private final String suit;
    private final String rank;
    private boolean isFaceUp;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
        this.isFaceUp = false;
    }
}