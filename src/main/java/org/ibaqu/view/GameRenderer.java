package org.ibaqu.view;

import org.ibaqu.model.Card;
import org.ibaqu.model.Foundation;
import org.ibaqu.model.Suit;
import org.ibaqu.model.TableauPile;
import org.ibaqu.state.GameState;

import java.util.List;

public class GameRenderer {

    public void renderGame(GameState gameState) {

        System.out.println("    -----     Stock and Waste Piles     ----- ");
        renderStock(gameState.getStock());
        renderWaste(gameState.getWaste());

        System.out.println("    -----     Foundation Piles     ----- ");
        renderFoundationPile(gameState.getFoundation());

        System.out.println("    -----     Tableau     ----- ");
        renderTableau(gameState.getTableau());
    }


    public void renderStock(List<Card> stock) {
        if(!stock.isEmpty()) {
            System.out.println("Stock (S) : [" + stock.size() + "] cards");
        } else {
            System.out.println("Stock (S) : Empty");
        }
    }

    public void renderWaste(List<Card> waste) {
        if(!waste.isEmpty()) {
            System.out.println("Waste (W) : [" + waste.size() + "] : " + waste.get(waste.size() - 1));
        } else {
            System.out.println("Waste (W) : Empty");
        }
    }

    // Displays live status of Foundation Piles
    public void renderFoundationPile(Foundation foundation) {

        for (Suit suit : Suit.values()) {
            Card topCard = foundation.getTopCard(suit);
            System.out.println(suit + ": " + ((topCard != null) ? topCard : "Empty"));
        }
    }

    // Displays live status of Tableau Piles
    public void renderTableau(List<TableauPile> tableau) {

        for (int i = 0; i < tableau.size(); i++) {
            // Get the tableau pile at the index
            TableauPile tableauPile = tableau.get(i);
            System.out.print("T" + (i + 1) + " : ");

            // Check and flip the last card for the tableau if applicable
            tableauPile.flipLastCard();

            // Display the faceDownCards
            for (Card card : tableauPile.getFaceDownCards()) {
                System.out.print(card + " ");
            }
            // Display the faceUpCards
            for (Card card : tableauPile.getFaceUpCards()) {
                System.out.print(card + " ");
            }
            System.out.println();
        }
    }
}
