package guidemon.engine.stats;

public interface IStatRollingStrategy {
    int[] rollStats();  //returns an array of 6 stats if they are to be assigned

    String getName();  // For display of the strategy's name 
}

//Override toString() as well