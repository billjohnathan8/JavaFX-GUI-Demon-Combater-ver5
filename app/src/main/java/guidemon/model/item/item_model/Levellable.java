package guidemon.model.item.item_model;

import java.util.Map; 

import guidemon.engine.condition.Condition;
import guidemon.model.ability.Ability; 

//Items that can be Levelled via Proficiency-Levelling
public interface Levellable {
    int getLevel();

    Map<Condition, Ability> getPrerequisites();
}
