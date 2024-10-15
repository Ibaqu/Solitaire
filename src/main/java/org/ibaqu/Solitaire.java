package org.ibaqu;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solitaire {

    private Deck deck;
    private List<List<Card>> tableau;
    private List<Stack<Card>> foundations;
    private List<Card> stock;
    private List<Card> waste;

    public Solitaire() {
        deck = new Deck();
        tableau = new ArrayList<>();
        foundations = new ArrayList<>();
        stock = new ArrayList<>();
        waste = new ArrayList<>();

        // Setup Cards into the tableau
        setupGame();
    }

    // Set up the game
    private void setupGame() {
        for (int i = 0; i < 7; i++) {
            List<Card> pile = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                Card topCard = deck.drawCard();
                if(j == i) topCard.flip();
                pile.add(topCard);
            }
            tableau.add(pile);
        }
        for (int i = 0; i < 4; i++) {
            Stack<Card> suit = new Stack<>();
            foundations.add(suit);
        }
        stock.addAll(deck.getCards());
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}