package org.ibaqu;

import org.ibaqu.Console;

public class Card {

    private final String suit;
    private final String rank;
    private boolean isFaceUp;

    // TODO : Remove this
    //public static final String[] suits = {Console.HEARTS, Console.DIAMONDS, Console.CLUBS, Console.SPADES};
    //public static final String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
        this.isFaceUp = false;
    }

    // Flip over card
    public Card flip() {
        this.isFaceUp = !isFaceUp;
        return this;
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
