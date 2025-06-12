package guidemon.engine.stats.strategies;

import java.util.Random;

import guidemon.engine.stats.IStatRollingStrategy;

/* 1. Roll 4d6
 * 2. Drop the lowest rolled d6 value
 * 3. Sum all remaining values
 * 4. Repeat for all 6 stats. 
 */
public class DND5EStandardRollStrategy implements IStatRollingStrategy {
    @Override
    public int[] rollStats() {
        int[] stats = new int[6]; 
        for (int i = 0 ; stats.length > i ; i++) { 
            stats[i] = rollForIndividualScore(); 
        }

        return stats; 
    }

    //roll each individual score 
    private int rollForIndividualScore() { 
        //setup 4d6 (minDiceFace = 1, maxDiceFace = 6)
        Random rand = new Random();
        int max = 6; 
        int min = 1; 
        int numRolls = 4; 

        //roll 4d6 
        int[] statRolls = new int[numRolls]; 
        for (int i = 0 ; numRolls > i ; i++) { 
            statRolls[i] = rand.nextInt((max - min + 1) + min); 
        }

        //remove lowest roll / drop the lowest 
        int indexOfLowest = getIndexOfLowest(statRolls); 
        statRolls[indexOfLowest] = 0; 

        //sum the rolls
        int output = 0;
        for (int i = 0 ; numRolls > i ; i++) { 
            output += statRolls[i]; 
        }

        return output; 
    }

    //get the index of the element with the lowest value in the array
    private int getIndexOfLowest(int[] integerArray) { 
        int smallest = integerArray[0];
        int indexOfSmallest = 0; 

        for (int i = 0 ; integerArray.length > i ; i++) {
            if(smallest > integerArray[i]) { 
                smallest = integerArray[i];
                indexOfSmallest = i; 
            }
        }

        return indexOfSmallest;  
    }

    @Override
    public String getName() {
        return "D&D 5e Standard Roll"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}