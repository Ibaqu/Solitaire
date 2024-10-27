package org.ibaqu;

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
        // Convert ranks 1 (Ace), 11 (Jack), 12 (Queen), and 13 (King) to their respective symbols
        String rankValue;
        switch (rank.getValue()) {
            case 1:  rankValue = "A"; break;  // Ace
            case 11: rankValue = "J"; break;  // Jack
            case 12: rankValue = "Q"; break;  // Queen
            case 13: rankValue = "K"; break;  // King
            default: rankValue = String.valueOf(rank.getValue());  // Numeric cards
        }

        return (isFaceUp() ? Console.BOLD + "[" + rankValue + " of " + suit.getValue() + "]" + Console.RESET :
                "[ ? ]");
    }
}
