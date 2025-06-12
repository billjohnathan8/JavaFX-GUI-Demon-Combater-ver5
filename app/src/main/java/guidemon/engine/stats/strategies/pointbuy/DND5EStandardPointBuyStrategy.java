package guidemon.engine.stats.strategies.pointbuy;

import java.util.Map;
// import java.util.Optional; 

import guidemon.engine.stats.IStatRollingStrategy;
import guidemon.engine.stats.PointBuyConfig;
import guidemon.engine.stats.StatAssignmentMode; 


//TODO: refactor 

/* 
 * D&D 5e Standard Point Buy has 27 Available Points. 
 * Tailored for NHe, the Starting Ability Score Value is 6. 
 */
public class DND5EStandardPointBuyStrategy implements IStatRollingStrategy{
    private static final int POINTS = 27; 
    private static final int BASE_SCORE_VALUES = 6; 

    @Override
    public int[] rollStats() {
        return new int[0]; // handled by PointBuy UI
    }

    public StatAssignmentMode getAssignmentMode() {
        return StatAssignmentMode.POINT_BUY;
    }

    public PointBuyConfig getPointBuyConfig() {
        return new PointBuyConfig(
                POINTS, BASE_SCORE_VALUES, true,
                Map.of("STR", 1, "DEX", 1, "CON", 1, "INT", 1, "WIS", 1, "CHA", 1),
                Map.of() // No upper limit
        );
    }

    @Override
    public String getName() {
        return "D&D 5e Standard Point Buy (" + POINTS + " pts)"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}