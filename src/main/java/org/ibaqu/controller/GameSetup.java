package org.ibaqu.controller;

import org.ibaqu.model.Card;
import org.ibaqu.model.Deck;
import org.ibaqu.model.Foundation;
import org.ibaqu.model.TableauPile;

import java.util.ArrayList;
import java.util.List;

public class GameSetup {
    private Deck deck;
    private List<TableauPile> tableau;
    private Foundation foundation;
    private List<Card> stock;
    private List<Card> waste;

    public GameSetup(Deck deck) {
        this.deck = deck;
        this.tableau = new ArrayList<>();
        this.foundation = new Foundation();
        this.stock = new ArrayList<>();
        this.waste = new ArrayList<>();
    }

    public void setupGame() {
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

    public List<TableauPile> getTableau() {
        return tableau;
    }

    public Foundation getFoundation() {
        return foundation;
    }

    public List<Card> getStock() {
        return stock;
    }

    public List<Card> getWaste() {
        return waste;
    }
}
