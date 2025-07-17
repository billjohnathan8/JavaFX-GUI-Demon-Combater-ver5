package guidemon.model.stats.immutable.definitions;

import java.util.List; 
import java.util.ArrayList;

import guidemon.model.effect.Effect;
import guidemon.model.immutability.BaseDefinition; 
import guidemon.model.stats.ResourceOrbState; 

/**
 * Some Resources occur in discrete amounts and hence are not loosely stackable - hence they cannot be encapsulated by numbers such as current amount or maximum amount in a conventional sense. 
 * 
 * These current/maximum value displays can only occur in the view and calculate dynamically at run-time. Because these resources contain references to other game or data objects at run-time (e.g. list of effects from a spell that the user is concentrating on or summon slots filled from Epic Summons currently out on the battlefield, summoned and controlled by the user), hence why it is called a Resource 'Orb' - because the orb stores other data.
 * 
 * It is usually used in a List. 
 * 
 * Some notable examples of ResourceOrbs include but are not limited to: Summoning Slots, Action Points, etc.
 * 
 * This class is the Definition for an Entry of Resource.
 * There is nothing to inherit from, so this class is final. 
 * 
 * //TODO: Actions vs SUM - separation of mechanics 
 */
public final class ResourceOrbDefinition extends BaseDefinition {
    private final ResourceOrbState defaultState; 
    //list of effects are not included in the definition. 
    private final String defaultType; 
    
    public ResourceOrbDefinition(String id, String displayName, String defaultType, ResourceOrbState defaultState) {
        super(id, displayName);
        this.defaultType = defaultType; 
        this.defaultState = defaultState; 
    }

    //only getters: 
    public String getDefaultType() {
        return this.defaultType; 
    }

    public ResourceOrbState getDefaultState() {
        return this.defaultState; 
    }
}