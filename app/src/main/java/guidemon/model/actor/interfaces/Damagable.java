package guidemon.model.actor.interfaces;

import java.util.Map;

import guidemon.model.combat.DamageType;

public interface Damagable {
    /**
     * A method used to allow an actor or entry to take damage. 
     */
    void takeDamage(int initialDamage, Map<DamageType, Integer> initialDamageTypes, double initialArmourPenetration); 
}