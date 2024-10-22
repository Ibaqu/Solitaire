package org.ibaqu;

import java.util.EnumMap;
import java.util.Map;

public class FoundationPile {

    private Map<Suit, Card> foundation;

    public FoundationPile() {
        foundation = new EnumMap<>(Suit.class);

        for (Suit suit : Suit.values()) {
            foundation.put(suit, null);  // Initialize each foundation pile as empty (null)
        }
    }

    public Card getTopCard(Suit suit) {
        return foundation.get(suit);
    }

    public void addCard(Suit suit, Card card) {
        foundation.put(suit, card);
    }
}
