package guidemon;

import java.util.List;

import guidemon.util.TokenLoader;
import guidemon.view.board.BoardPane;
import guidemon.view.token.TokenView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private Stage statRollerStage; // cached so the button always works

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        // === Board (grid) ===
        BoardPane board = new BoardPane();
        board.setGridSize(20, 30);  // rows, cols
        board.setCellSize(64);      // px per cell

        // Optional: auto-load tokens from resources/guidemon/tokens
        List<Image> imgs = TokenLoader.loadFromResources("/guidemon/tokens");
        int x = 0, y = 0;
        for (Image img : imgs) {
            TokenView tv = new TokenView(img, board, 1); // 1x1 cell token
            board.addToken(tv, x, y);
            x += 1;
            if (x >= 10) { x = 0; y += 1; } // wrap after 10 across
        }

        // === Top-right button to open Stat Roller ===
        Button statBtn = new Button("Stat Roller");
        statBtn.setOnAction(e -> openStatRoller());

        // Right-align with a spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topBar = new HBox(spacer, statBtn);
        topBar.setPadding(new Insets(8, 12, 8, 12));

        // === Root layout ===
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(board);

        Scene scene = new Scene(root, 1280, 800);
        stage.setTitle("JavaFX GUI Demon Combater ver5");
        stage.setScene(scene);
        stage.show();
    }

    /** Opens the Stat Roller view in a separate window (non-modal) and reuses it. */
    private void openStatRoller() {
        try {
            if (statRollerStage == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/guidemon/view/StatRollerView.fxml"));
                Scene statScene = new Scene(loader.load());

                statRollerStage = new Stage();
                statRollerStage.setTitle("Stat Roller");
                statRollerStage.initOwner(primaryStage);
                // Non-modal so you can keep using the grid while it’s open
                statRollerStage.initModality(Modality.NONE);
                statRollerStage.setScene(statScene);
            }

            if (!statRollerStage.isShowing()) {
                statRollerStage.show();
            }
            statRollerStage.toFront();
            statRollerStage.requestFocus();

        } catch (Exception ex) {
            ex.printStackTrace();
            
        }
    }

    public static void main(String[] args) { 
        launch(args);
     }
}