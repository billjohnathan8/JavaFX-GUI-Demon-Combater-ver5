package guidemon.engine.stat_rolling.strategies;

import java.util.Random;

import guidemon.engine.stat_rolling.IStatRollingStrategy;

/* 1. For first three stats: Roll 1d10 + 1, add it to 8. 
 * 2. For next three stats: Roll 1d10 - 1, add it to 9. 
 * 
 * Keeps each roll between 10 - 18. 
 */
public class TenRollStrategy implements IStatRollingStrategy {
    @Override
    public int[] rollStats() {
        int[] stats = new int[6]; 
        int halfStatsLength = stats.length / 2;

        //first three stats: 8 + (1d10 + 1) 
        for (int i = 0 ; halfStatsLength > i ; i++) { 
            stats[i] = 8;

            int tempScore = rollForIndividualScore() + 1; 
            if(tempScore < 0) { 
                tempScore = 1; 

            } else if (tempScore >= 10) { 
                tempScore = 10; 
            }

            stats[i] += tempScore;  
        }

        //next three stats: 9 + (1d10 - 1) 
        for (int i = halfStatsLength ; stats.length > i ; i++) { 
            stats[i] = 9;

            int tempScore = rollForIndividualScore() - 1; 
            if(tempScore < 0) { 
                tempScore = 1; 

            } else if (tempScore >= 10) { 
                tempScore = 10; 
            }

            stats[i] += tempScore;  
        }

        return stats; 
    } 

    //roll each individual score 
    private int rollForIndividualScore() { 
        //setup 1d10 (minDiceFace = 1, maxDiceFace = 10)
        Random rand = new Random();
        int max = 10; 
        int min = 1; 

        return rand.nextInt((max - min + 1) + min); 
    }

    @Override
    public String getName() {
        return "Ten Roll"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}
