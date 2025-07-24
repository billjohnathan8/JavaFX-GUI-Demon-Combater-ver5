package guidemon.engine.dice.roller_service.critical_damage.strategies;

import guidemon.engine.dice.roller_service.RollerService;
import guidemon.engine.dice.roller_service.critical_damage.ICriticalHitRollingStrategy; 
import guidemon.model.dice.Dice; 

public class CriticalHitDoubleDiceStrategy implements ICriticalHitRollingStrategy {
    @Override 
    /**
     * Critical Damage rolls the number of damage dice x the critical multiplier. 
     * 
     * Standard = double the number of damage dice. 
     * Non-Standard = x3, x5, x10, ... the number of damage dice rolled. 
     */
    public int calculateCriticalHitDamage(Dice damageDice, int damageModifier, double criticalMultiplier, int criticalDamageBonus, RollerService rollerService) {
        //swallow critical multiplier without doing anything 

        Dice doubledDamageDice = new Dice(damageDice.getNumberOfDice() * 2, damageDice.getDiceFace());  //double the number of damage dice

        int result = rollerService.rollDice(doubledDamageDice);

        result += damageModifier * 2; 

        result += criticalDamageBonus;

        return result;
    }

    @Override
    public String getName() {
        return "Double Damage Dice on Critical Hit"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }    
}