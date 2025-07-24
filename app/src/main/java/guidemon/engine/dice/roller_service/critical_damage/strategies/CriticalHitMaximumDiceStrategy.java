package guidemon.engine.dice.roller_service.critical_damage.strategies;

import guidemon.engine.dice.roller_service.RollerService;
import guidemon.engine.dice.roller_service.critical_damage.ICriticalHitRollingStrategy; 
import guidemon.model.dice.Dice; 

public class CriticalHitMaximumDiceStrategy implements ICriticalHitRollingStrategy {
    @Override
    /**
     * Critical Damage is not actually rolled - it just takes the sum of the number of faces of the dice, i.e, taking the maximum possible rolled damage for each damage dice. 
     * 
     * Does not apply critical multiplier - consumes it instead. 
     */
    public int calculateCriticalHitDamage(Dice damageDice, int damageModifier, double criticalMultiplier, int criticalDamageBonus, RollerService rollerService) {
        int sides = damageDice.getDiceFace();
        int numberOfDice = damageDice.getNumberOfDice(); 

        //swallow critical multiplier without doing anything 
        //swallow roller service - no rolling is actually done.

        return (sides * numberOfDice) + damageModifier + criticalDamageBonus;  
    }

    @Override
    public String getName() {
        return "Maximum Damage Dice on Critical Hit"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}
