package org.ibaqu.controller;

import org.ibaqu.view.Console;
import org.ibaqu.state.GameState;
import org.ibaqu.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLogic {

    // Deck is a List of Cards
    // Tableau is a list of Tableau piles
    // Foundation is a Map of <Suit, Card> values

    protected Deck deck;
    protected List<TableauPile> tableau;
    private GameSetup gameSetup;
    private CardMover cardMover;

    public GameLogic() {
        Deck deck = new Deck();
        gameSetup = new GameSetup(deck);
        gameSetup.setupGame();
        cardMover = new CardMover(gameSetup.getTableau(), gameSetup.getFoundation(), gameSetup.getStock(), gameSetup.getWaste());
    }

    // Return the current state of the game
    public GameState getGameState() {
        return new GameState(gameSetup.getStock(), gameSetup.getWaste(), gameSetup.getTableau(), gameSetup.getFoundation());
    }

    public void processUserInput(String playerInput) {
        if (playerInput.equalsIgnoreCase("Q")) {
            System.out.println("\nThanks for playing! See you next time!");
            System.out.println();
            System.exit(0);
        } else if (isValidMoveRegex(playerInput)) {
            cardMover.moveCard(playerInput);
        } else if (isGameWon()) {
            Console.printAction("Game has been WON!");
        } else {
            Console.printError("Invalid choice");
        }
    }

    private boolean isValidMoveRegex(String input) {
        String validMoveRegex =
                        "([dD])|" +                 // Stock to Waste
                        "([wW][tT][1-7])|" +        // Waste to Tableau
                        "([tT][1-7][tT][1-7])|" +   // Tableau to Tableau
                        "([wW][hdscHDSC])|" +       // Waste to Foundation
                        "([tT][1-7][hdscHDSC])";    // Tableau to Foundation

        return input.matches(validMoveRegex);
    }

    public boolean isGameWon() {
        // Check if all foundation piles are full (each contains 13 cards)
        for (Suit suit : Suit.values()) {
            if (!gameSetup.getFoundation().getTopCard(suit).getRank().equals(Rank.KING)) {
                return false;
            }
        }
        return true;  // All foundation piles are full, so the game is won
    }

}
