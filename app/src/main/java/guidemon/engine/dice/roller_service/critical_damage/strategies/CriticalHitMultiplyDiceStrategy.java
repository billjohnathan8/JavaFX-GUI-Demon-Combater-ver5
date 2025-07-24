package guidemon.engine.dice.roller_service.critical_damage.strategies;

import guidemon.engine.dice.roller_service.RollerService;
import guidemon.engine.dice.roller_service.critical_damage.ICriticalHitRollingStrategy; 
import guidemon.model.dice.Dice; 

public class CriticalHitMultiplyDiceStrategy implements ICriticalHitRollingStrategy {
     @Override
    /**
     * Critical Damage rolls the number of damage dice x the critical multiplier. 
     * 
     * Standard = double the number of damage dice. 
     * Non-Standard = x3, x5, x10, ... the number of damage dice rolled. 
     */
    public int calculateCriticalHitDamage(Dice damageDice, int damageModifier, double criticalMultiplier, int criticalDamageBonus, RollerService rollerService) {        
        int result = 0; 
        for (int i = 0 ; i < criticalMultiplier ; i++) {
            result += rollerService.rollDice(damageDice); 
            result += damageModifier; 
        }

        result += criticalDamageBonus; 

        return result; 
    }

    @Override
    public String getName() {
        return "Multiplier x Damage Dice on Critical Hit"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }      
}