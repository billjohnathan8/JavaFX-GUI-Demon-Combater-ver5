package guidemon.model.stats;

public enum ResourceOrbState {
    FREE,                  //Default Value - contains no effects and is free for use.
    OCCUPIED,              //Stores an effect
    USED,                  //The orb has been 'used' and will be freed up again at some point in time based on a condition e.g. every round end for Action Points
    BLOCKED;               //The orb is 'blocked' and unavailable for use unless a certain condition is met e.g. during TEMPO BREAK, the user must remove a status effect before the orb is unblocked. Orbs that are 'blocked' may still contain their effects - this simply depends on what effect caused the block. By default, the BLOKCED state does not remove effects, so the status effects that do remove the status effects do need to remove all effects within the orb separately. 
}
