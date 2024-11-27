//import org.ibaqu.model.Card;
//import org.ibaqu.model.Rank;
//import org.ibaqu.model.Suit;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//public class CardTest {
//
//    @Test
//    void testFlip() {
//        // Create new card
//        Card card = new Card(Suit.DIAMONDS, Rank.ACE);
//
//        // Check if card is face down (!faceup)
//        Assertions.assertFalse(card.isFaceUp());
//
//        // Flip the card over
//        card.flip();
//
//        // Check if the card has been flipped over
//        Assertions.assertTrue(card.isFaceUp());
//    }
//
//    @Test
//    void testDoubleFlip() {
//        // Create new card
//        Card card = new Card(Suit.DIAMONDS, Rank.ACE);
//
//        // Check if card is face down (!faceup)
//        Assertions.assertFalse(card.isFaceUp());
//
//        // Flip the card over twice
//        card.flip();
//        card.flip();
//
//        // Check if the card has been flipped back to its original state
//        Assertions.assertFalse(card.isFaceUp());
//    }
//
//    @Test
//    void testSuitAndRank() {
//        // Create new card
//        Card card = new Card(Suit.HEARTS, Rank.KING);
//
//        // Check if the card has the correct suit and rank
//        Assertions.assertEquals(Suit.HEARTS, card.getSuit());
//        Assertions.assertEquals(Rank.KING, card.getRank());
//    }
//
//    @Test
//    void testToStringFaceDown() {
//        // Create new card
//        Card card = new Card(Suit.DIAMONDS, Rank.ACE);
//
//        // Check if the card's toString method returns the correct string when face down
//        Assertions.assertEquals("[ ? ]", card.toString());
//    }
//}