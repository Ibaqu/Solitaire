package org.ibaqu;

import java.util.*;

public class Solitaire {

    private Deck deck;
    private List<TableauPile> tableau;
    private List<Stack<Card>> foundation;
    private List<Card> stock;
    private List<Card> waste;

    public Solitaire() {
        deck = new Deck();
        tableau = new ArrayList<>();
        foundation = new ArrayList<>();
        stock = new ArrayList<>();
        waste = new ArrayList<>();

        // Setup Cards into the tableau
        setupGame();
    }

    // Set up the game
    private void setupGame() {
        setupTableau();
        setupFoundation();
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
            tableauPile.flipTopCard();

            // Add the completed tableau pile to the tableau
            tableau.add(tableauPile);
        }
    }

    private void setupFoundation() {
        // Create an empty stack of cards to represent a full suit
        // TODO : Is it necessary to have a 'isSuit' function?
        for (int i = 0; i < 4; i++) {
            Stack<Card> suit = new Stack<>();
            foundation.add(suit);
        }
    }

    private void setupStock() {
        stock.addAll(deck.getCards());
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
                stock.addAll(waste);
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
            int tableauIndex = (Character.getNumericValue(instruction.charAt(2))) - 1;

            // If waste is not empty
            if (!waste.isEmpty()) {
                Console.printAction("- Moving from Waste to Tableau");

                // Get the tableau pile at the index
                TableauPile tableauPile = tableau.get(tableauIndex);

                // Get the tableau card
                Card tableauCard = tableauPile.getLastFaceUpCard();

                // Get the card in the waste pile
                Card wasteCard = waste.get(waste.size() - 1);

                // Check if wasteCard can be placed on tableau
                if (isValidTableauMove(wasteCard, tableauCard)) {
                    // Add the waste card
                    tableauPile = tableauPile.addFaceUpCard(waste.remove(waste.size() - 1));
                    // Set the tableau
                    tableau.set(tableauIndex, tableauPile);
                } else {
                    Console.printError("Cannot move card " + wasteCard + " to tableau T" + (tableauIndex + 1));
                }
            } else {
                Console.printError("Waste pile is EMPTY");
            }
        } else if (instruction.matches(regex_TableauToTableau)) {
            Console.printAction("- Moving from Tableau [n] to Tableau [n]");
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
                // Move all faceup cards from the source to the destination
                Console.printAction("- Moving from Tableau " + (sourceIndex + 1)
                        + "to Tableau " + (destinationIndex + 1));

                // Get the tableau pile at source index and destination index
                TableauPile sourcePile = tableau.get(sourceIndex);
                TableauPile destinationPile = tableau.get(destinationIndex);

                // Go through all faceup cards at the source pile
                for (Card sourceCards : sourcePile.getFaceUpCards()) {
                    // Add cards to the destination pile one by one
                    // TODO : Q. Is there a better way to do this?
                    destinationPile.addFaceUpCard(sourceCards);
                }

                // Remove all faceup cards from the source pile
                sourcePile.removeAllFaceUpCards();
            }
        } else if (instruction.matches(regex_WasteToFoundation)) {
            Console.printAction("- Moving from Waste to Foundation");
            /*  - Waste to Foundation
                If foundation is empty
                    Ace can be placed
                else
                    Single card can be moved to Foundation within rules

                If moving a card results in tableau becoming empty, the next card in pile should be flipped
            */
        } else if (instruction.matches(regex_TableauToFoundation)) {
            Console.printAction("- Moving from Tableau to Foundation");
            /*  - Tableau to Foundation
                If foundation is empty
                    Ace can be placed
                else
                    Single card can be moved to Foundation within rules
            */
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

    private boolean isValidTableauMove(Card source, Card target) {
        // Is the color same?
        boolean isColorSame = source.getSuit().getColor().equals(target.getSuit().getColor());

        // Is source rank equal to (target rank - 1)
        boolean isRanked = (source.getRank().getValue() == target.getRank().getPreviousRank());

        // If Color is the same, or the rank isn't right
        if (isColorSame || !isRanked) {
            return false;
        } else {
            return true;
        }
    }

    private void start() {
        Scanner consoleScanner = new Scanner(System.in);
        Renderer renderer = new Renderer();

        System.out.println("    -----     Stock and Waste Piles     ----- ");
        renderer.renderStock(stock);
        renderer.renderWaste(waste);

        System.out.println("    -----     Foundation Piles     ----- ");
        renderer.renderFoundationPile(foundation);

        System.out.println("    -----     Tableau     ----- ");
        renderer.renderTableau(tableau);

        System.out.println("    -----     Options     ----- ");
        if (!stock.isEmpty()) {
            System.out.println("[D] - Draw from Stock");
        } else {
            System.out.println("[D] - Reshuffle Waste into Stock and Draw again");
        }
        System.out.print("[W][T(i)] - Waste to Tableau (index)");
        System.out.print("\t[T(i)][T(i)] - Tableau (index) to Tableau (index)");
        System.out.print("\t[W][F] - Waste to Foundation");
        System.out.print("\t[T(i)][F] - Tableau (index) to Foundation");
        System.out.print("\t[F][T(i)] - Foundation to Tableau (index)");

        System.out.print("\nEnter choice: ");

        // Get input from player
        String playerInput = consoleScanner.nextLine();

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

    public static void main(String[] args) {
        Solitaire solitaire = new Solitaire();
        // TODO : Improve gameflow
        while (true) {
            solitaire.start();
        }
    }
}