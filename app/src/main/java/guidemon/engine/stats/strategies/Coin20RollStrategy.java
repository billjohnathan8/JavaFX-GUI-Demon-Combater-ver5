package guidemon.engine.stats.strategies;

import java.util.Random;

import guidemon.engine.stats.IStatRollingStrategy;

/* 1. Roll 20d2 (heads or tails)
 * 2. Count the number of heads / the number of 2s or even numbers that appeared
 * 3. The total number of heads / number of 2s or even numbers that appeared = value determined for one stat
 * 4. Repeat for all 6 stats 
 */
public class Coin20RollStrategy implements IStatRollingStrategy {
    @Override
    public int[] rollStats() {
        int[] stats = new int[6]; 

        for (int i = 0 ; stats.length > i ;  i++) { 
            stats[i] = rollForIndividualScore(); 
        }

        return stats; 
    } 

    private int rollForIndividualScore() { 
        Random rand = new Random(); 
        int max = 2; // tails 
        int min = 1; //heads 

        int numRolls = 20; 

        int heads = 0;

        for (int i = 0 ; numRolls > i ; i++) { 
            int coinFlipValue = rand.nextInt((max - min + 1) + min);

            if(coinFlipValue == 1) { 
                heads++; 
            }
        }

        return heads; 
    }

    @Override
    public String getName() {
        return "Coin20 Roll"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}
