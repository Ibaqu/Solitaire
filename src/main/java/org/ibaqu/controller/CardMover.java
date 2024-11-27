package org.ibaqu.controller;

import org.ibaqu.model.*;
import org.ibaqu.view.Console;

import java.util.Collections;
import java.util.List;

public class CardMover {
    private List<TableauPile> tableau;
    private Foundation foundation;
    private List<Card> stock;
    private List<Card> waste;

    public CardMover(List<TableauPile> tableau, Foundation foundation, List<Card> stock, List<Card> waste) {
        this.tableau = tableau;
        this.foundation = foundation;
        this.stock = stock;
        this.waste = waste;
    }

    public void moveCard(String instruction) {
        MoveType moveType = getMoveType(instruction);
        switch (moveType) {
            case STOCK_TO_WASTE:
                moveStockToWaste();
                break;
            case WASTE_TO_TABLEAU:
                moveWasteToTableau(instruction);
                break;
            case TABLEAU_TO_TABLEAU:
                moveTableauToTableau(instruction);
                break;
            case WASTE_TO_FOUNDATION:
                moveWasteToFoundation(instruction);
                break;
            case TABLEAU_TO_FOUNDATION:
                moveTableauToFoundation(instruction);
                break;
            default:
                Console.printError("Invalid move instruction: " + instruction);
        }
    }

    private MoveType getMoveType(String instruction) {
        if (instruction.matches("([dD])")) {
            return MoveType.STOCK_TO_WASTE;
        } else if (instruction.matches("([wW][tT][1-7])")) {
            return MoveType.WASTE_TO_TABLEAU;
        } else if (instruction.matches("([tT][1-7][tT][1-7])")) {
            return MoveType.TABLEAU_TO_TABLEAU;
        } else if (instruction.matches("([wW][hdscHDSC])")) {
            return MoveType.WASTE_TO_FOUNDATION;
        } else if (instruction.matches("([tT][1-7][hdscHDSC])")) {
            return MoveType.TABLEAU_TO_FOUNDATION;
        } else {
            return null;
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

    private void moveTableauToFoundation(String instruction) {
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
            case 'h':
                return Suit.HEARTS;
            case 'd':
                return Suit.DIAMONDS;
            case 'c':
                return Suit.CLUBS;
            case 's':
                return Suit.SPADES;
            default:
                throw new IllegalArgumentException("Invalid foundation suit: " + suitChar);
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
