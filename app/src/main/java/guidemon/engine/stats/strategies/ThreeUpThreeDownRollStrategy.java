package guidemon.engine.stats.strategies;

import java.util.Random;

import guidemon.engine.stats.IStatRollingStrategy;

/* 1. Roll 1d6
 * 2. Roll 1d8
 * 3. Roll 1d10
 * 4. First Stat: 10 + rolled value on 1d6
 * 5. Second Stat: 15 - rolled value on 1d6
 * 6. Third Stat: 10 + rolled value on 1d8
 * 7. Fourth Stat: 15 - rolled value on 1d8
 * 8. Fifth Stat: 8 + rolled value on 1d10
 * 9. Sixth Stat: 17 - rolled value on 1d10
 * 
 * Three stats are added up (1d6, 1d8, 1d10), Three Stats are lowered down (1d6, 1d8, 1d10)
 */
public class ThreeUpThreeDownRollStrategy implements IStatRollingStrategy {
    private int[] scoreArray = {10, 15, 10, 15, 8, 17}; 

    @Override
    public int[] rollStats() {
        Random rand = new Random(); 
        int min = 1; 
        int d6Max = 6; 
        int d8Max = 8;
        int d10Max = 10; 

        int d6Result = rand.nextInt((d6Max - min + 1) + min); 
        int d8Result = rand.nextInt((d8Max - min + 1) + min); 
        int d10Result = rand.nextInt((d10Max - min + 1) + min); 

        this.scoreArray[0] += d6Result; 
        this.scoreArray[1] -= d6Result; 

        this.scoreArray[2] += d8Result; 
        this.scoreArray[3] -= d8Result; 

        this.scoreArray[4] += d10Result; 
        this.scoreArray[5] -= d10Result; 

        return this.scoreArray; 
    } 

    @Override
    public String getName() {
        return "Three-Up Three-Down Roll"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}
