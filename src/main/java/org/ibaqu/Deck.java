package org.ibaqu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private final List<Card> deck;

    // Initialize main.java.Deck of cards
    public Deck() {
        deck = new ArrayList<>();

        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

        // Iterate through each suit up the ranks and define a card to add to the deck
        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(suit, rank));
            }
        }
    }

    // TODO : Consider changing the deck to a Hashset to preserve uniqueness and maintaining random order
    private void shuffle() {
        Collections.shuffle(deck);
    }

    // Draw a card from the deck
    public Card drawCard() {
        return deck.isEmpty() ? null : deck.remove(deck.size() - 1);
    }

}
