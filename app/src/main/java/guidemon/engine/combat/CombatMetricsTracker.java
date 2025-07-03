package guidemon.engine.combat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import guidemon.model.combat.Combatant; 

/**
 * Collects and allows you to query end-of-combat statistics.
 */
public class CombatMetricsTracker {
    //Time Passage Counters - total time, recorded and saved at different points in combat
    private int totalRounds;
    private int totalTurns; 
    private int totalActions;  //aka TotalAP 
    private int totalTicks;

    //Records: 
    private final Map<Combatant, String> actionRecord = new HashMap<>(); 

    private final Map<Combatant, Integer> damageMap = new HashMap<>();               //highest damage
    private final Map<Combatant, Integer> parryMap = new HashMap<>();               //highest parry damage

    private final Map<Combatant, Integer> dodgeMap = new HashMap<>();               //highest number of dodges
    private final Map<Combatant, Integer> counterMap = new HashMap<>();               //highest number of counter attacks
    private final Map<Combatant, Integer> criticalMap = new HashMap<>();               //highest number of critical hits 

    //Recording Methods: 
    public void recordDamage(Combatant who, int amount) {
        update(who, amount, damageMap); 
    }

    public void recordParry(Combatant who, int amount) {
        update(who, amount, parryMap);
    }

    public void recordDodge(Combatant who) {
        update(who, 1, dodgeMap);
    }

    public void recordCounter(Combatant who) {
        update(who, 1, counterMap);
    }

    public void recordCritical(Combatant who) {
        update(who, 1, criticalMap);
    }

    /**
     * Record the passage of Actions, AP
     */
    public void recordAction(Combatant who, String description) {
        actionRecord.put(who, description);

        //What did they do? - AP Cost, Cast Time Duration, etc.
        //How much does that tick forward in time? 

        //totalActions++; 
    }

    public void recordRound() {
        totalRounds++;
    }

    public void recordTurn() {
        totalTurns++; 
    }

    /**
     * Called on every tick
     */
    public void recordTick() {
        totalTicks++;
    }

    //Core Method:
    public void update(Combatant who, int delta, Map<Combatant, Integer> map) {
        map.put(who, map.getOrDefault(who, 0) + delta);
    }

    //Return Top Results: 
    public Optional<Map.Entry<Combatant, Integer>> topDamage() {
        return mapMaxEntry(damageMap);
    }

    public Optional<Map.Entry<Combatant, Integer>> topParry() { 
        return mapMaxEntry(parryMap); 
    }

    public Optional<Map.Entry<Combatant, Integer>> topDodge() {
        return mapMaxEntry(dodgeMap); 
    }

    public Optional<Map.Entry<Combatant, Integer>> topCounter() { 
        return mapMaxEntry(counterMap); 
    }

    public Optional<Map.Entry<Combatant, Integer>> topCritical() { 
        return mapMaxEntry(criticalMap); 
    }

    //TODO: WIP
    private Optional<Map.Entry<Combatant, Integer>> mapMaxEntry(Map<Combatant, Integer> map) { 
        return map.entrySet().stream()
                             .max(Map.Entry.comparingByValue()); 
    }
}