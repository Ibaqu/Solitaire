//import org.ibaqu.controller.GameLogic;
//import org.ibaqu.model.*;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GameLogicTest {
//
//    @Test
//    void testValidAceTableauToFoundationMove() {
//
//        // -- Setup
//        // Create a single tableau
//        TableauPile tableauPile = new TableauPile();
//        List<TableauPile> tableauPiles = new ArrayList<>();
//        tableauPiles.add(tableauPile);
//
//        // Populate tableau with ACE card
//        Card card = new Card(Suit.HEARTS, Rank.ACE);
//        tableauPile.addFaceUpCard(card);
//
//        // Create Foundation
//        Foundation foundation = new Foundation();
//
//        // Instantiate a testing Gamelogic object
//        GameLogic gameLogicTest = new GameLogic(tableauPiles, foundation);
//
//        // Attempt to move ACE card from a Tableau pile to Foundation
//        //gameLogicTest.moveTableauToFoundation("T1H");
//
//        // -- ASSERTIONS --
//
//        // Check if foundation HEARTS is not empty
//        Assertions.assertNotEquals(null, foundation.getTopCard(Suit.HEARTS));
//
//        // Check if foundation HEARTS pile only has HEARTS card
//        Assertions.assertEquals(card.getSuit(), foundation.getTopCard(Suit.HEARTS).getSuit());
//
//        // Check if the foundation HEARTS pile has an ACE card
//        Assertions.assertEquals(card.getRank(), foundation.getTopCard(Suit.HEARTS).getRank());
//    }
//}
