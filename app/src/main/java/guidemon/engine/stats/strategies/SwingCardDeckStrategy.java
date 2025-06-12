package guidemon.engine.stats.strategies;

import java.util.Arrays;
import java.util.Random;

import guidemon.engine.stats.IStatRollingStrategy;

/* 1. Start with a Card Deck of 12 cards. 
 * 2. Draw 2 Cards from the deck, without replacement. 
 * 3. Sum the values on the two cards. 
 * 4. Repeat for all 6 stats. 
 * 
 * Swing Deck: riskier - but wilder numbers. 
 * Swing Deck: [1, 2, 3, 5, 6, 7, 7, 7, 8, 8, 9, 9], (lowest: 3, highest: 18)
 */
public class SwingCardDeckStrategy implements IStatRollingStrategy {
    private int[] swingDeck = {1, 2, 3, 5, 6, 7, 7, 7, 8, 8, 9, 9};  //Swing Deck
    private int[] cardDeck; 

    @Override
    public int[] rollStats() {  
        int[] stats = new int[6]; 
        cardDeck = Arrays.copyOf(swingDeck, swingDeck.length); 

        for (int i = 0 ; stats.length > i ; i++) { 
            stats[i] = rollForIndividualScore(); 
        }

        return stats; 
    }

    //roll each individual score 
    private int rollForIndividualScore() { 
        Random rand = new Random(); 
        int max = this.cardDeck.length - 1; 
        int min = 0; 

        //enforce without replacement
        int firstCardIndex; 
        int secondCardIndex; 
        do {
            firstCardIndex = rand.nextInt((max - min + 1) + min);
        } while (this.cardDeck[firstCardIndex] == -1);

        do {
            secondCardIndex = rand.nextInt((max - min + 1) + min);
        } while (secondCardIndex == firstCardIndex || this.cardDeck[secondCardIndex] == -1);

        //obtain the sum
        int sum = this.cardDeck[firstCardIndex] + this.cardDeck[secondCardIndex]; 

        //discard the cards 
        this.cardDeck[firstCardIndex] = -1; 
        this.cardDeck[secondCardIndex] = -1;
    
        return sum; 
    }

    @Override
    public String getName() {
        return "Card Deck Drawing: Swing Deck"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}
