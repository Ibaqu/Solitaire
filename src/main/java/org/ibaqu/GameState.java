package org.ibaqu;

import java.util.List;

public class GameState {
    private List<Card> stock;
    private List<Card> waste;
    private List<TableauPile> tableau;
    private FoundationPile foundation;

    public GameState(List<Card> stock, List<Card> waste, List<TableauPile> tableau, FoundationPile foundation) {
        this.stock = stock;
        this.waste = waste;
        this.tableau = tableau;
        this.foundation = foundation;
    }

    public List<Card> getStock() {
        return stock;
    }

    public List<Card> getWaste() {
        return waste;
    }

    public List<TableauPile> getTableau() {
        return tableau;
    }

    public FoundationPile getFoundation() {
        return foundation;
    }
}
