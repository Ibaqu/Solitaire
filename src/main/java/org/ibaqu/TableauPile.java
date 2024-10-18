package org.ibaqu;

import java.util.LinkedList;
import java.util.List;

public class TableauPile {

    private LinkedList<Card> faceDownCards = new LinkedList<>();
    private LinkedList<Card> faceUpCards = new LinkedList<>();

    public void addFaceUpCard(Card card) {
        faceUpCards.add(card);
    }

    public void addFaceDownCard(Card card) {
        faceDownCards.add(card);
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

    public void flipTopCard() {
        // If face down cards are not empty and the faceup card list is empty
        if (!faceDownCards.isEmpty() && faceUpCards.isEmpty()) {
            faceUpCards.add(faceDownCards.removeLast().flip());
        }
    }
}
