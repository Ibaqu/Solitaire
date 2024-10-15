package org.ibaqu;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}