package guidemon.engine.stat_roller.strategies.pointbuy;

import guidemon.engine.stat_roller.IStatRollingStrategy;

//TODO: refactor
/* 
 * Custom Point Buy is configured using Point Buy Config
 */
public class CustomPointBuyStrategy implements IStatRollingStrategy{
    // private final PointBuyConfig config;

    // public CustomPointBuyStrategy(PointBuyConfig config) {
    //     this.config = config;
    // }

    @Override
    public int[] rollStats() {
        return new int[0]; // stat values are built in UI
    }

    // public StatAssignmentMode getAssignmentMode() {
    //     return StatAssignmentMode.POINT_BUY;
    // }

    // public PointBuyConfig getPointBuyConfig() {
    //     return config;
    // }

    @Override
    public String getName() {
        return "Custom Point Buy"; 
    }

    @Override
    public String toString() {
        return getName(); 
    }
}