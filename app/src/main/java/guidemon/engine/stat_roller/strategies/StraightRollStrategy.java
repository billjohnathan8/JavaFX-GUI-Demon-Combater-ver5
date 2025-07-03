package guidemon.engine.stat_roller.strategies;

import java.util.Random;

import guidemon.engine.stat_roller.IStatRollingStrategy;

/* 1. Roll 3d6
 * 2. Sum all rolled values
 * 3. Take this value for each ability score 
 */
public class StraightRollStrategy implements IStatRollingStrategy {
    @Override
    public int[] rollStats() {
        int[] stats = new int[6]; 

        Random rand = new Random(); 
        int max = 6; 
        int min = 1; 

        for (int i = 0 ; stats.length > i ; i++) {
            stats[i] = rand.nextInt((max - min + 1) + min); 
        }

        return stats; 
    } 

    @Override
    public String getName() {
        return "Straight Roll"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}