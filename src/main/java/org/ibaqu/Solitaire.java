package org.ibaqu;

import java.sql.SQLOutput;
import java.util.*;

public class Solitaire {

    private Deck deck;
    private List<List<Card>> tableau;
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

            List<Card> pile = new ArrayList<>();
            // Add j number of cards for the i'th tableau
            for (int j = 0; j <= i; j++) {
                // Draw card
                Card tableauCard = deck.drawCard();

                // If card is intended to be the top of the tableau, flip it
                if (j == i) {
                    tableauCard.flip();
                }

                // Add the card to the pile
                pile.add(tableauCard);
            }

            // Add setup pile to the tableau
            tableau.add(pile);
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
        6. Foundation to Tableau
     */
    public void moveCard(String instruction){
        String regex_StockToWaste = "([dD])";

        String regex_WasteToTableau = "([wW][tT][1-7])";
        String regex_TableauToTableau = "([tT][1-7][tT][1-7])";
        String regex_FoundationToTableau = "([hdscHDSC][tT][1-7])";

        String regex_WasteToFoundation = "([wW][hdscHDSC])";
        String regex_TableauToFoundation = "([tT][1-7][hdscHDSC])";

        if (instruction.matches(regex_StockToWaste)) {
            Console.printAction("- Drawing from Stock");
            /*   - Stock to Waste
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
        }

        if(instruction.matches(regex_WasteToTableau)) {
            Console.printAction("- Moving from Waste to Tableau");

            /*  - Waste to Tableau
                If Waste is not empty :
                    Top card is moved to any tableau pile within rules

                If Waste is empty :
                    Check 'Stock to Waste'
            */

        } else if (instruction.matches(regex_TableauToTableau)) {
            Console.printAction("- Moving from Tableau [n] to Tableau [n]");

            /*  - Tableau to Tableau
                If single card :
                    Card is moved to any tableau pile within rules

                If stack of cards :
                    Stack is moved to any tableau pile within rules

                Flip leftover card in tableau if face down

                If tableau is empty :
                    Rules are disregarded
            */

        } else if (instruction.matches(regex_FoundationToTableau)) {
            Console.printAction("- Moving from Foundation to Tableau");
            /*  - Foundation to Tableau
                Single card may be moved to tableau within rules
            */
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
        // TODO - Not so important, but check for t1t1 moves. i.e Moving to same tableau.
        String validMoveRegex =
                "([dD])|" +                 // Stock to Waste
                "([wW][tT][1-7])|" +        // Waste to Tableau
                "([tT][1-7][tT][1-7])|" +   // Tableau to Tableau
                "([hdscHDSC][tT][1-7])|" +  // Foundation to Tableau
                "([wW][hdscHDSC])|" +       // Waste to Foundation
                "([tT][1-7][hdscHDSC])";    // Tableau to Foundation

        return input.matches(validMoveRegex);
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