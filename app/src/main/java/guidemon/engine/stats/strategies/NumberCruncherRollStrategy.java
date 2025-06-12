package guidemon.engine.stats.strategies;

import java.util.Random;

import guidemon.engine.stats.IStatRollingStrategy;

/* 1. Roll 4d6
 * 2. Drop the lowest of the rolled d6 values
 * 3. Sum all remaining values
 * 4. Repeat 7 times
 * 5. Drop the lowest bundled sum of rolled values of the 7 times
 * 6. Distribute the remaining 6 across the ability scores
 */
public class NumberCruncherRollStrategy implements IStatRollingStrategy {
    @Override
    public int[] rollStats() {
        int[] stats = new int[6]; 

        //get the scores from 4d6 drop-lowest x7
        int[] temp = new int[7]; 

        for (int i = 0 ; temp.length > i ; i++) { 
            temp[i] = rollForIndividualScore(); 
        }

        //drop the lowest overall value from the 4d6 drop-lowest
        int indexOfLowest = getIndexOfLowest(temp);
        temp[indexOfLowest] = 0; 

        //copy temp -> stats (for final stats value)
        int idx = 0; 
        for (int i = 0 ; temp.length > i ; i++) { 
            if(temp[i] != 0) { 
                stats[idx] = temp[i]; 
                idx++;
            }
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
        return "Number Cruncher's Roll"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}
