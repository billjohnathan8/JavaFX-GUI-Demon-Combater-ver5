package guidemon.model.item.interfaces;

import java.util.List; 

import guidemon.model.ability.Ability; 
import guidemon.model.stats.immutable.instances.AbilityScoreEntry;

public interface Weaponable {
    int getBaseDamageDice();

    List<AbilityScoreEntry> getDamageModifier(); 

    int getStandardAttackRange(); 

    String getCombatItemArchetype();

    List<String> getCombatExItemTags(); 

    List<Ability> getCombatMoveset();
}