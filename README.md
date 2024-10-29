# Solitaire

Solitaire game in Java

## How to Play the Game : 

- The game consists of 4 piles : Stock, Waste, Foundation and Tableau
- Stock pile contains cards that are face down and can be drawn onto the Waste pile
- Waste pile displays a face up card that can be used to progress the game
- The Tableau consists of seven tableau piles which are used to contain cards
- The goal of the game is to finish a suit of cards in the Foundation pile using the cards in the Tableau
- Only opposite suits in descending order or rank can be placed on the Tableau piles
- Only the same suit can be placed in ascending order can be placed on the Foundation pile

### Commands : 

- The Tableau piles are indexed from 1 - 7
- The following commands can be used :

 `D`                 // Draw from Stock to Waste
 `WT(n)`             // Move from Waste to Tableau (n)
 `T(n)T(m)`         // Move card from Tableau(n) to Tableau(m)
 `WH`,`WS`          // Move card from Waste to Foundation (suit)
 `T(n)D`            // Move card Tableau(n) to Foundation (suit)
