package org.ibaqu;

import org.ibaqu.Console;

public class Card {

    private final Suit suit;
    private final Rank rank;
    private boolean isFaceUp;

    public Card(Suit suit, Rank rank) {
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

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        // TODO : Change rendering for Jack, Queen and King
        String rankValue = (rank.getValue() == 1 ? "A" : String.valueOf(rank.getValue()));
        return (isFaceUp() ? Console.BOLD + "[" + rankValue + " of " + suit.getValue() + "]" + Console.RESET :
                "[ ? ]");
    }
}
