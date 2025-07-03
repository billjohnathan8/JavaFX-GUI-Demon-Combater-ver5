package guidemon.model.stats.immutable.instances;

import java.util.List;

import guidemon.engine.effect.Effect;
import guidemon.model.entry.Entry;
import guidemon.model.stats.ResourceOrbState; 
import guidemon.model.stats.immutable.definitions.ResourceOrbDefinition; 

/**
 * Some Resources occur in discrete amounts and hence are not loosely stackable - hence they cannot be encapsulated by numbers such as current amount or maximum amount in a conventional sense. 
 * 
 * These current/maximum value displays can only occur in the view and calculate dynamically at run-time. Because these resources contain references to other game or data objects at run-time (e.g. list of effects from a spell that the user is concentrating on or summon slots filled from Epic Summons currently out on the battlefield, summoned and controlled by the user), hence why it is called a Resource 'Orb' - because the orb stores other data.
 * 
 * It is usually used in a List. 
 * 
 * Some notable examples of ResourceOrbs include but are not limited to: Summoning Slots, Action Points, etc.
 * 
 * Also known as: ResourceOrbInstance
 * 
 * This class is the entry for ResourceOrbs 
 * 
 * //TODO: Actions vs SUM - separation of mechanics 
 */
public class ResourceOrbEntry extends Entry {
    private final ResourceOrbDefinition definition; 
    private ResourceOrbState state; 
    private List<Effect> effects;  //effects currently stored by this ResourceOrb - for Actions it does not store any effects unless concentration is used. 

    private String type;           //used for ResourceOrb Subtypes, e.g. Standard Action Orb, Concentration Slot, etc. or Standard Summon Slot, Epic Summon Orb or Concentration Orb

    public ResourceOrbEntry(ResourceOrbDefinition definition, String name, String type, List<Effect> effects) {
        super(name); 
        this.definition = definition;
        this.type = definition.getDefaultType(); 
        this.state = definition.getDefaultState(); //no need for type cast 
        this.effects = effects; 
    }

    //overload this constructor if you want to load from JSON:
    public ResourceOrbEntry(ResourceOrbDefinition definition, String name, String type, ResourceOrbState state, List<Effect> effects) {
        super(name); 
        this.definition = definition;
        this.type = type; 
        this.state = state; 
        this.effects = effects; 
    }    

    //getters and setters: 
    public String getType() {
        return this.type; 
    }

    /**
     * For use in conversions between different types. 
     * E.g. Standard Action Orb -> Concentration Orb
     */
    public void setType(String type) {
        this.type = type; 
    }

    public ResourceOrbState getState() {
        return this.state; 
    }

    /**
     * For change of state e.g. when an action is used - set from FREE to USED
     */
    public void setState(ResourceOrbState state) {
        this.state = state;
    }

    /**
     * Use this getter to add and remove effects individually.
     */
    public List<Effect> getEffects() {
        return this.effects;
    }

    /**
     * Use this setter to change out an entire group of effects in one instantaneous transaction. 
     */
    public void setEffects(List<Effect> effects) {
        this.effects = effects; 
    }
}

/*
 * Different Types: 
 * Action Types: 
 * -Versatile <- any of the above
 * -Standard Action
 * -Re-Action
 * -Concentration
 * -Bonus Action <- does not count as an action, but ticks via Bonus Action Ticks
 * -Spell Action
 * -Attack Action
 * -Dash Action
 * -Interruption
 * -Teleportation 
 * 
 * 
 * -Item Action <- involves items, inventory, consumables, etc. does not count as an action, but does tick
 * -Inter-Action <- does not count as an action, but does tick
 * -Partial Action <- does not count as an action, but does tick
 * 
 * Other Action Types: 
 * 
 */