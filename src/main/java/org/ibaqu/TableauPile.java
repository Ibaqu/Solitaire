package org.ibaqu;

import java.util.LinkedList;
import java.util.List;

public class Tableau {

    private LinkedList<Card> faceDownCards = new LinkedList<>();
    private LinkedList<Card> faceUpCards = new LinkedList<>();

    public void addFaceUpCard(Card card) {
        faceUpCards.add(card);
    }

    public Card removeTopFaceUpCard() {
        return faceUpCards.isEmpty() ? null : faceUpCards.removeLast();
    }

    public void flipTopCard() {
        // If face down cards are not empty and the faceup card list is empty
        if (!faceDownCards.isEmpty() && faceUpCards.isEmpty()) {
            faceUpCards.add(faceDownCards.removeLast());
        }
    }

    public List<Card> getFaceUpCards() {
        return faceUpCards;
    }
}
