// package guidemon.engine.combat;

// import guidemon.engine.scene.SpaceManager; 

//TODO: 
// public class GridSpaceManager implements SpaceManager {
//     private final TileGrid grid; 
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
// }