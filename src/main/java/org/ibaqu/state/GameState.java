package org.ibaqu.state;

import org.ibaqu.model.Card;
import org.ibaqu.model.Foundation;
import org.ibaqu.model.TableauPile;

import java.util.List;

public class GameState {
    private List<Card> stock;
    private List<Card> waste;
    private List<TableauPile> tableau;
    private Foundation foundation;

    public GameState(List<Card> stock, List<Card> waste, List<TableauPile> tableau, Foundation foundation) {
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

    public Foundation getFoundation() {
        return foundation;
    }
}
