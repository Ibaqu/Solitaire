package org.ibaqu;

import org.ibaqu.Console;

public class Card {

    private final String suit;
    private final String rank;
    private boolean isFaceUp;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
        this.isFaceUp = false;
    }

    // Flip over card
    public void flip() {
        this.isFaceUp = !isFaceUp;
    }

    public Boolean isFaceUp() {
        return this.isFaceUp;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return (isFaceUp() ? Console.BOLD + "[" + rank + " of " + suit + "]" + Console.RESET : "[ ? ]");
    }
}
