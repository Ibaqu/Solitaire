package org.ibaqu;

import java.util.LinkedList;
import java.util.List;

public class TableauPile {

    private LinkedList<Card> faceDownCards = new LinkedList<>();
    private LinkedList<Card> faceUpCards = new LinkedList<>();

    public TableauPile addFaceUpCard(Card card) {
        if (!card.isFaceUp()) {
            card.flip();
        }
        faceUpCards.add(card);
        return this;
    }

    public TableauPile addFaceDownCard(Card card) {
        if (card.isFaceUp()) {
            card.flip();
        }
        faceDownCards.add(card);
        return this;
    }

    public List<Card> getFaceUpCards() {
        return faceUpCards;
    }

    public List<Card> getFaceDownCards() {
        return faceDownCards;
    }

    public Card removeTopFaceUpCard() {
        return faceUpCards.isEmpty() ? null : faceUpCards.removeLast();
    }

    // Remove all face up cards when moving between tableau piles
    // Flip the top card in the face down pile automatically
    public void removeAllFaceUpCards() {
        faceUpCards.clear();
        flipTopCard();
    }

    public void flipTopCard() {
        // If face down cards are not empty and the faceup card list is empty
        if (!faceDownCards.isEmpty() && faceUpCards.isEmpty()) {
            faceUpCards.add(faceDownCards.removeLast().flip());
        }
    }
}
