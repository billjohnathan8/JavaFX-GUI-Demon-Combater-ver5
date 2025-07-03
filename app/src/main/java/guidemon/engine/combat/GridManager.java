// package guidemon.engine.combat;

// import guidemon.engine.scene.SpaceManager; 

//TODO: 
//encapsulates all battlemap and overlay concerns.
// public class GridSpaceManager implements SpaceManager {
//     private final TileGrid grid; 
    //grid contains all the tiles 
    // you can hold references to each overlay layer here:
    // private final VisionOverlay vision;
    // private final LightOverlay light;
    // ...

//     private final OverlayCanvas canvas; 

//     public GridSpaceManager(TileGrid grid, OverlayCanvas canvas) {
//         this.grid = grid;
//         this.canvas = canvas; 
//     }

//     //TODO: String shapePath -> Shape shape
//     @Override
//     public void showRangeOverlay(Combatant c, String shapePath) {
//         canvas.draw(shape); //shapes already aligned to grid. 
//     }

//     @Override
//     public Combatant snap(double x, double y) {
//         return grid.tileAt(x, y); 
//     }

//     @Override
//     public void clearOverlays() {
//         canvas.clear(); 
//     }

    //TODO: Tiles, Terrain and TileGrid
    /** Snap a world‐coordinate to the nearest tile. */
    // public Tile snapToTile(double x, double y) {
    //   return grid.tileAt(screenToWorld(x,y));
    // }

    //TODO: Shapes for AOE, Casting, etc.
    /** Show casting range for this caster + ability. */
    // public void showCastingRange(Combatant caster, Ability ability) {
    //   Shape shape = CastingOverlay.rangeShape(caster, ability);
    //   // push to your JavaFX canvas…
    // }

    // …more methods for panning, token highlights, etc.
// }