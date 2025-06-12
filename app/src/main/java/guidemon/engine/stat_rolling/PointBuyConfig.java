package guidemon.engine.stat_rolling;

import java.util.Map; 

//TOOD: 
public class PointBuyConfig {
    private int points; 
    private int baseValue;
    private boolean bounded; 
    private Map<String, Integer> minValuePerStat;
    private Map<String, Integer> maxValuePerStat; 

    public PointBuyConfig(int points, int baseValue, boolean bounded, Map<String, Integer> minValuePerStat, Map<String, Integer> maxValuePerStat) { 
        this.points = points; 
        this.baseValue = baseValue; 
        this.bounded = bounded; 
        this.minValuePerStat = minValuePerStat; 
        this.maxValuePerStat = maxValuePerStat;
    
    }

    public int getPoints() { 
        return this.points;
    }

    public int getBaseValue() { 
        return this.baseValue; 
    }

    public boolean getBounded() { 
        return this.bounded; 
    }

    public Map<String, Integer> getMinValuePerStat() {
        return this.minValuePerStat; 
    }

    public Map<String, Integer> getMaxValuePerStat() {
        return this.maxValuePerStat; 
    }
}