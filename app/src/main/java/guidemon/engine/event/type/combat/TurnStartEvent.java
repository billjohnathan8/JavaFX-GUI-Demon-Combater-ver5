package guidemon.engine.event.type.combat;

import guidemon.engine.event.Event; 
import guidemon.model.combat.Combatant; 

/**
 * Fired at the beginning of each combatant’s turn.
 *   - Regen triggers can listen to restore HP or AP.
 *   - UI can move the camera to the active token.
 * 
 */
public class TurnStartEvent implements Event {
    private final Combatant combatant; 

    public TurnStartEvent(Combatant combatant) {
        this.combatant = combatant;
    }

    //only getters (because of publisher model - give only the necessary information for the subscribers): 
    public Combatant getCombatant() {
        return this.combatant;
    }   
}