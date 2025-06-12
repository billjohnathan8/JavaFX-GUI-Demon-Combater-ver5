package guidemon.engine.stat_rolling.strategies;

import java.util.Random;

import guidemon.engine.stat_rolling.IStatRollingStrategy;

/* 1. Roll 4d20
 * 2. Drop the lowest rolled 1d20 value
 * 3. Sum all remaining values
 * 4. Divide the summed value by 2
 * 5. Round down the division if there are any remainders or decimals.
 * 6. Repeat 7 times
 * 7. Drop the lowest bundled sum of rolled values of the 7 times
 * 8. Distribute the remaining 6 across the ability scores
 */
public class NHEDropOneRollStrategy implements IStatRollingStrategy {
    @Override
    public int[] rollStats() {
        int[] stats = new int[6];
        int[] temp = new int[7]; 

        for (int i = 0 ; temp.length > i ; i++) { 
            temp[i] = rollForIndividualScore(); 
        }

        int indexOfLowest = getIndexOfLowest(temp); 
        temp[indexOfLowest] = 0; 

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
        //setup 4d20 (minDiceFace = 1, maxDiceFace = 20)
        Random rand = new Random();
        int max = 20; 
        int min = 1; 
        int numRolls = 4; 

        //roll 4d20
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

        //divide by 2 and round down
        output = (int) Math.floor(output / 2); 

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
        return "NHe Drop-One Roll"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}