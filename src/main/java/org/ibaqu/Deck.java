package org.ibaqu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private final List<Card> cards;

    // Initialize main.java.Deck of cards
    public Deck() {
        cards = new ArrayList<>();

        // Iterate through each suit up the ranks and define a card to add to the deck
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit.getValue(), rank.getValue()));
            }
        }

        shuffle();
    }

    public List<Card> getCards() {
        return cards;
    }

    // TODO : Consider changing the deck to a Hashset to preserve uniqueness and maintaining random order
    private void shuffle() {
        Collections.shuffle(cards);
    }

    // Draw a card from the deck
    public Card drawCard() {
        return cards.isEmpty() ? null : cards.remove(cards.size() - 1);
    }

}
