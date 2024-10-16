package org.ibaqu;

import java.util.List;
import java.util.Stack;

public class Renderer {

    public void renderStock(List<Card> stock) {
        if(!stock.isEmpty()) {
            System.out.println("Stock (S): Contains Cards");
        } else {
            System.out.println("Stock (S): Empty");
        }
    }

    public void renderWaste(List<Card> waste) {
        if(!waste.isEmpty()) {
            System.out.println("Waste (W): " + waste.get(waste.size() - 1));
        } else {
            System.out.println("Waste (W): Empty");
        }
    }

    // Displays live status of Foundation Piles
    public void renderFoundationPile(List<Stack<Card>> foundation) {
        int suitIndex = 0;

        for (String suit : new String[]{Console.HEARTS, Console.DIAMONDS, Console.CLUBS, Console.SPADES}) {
            System.out.print(suit + ": ");

            for(Card foundationCard : foundation.get(suitIndex)) {
                System.out.print(foundationCard + " ");
            }

            suitIndex++;
            System.out.println();
        }
    }

    // Displays live status of Tableau Piles
    public void renderTableau(List<List<Card>> tableau) {
        for (int i = 0; i < tableau.size(); i++) {
            List<Card> pile = tableau.get(i);
            System.out.print("Pile " +  (i + 1) + ": ");
            for (Card card : pile) {
                System.out.print(card + " ");
            }
            System.out.println();
        }
    }
}
