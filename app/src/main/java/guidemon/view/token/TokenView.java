package guidemon.view.token;

import guidemon.view.board.BoardPane;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class TokenView extends ImageView {
    private double pressOffsetX;
    private double pressOffsetY;

    private final BoardPane board;
    private final Group content;  // the transform root for tokens/grid
    private final int sizeInCells; // support large tokens later (2x2, etc.)

    public TokenView(Image image, BoardPane board, int sizeInCells) {
        super(image);
        this.board = board;
        this.content = board.getContentGroup();
        this.sizeInCells = sizeInCells;

        // Make token square-ish by cell size
        setPreserveRatio(true);
        setSmooth(true);
        resizeToCell();

        enableDrag();
    }

    private void resizeToCell() {
        double cs = board.getCellSize();
        setFitWidth(cs * sizeInCells);
        setFitHeight(cs * sizeInCells); // With preserveRatio, width is primary; height is a cap.
    }

    private void enableDrag() {
        // If cell size changes, keep token size consistent
        board.cellSizeProperty().addListener((obs, o, n) -> resizeToCell());

        setOnMousePressed(this::onPress);
        setOnMouseDragged(this::onDrag);
        setOnMouseReleased(this::onRelease);
    }

    private void onPress(MouseEvent e) {
        // Mouse coords are in TokenView's local space
        pressOffsetX = e.getX();
        pressOffsetY = e.getY();
        toFront();
        e.consume();
    }

    private void onDrag(MouseEvent e) {
        // Convert scene coords to content local so transforms don't break dragging
        Point2D p = content.sceneToLocal(e.getSceneX(), e.getSceneY());
        relocate(p.getX() - pressOffsetX, p.getY() - pressOffsetY);
        e.consume();
    }

    private void onRelease(MouseEvent e) {
        // Snap to grid
        double snappedX = board.snap(getLayoutX());
        double snappedY = board.snap(getLayoutY());
        relocate(snappedX, snappedY);
        e.consume();
    }
}