package guidemon.engine.stats.strategies;

import java.util.Random;

import guidemon.engine.stats.IStatRollingStrategy;

/* 1. Roll 3d6
 * 2. Sum all rolled values 
 * 3. Repeat 7 times
 * 4. Drop the lowest bundled sum of rolled values of the 7 times
 * 5. Distribute the remaining 6 across the ability scores
 */
public class DropOneRollStrategy implements IStatRollingStrategy {
    @Override
    public int[] rollStats() {
        int[] stats = new int[6]; 

        //get the first 3d6 x7
        int[] temp = new int[7]; 

        for (int i = 0 ; temp.length > i ; i++) { 
            temp[i] = rollForIndividualScore(); 
        }

        //drop the lowest 3d6 value
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
        //setup 3d6 (minDiceFace = 1, maxDiceFace = 6)
        Random rand = new Random();
        int max = 6; 
        int min = 1; 
        int numRolls = 3; 

        int output = 0; 

        //roll and sum 3d6
        for (int i = 0 ; numRolls > i ; i++) { 
            output += rand.nextInt((max - min + 1) + min); 
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
        return "Drop-One Roll"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}