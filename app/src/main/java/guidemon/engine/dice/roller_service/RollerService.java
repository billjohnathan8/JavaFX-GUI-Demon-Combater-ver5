package guidemon.engine.dice.roller_service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import guidemon.engine.dice.roller_service.critical_damage.ICriticalHitRollingStrategy;
import guidemon.model.dice.Dice;

/**
 * Used for internal dice rolling by the SceneContext itself. 
 * 
 */
public class RollerService {
    private static final Random RNG = new Random(); 

    // your uniform 1…sides roll - for any basic roll
    public int roll(int sides) {
        return RNG.nextInt(sides) + 1;     

    }

    //for dice input 
    public int rollDice(Dice dice) {
        return rollDice(dice, new HashMap<>()); //empty hashmap = 0 bonuses to anything. 
    
    }

    //overloaded method to take in rolls with just one bonus at the end of the roll
    public int rollDice(Dice dice, int singleBonus) {
        Map<String, Integer> bonuses = new HashMap<>();
        
        bonuses.put("Roll End Bonus", singleBonus); 

        return rollDice(dice, bonuses);
    }

    //overloaded method to take in rolls with specified bonuses. 
    public int rollDice(Dice dice, Map<String, Integer> bonuses) {
        int sides = dice.getDiceFace();
        int numberOfDice = dice.getNumberOfDice();

        int inRollBonus = 0; 
        int endRollBonus = 0; 

        //skips if the map is empty. 
        for (Map.Entry<String, Integer> entry : bonuses.entrySet()) {
            String bonusType = entry.getKey(); 
            Integer bonusValue = entry.getValue(); 

            if(bonusType.equals("In Roll Bonus")) {
                inRollBonus += bonusValue; 

            } else if (bonusType.equals("Roll End Bonus")) {
                endRollBonus += bonusValue; 

            }
        }
        
        int result = 0; 

        for (int i = 0 ; i < numberOfDice ; i++) {
            result += roll(sides); 
            result += inRollBonus; 
        }

        result += endRollBonus; 

        return result;
    }    

    //Handle Critical Hit
    public int rollCriticalHitDamage(ICriticalHitRollingStrategy criticalHitStrategy, Dice damageDice, int damageModifier, double criticalMultiplier, int criticalDamageBonus) {
        return criticalHitStrategy.calculateCriticalHitDamage(damageDice, damageModifier, criticalMultiplier, criticalDamageBonus, this);
    }
    
    //TODO: based on roll parser (?)
    //TODO: roll types: exploding dice, etc.
}