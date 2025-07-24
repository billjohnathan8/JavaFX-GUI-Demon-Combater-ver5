package guidemon.engine.dice.roller_service.critical_damage;

import guidemon.engine.dice.roller_service.RollerService; 
import guidemon.model.dice.Dice; 

public interface ICriticalHitRollingStrategy {
        int calculateCriticalHitDamage(Dice damageDice, int damageModifier, double criticalMultiplier, int criticalDamageBonus, RollerService rollerService); // returns a single final value for critical damage. 

        String getName();                                                                                                                 // For display of the strategy's name
}

//override toString() as well