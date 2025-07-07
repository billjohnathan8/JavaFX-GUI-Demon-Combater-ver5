package guidemon.model.actor.interfaces;
 
//can travel vertically 
public interface VerticalMovable {
    /**
     * A method used to travel vertically. 
     * 
     * Involves token.
     * 
     * @param amountOfMovement
     * @param movementCost
     */
    void verticallyMove(int amountOfMovement, int movementCost);
    
}