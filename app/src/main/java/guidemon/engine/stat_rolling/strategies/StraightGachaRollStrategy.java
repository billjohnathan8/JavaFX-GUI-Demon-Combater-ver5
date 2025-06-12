package guidemon.engine.stat_rolling.strategies;

import java.util.Random;

import guidemon.engine.stat_rolling.IStatRollingStrategy;

/* 1. Roll 1d20
 * 2. Take this value for each ability score 
 */
public class StraightGachaRollStrategy implements IStatRollingStrategy {
    @Override
    public int[] rollStats() {
        int[] stats = new int[6]; 

        Random rand = new Random(); 
        int max = 20; 
        int min = 1; 

        for (int i = 0 ; stats.length > i ; i++) {
            stats[i] = rand.nextInt((max - min + 1) + min); 
        }

        return stats; 
    } 

    @Override
    public String getName() {
        return "Straight Gacha Roll"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}