package guidemon.engine.combat;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.*;

import guidemon.model.combat.Combatant; 

/**
 * CombatManager drives the turn order, round count, AP pools, and basic HP adjustments
 * for a single combat encounter.
 * 
 * Responsibilities of this class: 
 * -Turn & Round Tracking
 * -Action Point (AP) Tracking
 * -Damage, Healing, (other effects, etc.)
 * -Combat (Start) / End Detection
 */
public class CombatManager {
    private final List<Combatant> combatants; //aka Initiative List
    private final CombatMetricsTracker metrics = new CombatMetricsTracker(); 
    private boolean isCombatOn = true;        //assume true unless false

    private int turnIndex = -1;               //turn 0 - surprised ; first turn is turn 1
    private int round = 1;                    //first round

    public CombatManager(List<Combatant> initialCombatants) {
        this.combatants = new ArrayList<>(initialCombatants); 
    }

    /**
     * Advance to the next turn, handling round rollover and AP reset.
     */
    public Combatant nextTurn() {
        turnIndex++; 
        metrics.recordTurn(); 

        if(turnIndex >= combatants.size()) {
            turnIndex = 0; 
            round++; 
            metrics.recordRound(); 

            resetCombatants(); 
        }

        return getCurrentCombatant(); 
    }

    //TODO: Method for Processing Actions

    //TODO: Method for Processing Tasks 

    //TODO: ALL OF THE BELOW
    /**
     * Returns true when ≤1 combatant remains alive - one of the ways to trigger isCombatOver()  
     */
    public boolean lastCombatantStanding() {
        long alive = combatants.stream()
                               .filter(Combatant::isAlive)
                               .count();

        return alive <= 1;
    }

    /**
     * Manually trigger a combatOver via GM Tick or surrender
     */
    public void endCombat() {
        isCombatOn = false; 

    }

   /* - you can also trigger this via GM Tick or Surrendering. */
    public boolean isCombatOver() {
        if(lastCombatantStanding()) {
            endCombat(); 
        }

        return isCombatOn;
    }
    
    /**
     * Reset all relevant resources back to full at the end of a round of combat. 
     */
    public void resetCombatants() {
        resetAllAP(); 
        resetMovement(); 
    }

    //TODO: 
    public void resetAllAP() {
        // combatants.forEach(c -> c.setCurrentAP(c.getMaxAP()));
    }

    //TODO: 
    public void resetMovement() {
        // combatants.forEach(c -> c.setCurrentSPD(c.getMaxSPD()));
    }

    /**
     * Deal damage and record it for metrics.
     * 
     * TODO: DamageEvent 
     */
     public void dealDamage(Combatant target, int amount) {
        // your damage logic…
    //     int hp = Math.max(0, target.getCurrentHP() - amt);
    //     target.setCurrentHP(hp);
    //     metrics.recordDamage(target, amt);
     }

    /** Record a parry event with its blocked amount. */
    public void parry(Combatant who, int amt) {
        // your parry logic…
        // metrics.recordParry(who, amt);
    }

    /** Record a dodge event (counts number of dodges).*/
    public void dodge(Combatant who) {
        // your dodge logic…
        // metrics.recordDodge(who);
    }

    /** Record a counter‐attack event (counts number of counters). */
    public void counter(Combatant who) {
         // your counter logic…
         // metrics.recordCounter(who);
    }

    /** Record a critical hit event (counts number of crits). */
    public void criticalHit(Combatant who) {
        // your critical hit logic…
        // metrics.recordCritical(who);
    }
    
    /** Heal a combatant, clamped to maxHP (does not affect metrics). */
    public void heal(Combatant target, int amt) {
        // int newHp = Math.min(target.getMaxHP(), target.getCurrentHP() + amt);
        // target.setCurrentHP(newHp);
    }

    //TODO: 
    private void sortByInitiative(List<Combatant> combatants) { 
        //sort in descending order of intiative. 
    }

    /** @return the Combatant whose turn is active; errors if called too early. */
    public Combatant getCurrentCombatant() {
        if (turnIndex < 0 || turnIndex >= combatants.size()) {
            throw new IllegalStateException("Turn has not been started or index out of range");
        }

        return combatants.get(turnIndex);
    }

    // Getters
    // Expose the live roster
    public List<Combatant> getCombatants() { 
        return Collections.unmodifiableList(combatants); 
    }

    // Expose the round number
    public int getRound() {
        return round;
    }

    // Expose metrics for end‐of‐combat summary
    public CombatMetricsTracker getMetrics() {
        return metrics;
    }
}

    //MOVE TO VIEW: 
    //initiative overlay HUD
    //overall overlay HUD <- bloodied etc
    //sense overlay HUD <- for sense
    //light overlay HUD <- for light levels 
    //obscurity overlay HUD <- for obscurity
    //current selected combatnt on the user view  
    //camera panning - smooth pan etc.

    //statsPanel
    //controlPanel <- for all abiliites during combat etc.
    //character sheet popups