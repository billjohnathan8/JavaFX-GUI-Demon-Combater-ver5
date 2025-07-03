package guidemon.view.token.space;

import guidemon.view.token.type.ActorToken;

/*
 * Individual Tiles on a Grid
 */
public class Tile {
    private ActorToken currentOccupyingToken;  //must be an actor
    //other tokens such as structures are overlayed in logic, despite appaering like they are occupying the tile //TODO:

    private int groundLevelHeight;    //used in elevation calculation
    //hardness of the ground for bounciness, friction, etc.

    //list of terrains overlaying on top of the current tile.

    //vision - obscurity
    //vision - light level 

}