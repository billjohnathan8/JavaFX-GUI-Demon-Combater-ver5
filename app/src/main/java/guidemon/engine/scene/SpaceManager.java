package guidemon.engine.scene;

import guidemon.model.actor.Actor; 

/**
 * Converts between world coords & game logic, draws overlays.
 * 
 * TODO: GridManager
 */
public interface SpaceManager {

    //TODO: String rangeShapePath -> Shape rangeShape 
    /*
     * Show Casting-Range Overlay Shape
     */
    void showRangeOverlay(Actor caster, String rangeShapePath); 

    /**
     * Snap screen coordinates to a tile or point. 
     */
    Actor snap(double x, double y);

    /*
     * Claer all overlays 
     */
    void clearOverlay(); 
}