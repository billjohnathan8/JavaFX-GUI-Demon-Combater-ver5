package guidemon.view.board;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class BoardPane extends Pane {

    private final DoubleProperty cellSize = new SimpleDoubleProperty(64); // px
    private int rows = 20;
    private int cols = 30;

    // Layers
    private final Canvas gridCanvas = new Canvas();
    private final Group tokenLayer = new Group();
    private final Group content = new Group(); // gridCanvas (back) + tokenLayer (front)

    public BoardPane() {
        // order matters: grid behind tokens
        content.getChildren().addAll(gridCanvas, tokenLayer);
        getChildren().add(content);

        // Keep gridCanvas sized to BoardPane
        gridCanvas.widthProperty().bind(widthProperty());
        gridCanvas.heightProperty().bind(heightProperty());

        // Redraw grid when size or cell size changes
        widthProperty().addListener((obs, o, n) -> drawGrid());
        heightProperty().addListener((obs, o, n) -> drawGrid());
        cellSize.addListener((obs, o, n) -> drawGrid());

        // Initial draw
        drawGrid();
    }

    public Group getContentGroup() {
        return content;
    }

    public Group getTokenLayer() {
        return tokenLayer;
    }

    public double getCellSize() {
        return cellSize.get();
    }

    public void setCellSize(double size) {
        this.cellSize.set(size);
    }

    public DoubleProperty cellSizeProperty() {
        return cellSize;
    }

    public void setGridSize(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        drawGrid();
    }

    private void drawGrid() {
        double w = getWidth();
        double h = getHeight();
        double cs = getCellSize();

        if (w <= 0 || h <= 0 || cs <= 0) return;

        GraphicsContext g = gridCanvas.getGraphicsContext2D();
        g.clearRect(0, 0, w, h);

        // Background (optional): subtle dark
        g.setFill(Color.web("#1e1e1e"));
        g.fillRect(0, 0, w, h);

        // Grid lines
        g.setStroke(Color.web("#444444"));
        g.setLineWidth(1);

        // Crisp lines at .5 offsets
        for (double x = 0.5; x <= cols * cs + 0.5; x += cs) {
            g.strokeLine(x, 0.5, x, rows * cs + 0.5);
        }
        for (double y = 0.5; y <= rows * cs + 0.5; y += cs) {
            g.strokeLine(0.5, y, cols * cs + 0.5, y);
        }

        // Bold outer border (optional)
        g.setStroke(Color.web("#666666"));
        g.setLineWidth(2);
        g.strokeRect(0.5, 0.5, cols * cs, rows * cs);
    }

    /** Add a token node onto the token layer at a given cell position. */
    public void addToken(javafx.scene.Node token, int cellX, int cellY) {
        double cs = getCellSize();
        token.relocate(cellX * cs, cellY * cs);
        tokenLayer.getChildren().add(token);
    }

    /** Snap any pixel (x,y) to nearest cell top-left in board-local coords. */
    public double snap(double pixel) {
        double cs = getCellSize();
        return Math.round(pixel / cs) * cs;
    }
}

