package org.ibaqu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLogic {

    protected Deck deck;
    protected List<TableauPile> tableau;
    private FoundationPile foundation;
    private List<Card> stock;
    private List<Card> waste;

    public GameLogic() {
        deck = new Deck();
        tableau = new ArrayList<>();
        foundation = new FoundationPile();
        stock = new ArrayList<>();
        waste = new ArrayList<>();

        setupGame();
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
    public void moveCard(String instruction){
        String regex_StockToWaste = "([dD])";

        String regex_WasteToTableau = "([wW][tT][1-7])";
        String regex_TableauToTableau = "([tT][1-7][tT][1-7])";

        String regex_WasteToFoundation = "([wW][hdscHDSC])";
        String regex_TableauToFoundation = "([tT][1-7][hdscHDSC])";

        if (instruction.matches(regex_StockToWaste)) {
            Console.printAction("- Drawing from Stock");
            /*   - Stock to Waste :: Command : D
                If Stock is empty :
                    Shuffle contents of Waste into Stack
                Top card is moved from Stock to Waste (after flipping)
            */

            if (stock.isEmpty()) {
                // Shuffle waste cards
                Collections.shuffle(waste);

                // Add all waste cards to Stock
                // TODO : Improve the functionality of flipping all cards before adding them to stock
                for (Card card : waste) {
                    stock.add(card.flip());
                }

                // Remove all waste cards
                waste.clear();
            }

            // Remove top of stock card and add to waste
            waste.add(stock.remove(stock.size() - 1).flip());
        } else if(instruction.matches(regex_WasteToTableau)) {
            /*  - Waste to Tableau :: Command : WT1 ... WT7
                If Waste is not empty :
                    Top card is moved to any tableau pile within rules

                If Waste is empty :
                    Check 'Stock to Waste'
            */

            // Calculate the index based on the instruction provided
            int destinationIndex = (Character.getNumericValue(instruction.charAt(2))) - 1;

            // If waste is not empty
            if (!waste.isEmpty()) {
                Console.printAction("- Moving from Waste to Tableau");

                // Get the tableau pile at the index
                TableauPile tableauPile = tableau.get(destinationIndex);

                // Get the tableau card
                Card tableauCard = tableauPile.getLastFaceUpCard();

                // Get the card in the waste pile
                Card wasteCard = waste.get(waste.size() - 1);

                // Check if wasteCard can be placed on tableau
                if (isValidTableauMove(wasteCard, tableauCard)) {
                    // Add the waste card
                    tableauPile = tableauPile.addFaceUpCard(waste.remove(waste.size() - 1));
                    // Set the tableau
                    tableau.set(destinationIndex, tableauPile);
                } else {
                    Console.printError("Cannot move card " + wasteCard + " to tableau T" + (destinationIndex + 1));
                }
            } else {
                Console.printError("Waste pile is EMPTY");
            }
        } else if (instruction.matches(regex_TableauToTableau)) {
            /*  - Tableau to Tableau :: Command T1T2
                Try to move the entire faceUpCards linked list to the destination within the rules
                The logic is that all the faceUpCards would have formed only if the rules are followed

                If tableau is empty :
                    Rules are disregarded
            */

            // Calculate the source tableau index and destination tableau index
            int sourceIndex = (Character.getNumericValue(instruction.charAt(1))) - 1;
            int destinationIndex = (Character.getNumericValue(instruction.charAt(3))) - 1;

            // If the indexes are same, print an error
            if (sourceIndex == destinationIndex) {
                Console.printError("Source Tableau and Destination Tableau are the same");
            } else {
                // Get the tableau pile at source index and destination index
                TableauPile sourcePile = tableau.get(sourceIndex);
                TableauPile destinationPile = tableau.get(destinationIndex);

                // Get first upturned card in source pile
                Card sourceCard = sourcePile.getFirstFaceUpCard();
                // Get last upturned card in destination pile
                Card destinationCard = destinationPile.getLastFaceUpCard();

                // Check if source tableau cards can be placed at the destination
                if (isValidTableauMove(sourceCard, destinationCard)) {
                    // Move all faceup cards from the source to the destination
                    Console.printAction("- Moving from Tableau " + (sourceIndex + 1)
                            + "to Tableau " + (destinationIndex + 1));

                    // Go through all faceup cards at the source pile
                    for (Card sourceCards : sourcePile.getFaceUpCards()) {
                        // Add cards to the destination pile one by one
                        destinationPile.addFaceUpCard(sourceCards);
                    }

                    // Remove all faceup cards from the source pile
                    sourcePile.removeAllFaceUpCards();
                } else {
                    Console.printError("Cannot move card " + sourceCard + " to tableau T" + (destinationIndex + 1));
                }
            }
        } else if (instruction.matches(regex_WasteToFoundation)) {
            /*  - Waste to Foundation
                If foundation is empty
                    Ace can be placed
                else
                    Single card can be moved to Foundation within rules

                If moving a card results in tableau becoming empty, the next card in pile should be flipped
            */

            if (!waste.isEmpty()) {
                // Get WasteCard
                Card wasteCard = waste.get(waste.size() - 1);

                // Get Destination Foundation Pile
                String foundationInstruction = String.valueOf(instruction.charAt(1)).toLowerCase();

                // h d c s
                Suit foundationSuit = null;

                switch (foundationInstruction) {
                    case "h" : {
                        foundationSuit = Suit.HEARTS;
                        break;
                    }

                    case "d" : {
                        foundationSuit = Suit.DIAMONDS;
                        break;
                    }

                    case "c" : {
                        foundationSuit = Suit.CLUBS;
                        break;
                    }

                    case "s" : {
                        foundationSuit = Suit.SPADES;
                        break;
                    }
                }

                // If the waste card is the same suit, then continue
                if (wasteCard.getSuit().equals(foundationSuit)) {
                    Card topCard = foundation.getTopCard(foundationSuit);

                    // If Foundation is empty
                    if (topCard == null) {
                        // Only add if waste card is an ACE
                        if (wasteCard.getRank().equals(Rank.ACE)) {
                            // Add the ace card to foundation
                            foundation.addCard(foundationSuit, wasteCard);
                            // Remove wasteCard
                            waste.remove(waste.size() - 1);
                        } else {
                            Console.printError("Card is not an ACE card. Cannot add to foundation");
                        }
                    } else {
                        if (wasteCard.getRank().getValue() == topCard.getRank().getValue() + 1) {
                            // Add the card to the foundation
                            foundation.addCard(foundationSuit, wasteCard);
                            // Remove the waste card
                            waste.remove(waste.size() - 1);
                        } else {
                            Console.printError("Card is not the right rank");
                        }
                    }
                } else {
                    Console.printError("Not the correct foundation suit");
                }
            } else {
                Console.printError("Waste is empty");
            }
        } else if (instruction.matches(regex_TableauToFoundation)) {
            Console.printAction("- Moving from Tableau to Foundation");
            /*  - Tableau to Foundation
                If foundation is empty
                    Ace can be placed
                else
                    Single card can be moved to Foundation within rules
            */

            // Get Source tableau and Destination foundation
            // t1h, t2S
            // Get Tableau top card
            int sourceTableauIndex = (int) instruction.charAt(1);

            String foundationInstruction = String.valueOf(instruction.charAt(2)).toLowerCase();

            // h d c s
            Suit foundationSuit = null;

            switch (foundationInstruction) {
                case "h" : {
                    foundationSuit = Suit.HEARTS;
                    break;
                }

                case "d" : {
                    foundationSuit = Suit.DIAMONDS;
                    break;
                }

                case "c" : {
                    foundationSuit = Suit.CLUBS;
                    break;
                }

                case "s" : {
                    foundationSuit = Suit.SPADES;
                    break;
                }
            }

            Card tableauCard = tableau.get(sourceTableauIndex).getLastFaceUpCard();

            // Check suit
            if (tableauCard.getSuit().equals(foundationSuit)) {
                Card topCard = foundation.getTopCard(foundationSuit);

                // If foundation is empty only ACE cards can be placed
                if (topCard == null) {
                    // Only add if tableau card is an ACE
                    if (tableauCard.getRank().equals(Rank.ACE)) {
                        // Add the ace card to foundation
                        foundation.addCard(foundationSuit, tableauCard);
                        // Remove the last faceUpCards in tableau
                        tableau.get(sourceTableauIndex).removeLastFaceUpCard();
                    } else {
                        Console.printError("Card is not an ACE card. Cannot add to foundation");
                    }
                } else {
                    if (tableauCard.getRank().getValue() == topCard.getRank().getValue() + 1) {
                        // Add the faceup card to foundation
                        foundation.addCard(foundationSuit, tableauCard);
                        // Remove the all faceUpCards in tableau
                        tableau.get(sourceTableauIndex).removeLastFaceUpCard();
                    } else {
                        Console.printError("Card is not the right rank");
                    }
                }
            } else {
                Console.printError("Card is not the correct foundation suit");
            }
        }
    }

    private boolean isValidTableauMove(Card source, Card target) {
        // Is the color same?
        boolean isColorSame = source.getSuit().getColor().equals(target.getSuit().getColor());

        // Is source rank equal to (target rank - 1)
        boolean isRanked = (source.getRank().getValue() == target.getRank().getPreviousRank());

        // If Color is not the same, and the rank is right
        return !isColorSame && isRanked;
    }

}
