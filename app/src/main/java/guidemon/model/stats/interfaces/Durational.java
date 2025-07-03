package guidemon.model.stats.interfaces;

import guidemon.model.combat.TickUnit; 

/**
 * Also known as: Temporary 
 * Used for Temporary Effects, Temporary HP etc.
 */
public interface Durational {
    int getDurationInCombat(); 
    int getDurationOutOfCombat(); 
    TickUnit getDurationTickUnit(); 

    void setDurationInCombat(); 
    void setDurationOutOfCombat();     
}