package guidemon.engine.stat_roller.strategies;

import guidemon.engine.stat_roller.IStatRollingStrategy;

/*
 * Stats Spread consists of: 
 * -2 Ability Boosts (score: 20)
 * -2 Ability Flaws (score: 6)
 * -2 Neutral Ability Scores (score: 10)
 * 
 * Ability Array: [20, 20, 10, 10, 6, 6]
 */
public class PF2EAbilityArrayStrategy implements IStatRollingStrategy {
    private final int[] abilityArray = {20, 20, 10, 10, 6, 6}; 

    @Override
    public int[] rollStats() {
        return this.abilityArray; 
    }

    @Override
    public String getName() {
        return "PF2e Ability Boosts & Ability Flaws - Ability Array (20, 20, 10, 10, 8, 8)"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}
