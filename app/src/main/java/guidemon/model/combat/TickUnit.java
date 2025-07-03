package guidemon.model.combat;

public enum TickUnit {
    TICK,             //Smallest Unit
    ACTION,           //Actions Made via CastTime not actual AP Cost
    TURN,             //At Every Turn End
    ROUND,            //At Every Round End
    CUSTOM;           //Based on every epoch that a Condition is met
}