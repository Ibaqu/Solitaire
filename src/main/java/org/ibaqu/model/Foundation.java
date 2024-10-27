package org.ibaqu.model;

import java.util.EnumMap;
import java.util.Map;

public class Foundation {

    private Map<Suit, Card> foundation;

    public Foundation() {
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
