package guidemon.engine.stat_roller.strategies;

import java.util.Random;

import guidemon.engine.stat_roller.IStatRollingStrategy;

/* 1. For First Stat: Roll 15d2. Count the total number of 2s - this is the ability score value. 
 * 2. For Second Stat: Roll 14d2. Count the total number of 2s - this is the ability score value. 
 * 3. For Third Stat: Roll 13d2. Count the total number of 2s - this is the ability score value. 
 * 4. For Fourth Stat: Roll 12d2. Count the total number of 2s - this is the ability score value. 
 * 5. For Fifth Stat: Roll 10d2. Count the total number of 2s - this is the ability score value. 
 * 6. For Sixth Stat: Roll 8d2. Count the total number of 2s - this is the ability score value. 
 * 
 * Weirdest Dice Roll - it creates a replicated standard array but dice were still rolled, resulting in a varied standard array.
 */
public class VariedStandardArrayRollStrategy implements IStatRollingStrategy {

    @Override
    public int[] rollStats() {
        int[] variedStandardArray = new int[6]; 

        variedStandardArray[0] = rollForIndividualScore(15); //15d2 
        variedStandardArray[1] = rollForIndividualScore(14); //14d2
        variedStandardArray[2] = rollForIndividualScore(13); //13d2
        variedStandardArray[3] = rollForIndividualScore(12); //12d2
        variedStandardArray[4] = rollForIndividualScore(10); //10d2
        variedStandardArray[5] = rollForIndividualScore(8); //8d2 
        
        return variedStandardArray;
    } 

    private int rollForIndividualScore(int numOfDice) { 
        Random rand = new Random();
        int max = 2;
        int min = 1;

        int outcome = 0; 
        for (int i = 0 ; numOfDice > i ; i++) { 
            int rollValue = rand.nextInt((max - min + 1) + min); 

            if(rollValue % 2 == 0) { 
                outcome += rollValue; 
            }   
        }

        return outcome; 
    }


    @Override
    public String getName() {
        return "Varied Standard Array Roll"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}
