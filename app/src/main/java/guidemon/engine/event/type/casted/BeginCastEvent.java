package guidemon.engine.event.type.casted;

import java.util.Collections;
import java.util.List; 

import guidemon.engine.event.Event; 
import guidemon.model.ability.Ability;
import guidemon.model.entry.Entry; 

/**
 * Fired when the caster or AI starts casting an ability: 
 *    - UI draws targeting overlays
 *    - Triggers can validate range or highlight tokens
 * 
 * What it does: carries exactly the information subscribers need to show the range overlay, validate which tokens can be clicked, etc.
 */
public class BeginCastEvent implements Event {
    private final Ability ability; 
    private final Entry caster; 
    private final List<Entry> targets;
    
    public BeginCastEvent(Ability ability, Entry caster, List<Entry> targets) {
        this.ability = ability; 
        this.caster =  caster;
        this.targets = targets; 
    }

    //only getters (because of publisher model - give only the necessary information for the subscribers): 
    public Ability getAbility() {
        return this.ability;
    }

    public Entry getCaster() {
        return this.caster;
    }

    public List<Entry> getTargets() {
        return this.targets;
    }
}