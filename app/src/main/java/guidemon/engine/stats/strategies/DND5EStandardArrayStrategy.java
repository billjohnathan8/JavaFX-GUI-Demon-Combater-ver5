package guidemon.engine.stats.strategies;

import guidemon.engine.stats.IStatRollingStrategy;

/*
 * Use Standard Array for each of the stats [15, 14, 13, 12, 10, 8]
 */
public class DND5EStandardArrayStrategy implements IStatRollingStrategy {
    private final int[] standardArray = {15, 14, 13, 12, 10, 8}; 

    @Override
    public int[] rollStats() {
        return this.standardArray; 
    }

    @Override
    public String getName() {
        return "D&D 5e Standard Array (15, 14, 13, 12, 10, 8)"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}
