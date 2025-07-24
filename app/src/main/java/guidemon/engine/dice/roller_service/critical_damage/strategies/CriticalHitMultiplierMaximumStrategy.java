package guidemon.engine.dice.roller_service.critical_damage.strategies;

import guidemon.engine.dice.roller_service.RollerService;
import guidemon.engine.dice.roller_service.critical_damage.ICriticalHitRollingStrategy; 
import guidemon.model.dice.Dice; 

public class CriticalHitMultiplierMaximumStrategy implements ICriticalHitRollingStrategy {
     @Override
    /**
     * Critical Damage is not actually rolled - it just takes the sum of the (number of faces of the dice xCRIT), i.e, taking xCRIT times the maximum possible rolled damage for each damage dice. 
     * 
     * NHe Standard. 
     */
    public int calculateCriticalHitDamage(Dice damageDice, int damageModifier, double criticalMultiplier, int criticalDamageBonus, RollerService rollerService) {
        int sides = damageDice.getDiceFace();
        int numberOfDice = damageDice.getNumberOfDice(); 

        //swallow roller service - no rolling is actually done.

        return ((int) Math.round((sides * numberOfDice + damageModifier) * criticalMultiplier)) + criticalDamageBonus; 
    }

    @Override
    public String getName() {
        return "Multiplier x Maximum Damage Dice on Critical Hit"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }   
}