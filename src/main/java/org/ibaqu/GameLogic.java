package org.ibaqu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLogic {

    // Deck is a List of Cards
    // Tableau is a list of Tableau piles
    // Foundation is a Map of <Suit, Card> values

    protected Deck deck;
    protected List<TableauPile> tableau;
    private Foundation foundation;
    private List<Card> stock;
    private List<Card> waste;

    public GameLogic() {
        deck = new Deck();
        tableau = new ArrayList<>();
        foundation = new Foundation();
        stock = new ArrayList<>();
        waste = new ArrayList<>();

        setupGame();
    }

    public GameLogic(List<TableauPile> tableau, Foundation foundation) {
        this.tableau = tableau;
        this.foundation = foundation;
    }

    // Set up the game
    private void setupGame() {
        setupTableau();
        setupStock();
    }

    private void setupTableau() {
        // Iterate through seven tableau
        for (int i = 0; i < 7; i++) {
            TableauPile tableauPile = new TableauPile();

            // For each tableau created at the index
            // Add a card to the faceDownCards
            for (int j = 0; j <= i; j++) {
                tableauPile.addFaceDownCard(deck.drawCard());
            }

            // Flip the top card of the tableau pile
            tableauPile.flipLastCard();

            // Add the completed tableau pile to the tableau
            tableau.add(tableauPile);
        }
    }

    private void setupStock() {
        stock.addAll(deck.getCards());
    }

    // Return the current state of the game
    public GameState getGameState() {
        return new GameState(stock, waste, tableau, foundation);
    }

    // Get instructions from the user and parse into game
    public void processUserInput(String playerInput) {

        if (playerInput.equalsIgnoreCase("Q")) {
            System.out.println("\nThanks for playing! See you next time!");
            System.out.println();
            System.exit(0);
        } else if (isValidMoveRegex(playerInput)) {
            moveCard(playerInput);
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

    /*  Moving cards from :
        1. Stock to Waste
        2. Waste to Tableau
        3. Tableau to Tableau
        4. Waste to Foundation
        5. Tableau to Foundation
     */
    public void moveCard(String instruction) {
        if (instruction.matches("([dD])")) {
            moveStockToWaste();
        } else if (instruction.matches("([wW][tT][1-7])")) {
            moveWasteToTableau(instruction);
        } else if (instruction.matches("([tT][1-7][tT][1-7])")) {
            moveTableauToTableau(instruction);
        } else if (instruction.matches("([wW][hdscHDSC])")) {
            moveWasteToFoundation(instruction);
        } else if (instruction.matches("([tT][1-7][hdscHDSC])")) {
            moveTableauToFoundation(instruction);
        } else {
            Console.printError("Invalid move instruction: " + instruction);
        }
    }

    private void moveStockToWaste() {
        Console.printAction("- Drawing from Stock");

        if (stock.isEmpty() && waste.isEmpty()) {
            Console.printError("Both Stock and Waste are empty");
        } else if (stock.isEmpty()) {
            // Shuffle and move waste to stock
            Collections.shuffle(waste);
            for (Card card : waste) {
                stock.add(card.flip());
            }
            waste.clear();
        } else {
            waste.add(stock.remove(stock.size() - 1).flip());
        }
    }

    private void moveWasteToTableau(String instruction) {
        int destinationIndex = (Character.getNumericValue(instruction.charAt(2))) - 1;

        if (!waste.isEmpty()) {
            Console.printAction("- Moving from Waste to Tableau");
            TableauPile tableauPile = tableau.get(destinationIndex);
            Card tableauCard = tableauPile.getLastFaceUpCard();
            Card wasteCard = waste.get(waste.size() - 1);

            if (isValidTableauMove(wasteCard, tableauCard)) {
                tableauPile.addFaceUpCard(waste.remove(waste.size() - 1));
                tableau.set(destinationIndex, tableauPile);
            } else {
                Console.printError("Cannot move card " + wasteCard + " to tableau T" + (destinationIndex + 1));
            }
        } else {
            Console.printError("Waste pile is EMPTY");
        }
    }

    private void moveTableauToTableau(String instruction) {
        int sourceIndex = (Character.getNumericValue(instruction.charAt(1))) - 1;
        int destinationIndex = (Character.getNumericValue(instruction.charAt(3))) - 1;

        if (sourceIndex == destinationIndex) {
            Console.printError("Source Tableau and Destination Tableau are the same");
        } else {
            TableauPile sourcePile = tableau.get(sourceIndex);
            TableauPile destinationPile = tableau.get(destinationIndex);
            Card sourceCard = sourcePile.getFirstFaceUpCard();
            Card destinationCard = destinationPile.getLastFaceUpCard();

            if (isValidTableauMove(sourceCard, destinationCard)) {
                Console.printAction("- Moving from Tableau " + (sourceIndex + 1)
                        + " to Tableau " + (destinationIndex + 1));
                for (Card sourceCards : sourcePile.getFaceUpCards()) {
                    destinationPile.addFaceUpCard(sourceCards);
                }
                sourcePile.removeAllFaceUpCards();
            } else {
                Console.printError("Cannot move card " + sourceCard + " to tableau T" + (destinationIndex + 1));
            }
        }
    }

    private void moveWasteToFoundation(String instruction) {
        if (!waste.isEmpty()) {
            Card wasteCard = waste.get(waste.size() - 1);
            Suit foundationSuit = getFoundationSuit(instruction.charAt(1));

            if (wasteCard.getSuit().equals(foundationSuit)) {
                Card topCard = foundation.getTopCard(foundationSuit);

                if (topCard == null) {
                    if (wasteCard.getRank().equals(Rank.ACE)) {
                        foundation.addCard(foundationSuit, wasteCard);
                        waste.remove(waste.size() - 1);
                    } else {
                        Console.printError("Card is not an ACE card. Cannot add to foundation");
                    }
                } else if (wasteCard.getRank().getValue() == topCard.getRank().getValue() + 1) {
                    foundation.addCard(foundationSuit, wasteCard);
                    waste.remove(waste.size() - 1);
                } else {
                    Console.printError("Card is not the right rank");
                }
            } else {
                Console.printError("Not the correct foundation suit");
            }
        } else {
            Console.printError("Waste is empty");
        }
    }

    public void moveTableauToFoundation(String instruction) {
        Console.printAction("- Moving from Tableau to Foundation");

        int sourceTableauIndex = Character.getNumericValue(instruction.charAt(1)) - 1;
        Suit foundationSuit = getFoundationSuit(instruction.charAt(2));
        Card tableauCard = tableau.get(sourceTableauIndex).getLastFaceUpCard();

        if (tableauCard.getSuit().equals(foundationSuit)) {
            Card topCard = foundation.getTopCard(foundationSuit);

            if (topCard == null) {
                if (tableauCard.getRank().equals(Rank.ACE)) {
                    foundation.addCard(foundationSuit, tableauCard);
                    tableau.get(sourceTableauIndex).removeLastFaceUpCard();
                } else {
                    Console.printError("Card is not an ACE card. Cannot add to foundation");
                }
            } else if (tableauCard.getRank().getValue() == topCard.getRank().getValue() + 1) {
                foundation.addCard(foundationSuit, tableauCard);
                tableau.get(sourceTableauIndex).removeLastFaceUpCard();
            } else {
                Console.printError("Card is not the right rank");
            }
        } else {
            Console.printError("Card is not the correct foundation suit");
        }
    }

    private Suit getFoundationSuit(char suitChar) {
        switch (Character.toLowerCase(suitChar)) {
            case 'h': return Suit.HEARTS;
            case 'd': return Suit.DIAMONDS;
            case 'c': return Suit.CLUBS;
            case 's': return Suit.SPADES;
            default: throw new IllegalArgumentException("Invalid foundation suit: " + suitChar);
        }
    }


    private boolean isValidTableauMove(Card source, Card target) {
        // If the tableau is empty, the target card would be null
        if (target == null) {
            return true;
        }

        // Is the color same?
        boolean isColorSame = source.getSuit().getColor().equals(target.getSuit().getColor());

        // Is source rank equal to (target rank - 1)
        boolean isRanked = (source.getRank().getValue() == target.getRank().getPreviousRank());

        // If Color is not the same, and the rank is right
        return !isColorSame && isRanked;
    }

}
