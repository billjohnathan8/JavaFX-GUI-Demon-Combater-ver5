package guidemon.controller;

import java.io.IOException;
import java.util.Optional; 

import guidemon.engine.stats.IStatRollingStrategy;
import guidemon.engine.stats.PointBuyConfig;
import guidemon.engine.stats.StatAssignmentMode;
import guidemon.engine.stats.strategies.BalanceCardDeckStrategy;
import guidemon.engine.stats.strategies.Coin20RollStrategy; 
import guidemon.engine.stats.strategies.DND5EStandardArrayStrategy;
import guidemon.engine.stats.strategies.DND5EStandardRollStrategy;
import guidemon.engine.stats.strategies.DropOneGachaRollStrategy;
import guidemon.engine.stats.strategies.DropOneRollStrategy;
import guidemon.engine.stats.strategies.DropTwoRollStrategy;
import guidemon.engine.stats.strategies.FullSwingCardDeckStrategy;
import guidemon.engine.stats.strategies.NHEDropOneRollStrategy;
import guidemon.engine.stats.strategies.NHEInflatedRollStrategy;
import guidemon.engine.stats.strategies.NumberCruncherRollStrategy;
import guidemon.engine.stats.strategies.PF2EAbilityArrayStrategy;
import guidemon.engine.stats.strategies.StraightGachaRollStrategy;
import guidemon.engine.stats.strategies.StraightRollStrategy;
import guidemon.engine.stats.strategies.SwingCardDeckStrategy;
import guidemon.engine.stats.strategies.TenRollStrategy;
import guidemon.engine.stats.strategies.ThreeUpThreeDownRollStrategy;
import guidemon.engine.stats.strategies.VariedStandardArrayRollStrategy;
import guidemon.engine.stats.strategies.pointbuy.CustomPointBuyStrategy;
import guidemon.engine.stats.strategies.pointbuy.DND5EStandardPointBuyStrategy;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader; 
import javafx.scene.Parent;
import javafx.scene.Scene; 
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage; 

//TODO: navigation using purely keyboard (i.e, power-user speed creation)

/* 
Lets user select a stat rolling method

Prompts for assignment mode

Prompts for point-buy config (if needed)

Proceeds to roll + flow into assignment UI
 */
public class MainViewController {
    @FXML private ListView<IStatRollingStrategy> strategyChoiceBox;

    @FXML
    public void initialize() {
        strategyChoiceBox.setItems(FXCollections.observableArrayList(
            new NHEInflatedRollStrategy(), //default

            new BalanceCardDeckStrategy(),
            new Coin20RollStrategy(),
            new DND5EStandardArrayStrategy(),
            new DND5EStandardRollStrategy(),
            new DropOneGachaRollStrategy(), 
            new DropOneRollStrategy(), 
            new DropTwoRollStrategy(), 
            new FullSwingCardDeckStrategy(),
            new NHEDropOneRollStrategy(), 
            new NumberCruncherRollStrategy(),
            new PF2EAbilityArrayStrategy(),
            new StraightGachaRollStrategy(),
            new StraightRollStrategy(),
            new SwingCardDeckStrategy(),
            new TenRollStrategy(),
            new ThreeUpThreeDownRollStrategy(),
            new VariedStandardArrayRollStrategy(),

            //Add others here

            new CustomPointBuyStrategy(), 
            new DND5EStandardPointBuyStrategy()

            //add more point buy here
        ));
    }

    @FXML
    @SuppressWarnings("unused")
    private void onRollButtonPressed() {
        IStatRollingStrategy strategy = strategyChoiceBox.getSelectionModel().getSelectedItem();

        if (strategy == null) { 
            return;
        } 

        boolean isStrategyPointBuy = (strategy instanceof CustomPointBuyStrategy || strategy instanceof DND5EStandardPointBuyStrategy); 

        StatAssignmentMode mode; 
        if(isStrategyPointBuy) { 
            mode = StatAssignmentMode.POINT_BUY;

        } else {
            mode = promptAssignmentMode(); 

        }

        Optional<PointBuyConfig> pointBuyConfig = Optional.empty();

        if (mode == StatAssignmentMode.POINT_BUY) { 
            pointBuyConfig = promptPointBuyConfig(strategy);

        }
            
        int[] stats = strategy.rollStats();
        validateStats(stats);

        switch (mode) {
                case FIXED -> applyStats(stats);
                case CHOOSE -> openStatAssignment(stats);
                case POINT_BUY -> openPointBuy(pointBuyConfig.get());
        }
    }

    private StatAssignmentMode promptAssignmentMode() {
        ChoiceDialog<StatAssignmentMode> dialog = new ChoiceDialog<>(StatAssignmentMode.CHOOSE, StatAssignmentMode.values());
        dialog.setTitle("Choose Assignment Mode");
        dialog.setHeaderText("How should ability scores be assigned?");
        return dialog.showAndWait().orElse(StatAssignmentMode.FIXED);
    }

    //* opens PointBuyConfigPrompt.fxml */
    private Optional<PointBuyConfig> promptPointBuyConfig(IStatRollingStrategy strategy) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PointBuyConfigPrompt.fxml"));
            Parent root = loader.load();
            PointBuyConfigPromptController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Point Buy Configuration");

            controller.setStage(stage); 
            controller.setStrategy(strategy); 

            //TODO: decouple stage, showAndWait(), and PointBuyStrategies
            if(!(strategy instanceof DND5EStandardPointBuyStrategy)) { 
                stage.showAndWait(); 

            } 

            return controller.getFinalConfig();

        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
            
        }
    }

    //* opens StatAssignmentView.fxml */ 
    private void openStatAssignment(int[] stats) { 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StatAssignmentView.fxml"));
            Parent root = loader.load();

            StatAssignmentController controller = loader.getController();
            controller.setRolledStats(stats);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Assign Stats");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    //* opens PointBuyView.fxml */
    private void openPointBuy(PointBuyConfig config) { 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PointBuyView.fxml"));
            Parent root = loader.load();

            PointBuyController controller = loader.getController();
            controller.setConfig(config);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Point Buy Assignment");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    //TODO: 
    /* sets stats directly to character */ 
    private void applyStats(int[] stats) { 
        String formattedString = String.format("Stats: [STR: %d, DEX: %d, CON: %d, INT: %d, WIS: %d, CHA: %d]", stats[0], stats[1], stats[2], stats[3], stats[4], stats[5]);

        System.out.println(formattedString);
    }

    //ensure that if a 0 value is detected - replace it with 1 instead, since the minimum value for any given ability score is 1. 
    private void validateStats(int[] stats) { 
        for (int i = 0 ; stats.length > i ; i++) { 
            if(stats[i] < 1) { 
                stats[i] = 1; 
            }
        }
    }
}