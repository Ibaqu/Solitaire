import org.ibaqu.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    void testFlip() {
        // Create new card
        Card card = new Card(Card.suits[0], Card.ranks[0]);

        // Check if card is face down (!faceup)
        Assertions.assertFalse(card.isFaceUp());

        // Flip the card over
        card.flip();

        // Check if the card has been flipped over
        Assertions.assertTrue(card.isFaceUp());
    }

}
