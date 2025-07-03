// package guidemon.engine.scene;

// import guidemon.model.actor.Actor; 

//TODO: 
// public class FreeSpaceManager implements SpaceManager {
//     private final OverlayCanvas canvas;

//     public FreeSpaceManager(OverlayCanvas canvas) {
//         this.canvas = canvas;
//     }

//     @Override 
//     public void showRangeOverlay(Actor a, Shape) {
//         canvas.draw(shape) //no grid snapping 
//     }

//     @Override
//     public Actor snap(double x, double y) {
//         return new double[](x, y); //free coordinates
//     }

//     @Override
//     public void clearOverlays() {
//         canvas.clear(); 
//     }
// }