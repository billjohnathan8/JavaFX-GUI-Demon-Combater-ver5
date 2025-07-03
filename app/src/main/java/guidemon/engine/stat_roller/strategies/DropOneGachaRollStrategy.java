package guidemon.engine.stat_roller.strategies;

import java.util.Random;

import guidemon.engine.stat_roller.IStatRollingStrategy;

/* 1. Roll 1d20
 * 2. Repeat 7 times
 * 3. Drop the lowest rolled value of the 7 times
 * 4. Distribute the remaining 6 across the ability scores
 */
public class DropOneGachaRollStrategy implements IStatRollingStrategy {
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
        Random rand = new Random(); 
        int max = 20;
        int min = 1; 

        int output = rand.nextInt((max - min + 1) + min); 

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
        return "Drop-One Gacha Roll"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}