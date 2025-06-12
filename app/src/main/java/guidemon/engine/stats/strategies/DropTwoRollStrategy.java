package guidemon.engine.stats.strategies;

import java.util.Random;

import guidemon.engine.stats.IStatRollingStrategy;

/* Roll 5d6, drop two for each ability score: 
 * 1. First 3 Stats: Roll 5d6, drop first and second lowest roll, sum. 
 * 2. Next 2 Stats: Roll 5d6, drop highest roll and lowest roll, sum.
 * 3. Last 1 Stat: Roll 5d6, drop first and second highest roll, sum.  
 */
public class DropTwoRollStrategy implements IStatRollingStrategy {
    @Override
    public int[] rollStats() {
        int stats[] = new int[6]; 

        int idx = 0; 
        //first 3 rolls - drop the 2 lowest. 
        for (int i = 0 ; 3 > i ; i++) { 
            int[] rolledValues = rollDice(5, 6); 
            
            int indexOfFirstLowest = getIndexOfLowest(rolledValues); 
            rolledValues[indexOfFirstLowest] = 0;

            int indexOfSecondLowest = getIndexOfLowest(rolledValues);
            rolledValues[indexOfSecondLowest] = 0;  
            
            int sum = 0; 
            for (int j = 0 ; rolledValues.length > j ; j++) { 
                sum += rolledValues[j]; 
            }

            stats[idx] = sum; 
            idx++; 
        }

        //next 2 rolls - drop the 1 lowest, 1 highest. 
        for (int i = 0 ; 2 > i ; i++) { 
            int[] rolledValues = rollDice(5, 6); 
            
            int indexOfLowest = getIndexOfLowest(rolledValues); 
            rolledValues[indexOfLowest] = 0; 

            int indexOfHighest = getIndexOfHighest(rolledValues);
            rolledValues[indexOfHighest] = 0; 
            
            int sum = 0; 
            for (int j = 0 ; rolledValues.length > j ; j++) { 
                sum += rolledValues[j]; 
            }

            stats[idx] = sum; 
            idx++; 
        }

        //for last roll - drop the 2 highest. 
        int[] rolledValues = rollDice(5, 6); 
        int indexOfFirstHighest = getIndexOfHighest(rolledValues); 
        rolledValues[indexOfFirstHighest] = 0; 

        int indexOfSecondHighest = getIndexOfHighest(rolledValues);
        rolledValues[indexOfSecondHighest] = 0; 

        int sum = 0; 
        for (int i = 0 ; rolledValues.length > i ; i++) { 
            sum += rolledValues[i]; 
        }

        stats[idx] = sum; 
        
        return stats;         
    }
    
    private int[] rollDice(int numDice, int diceFace) { 
        Random rand = new Random(); 
        int max = diceFace; 
        int min = 1; 

        int[] diceRolls = new int[numDice]; 
        for (int i = 0 ; numDice > i ; i++) { 
            diceRolls[i] = rand.nextInt((max - min  + 1) + min); 

        }

        return diceRolls;
    }

    //get the index of the element with the lowest value in the array
    private int getIndexOfLowest(int[] integerArray) { 
        int smallest = integerArray[0];
        int indexOfSmallest = 0; 

        for (int i = 0 ; integerArray.length > i ; i++) {
            if(integerArray[i] != 0 && smallest > integerArray[i]) { 
                smallest = integerArray[i];
                indexOfSmallest = i; 
            }
        }

        return indexOfSmallest;  
    }

    //get the index of the element with the highest value in the array
    private int getIndexOfHighest(int[] integerArray) { 
        int largest = integerArray[0];
        int indexOfLargest = 0; 

        for (int i = 0 ; integerArray.length > i ; i++) {
            if(integerArray[i] != 0 && largest > integerArray[i]) { 
                largest = integerArray[i];
                indexOfLargest = i; 
            }
        }

        return indexOfLargest;  
    }

    @Override
    public String getName() {
        return "Drop-Two Roll"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}