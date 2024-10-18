import org.ibaqu.Card;
import org.ibaqu.Rank;
import org.ibaqu.Suit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardTest {

    @Test
    void testFlip() {
        // Create new card
        Card card = new Card(Suit.DIAMONDS, Rank.ACE);

        // Check if card is face down (!faceup)
        Assertions.assertFalse(card.isFaceUp());

        // Flip the card over
        card.flip();

        // Check if the card has been flipped over
        Assertions.assertTrue(card.isFaceUp());
    }

}
