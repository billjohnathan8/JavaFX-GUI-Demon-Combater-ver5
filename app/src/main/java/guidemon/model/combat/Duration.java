package guidemon.model.combat;

/**
 * Class that bundles together duration in combat, out of combat, and the relevant amounts
 */
public class Duration {
    private double outOfCombatDuration; 
    private TickUnit combatTickUnit; 
    private int inCombatDurationAmount; 

    public Duration(double outOfCombatDuration, TickUnit combatTickUnit, int inCombatDurationAmount) {
        this.outOfCombatDuration = outOfCombatDuration; 
        this.combatTickUnit = combatTickUnit; 
        this.inCombatDurationAmount = inCombatDurationAmount; 
    }

    //getters and setters: 
    public double getOutOfCombatDuration() {
        return this.outOfCombatDuration; 
    }

    public void setOutOfCombatDuration(double outOfCombatDuration) {
        this.outOfCombatDuration = outOfCombatDuration;
    }

    public TickUnit getCombatTickUnit() {
        return this.combatTickUnit; 
    }

    public void setCombatTickUnit(TickUnit combatTickUnit) {
        this.combatTickUnit = combatTickUnit; 
    }

    public int getInCombatDurationAmount() {
        return this.inCombatDurationAmount; 
    }

    public void setInCombatDurationAmount(int inCombatDurationAmount) {
        this.inCombatDurationAmount = inCombatDurationAmount; 
    }
}