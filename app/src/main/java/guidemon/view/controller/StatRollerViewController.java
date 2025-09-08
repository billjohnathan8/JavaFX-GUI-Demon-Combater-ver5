package guidemon.view.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import guidemon.engine.stat_roller.IStatRollingStrategy;
import guidemon.engine.stat_roller.PointBuyConfig;
import guidemon.engine.stat_roller.StatAssignmentMode;
import guidemon.engine.stat_roller.strategies.BalanceCardDeckStrategy;
import guidemon.engine.stat_roller.strategies.Coin20RollStrategy;
import guidemon.engine.stat_roller.strategies.DND5EStandardArrayStrategy;
import guidemon.engine.stat_roller.strategies.DND5EStandardRollStrategy;
import guidemon.engine.stat_roller.strategies.DropOneGachaRollStrategy;
import guidemon.engine.stat_roller.strategies.DropOneRollStrategy;
import guidemon.engine.stat_roller.strategies.DropTwoRollStrategy;
import guidemon.engine.stat_roller.strategies.FullSwingCardDeckStrategy;
import guidemon.engine.stat_roller.strategies.NHEDropOneRollStrategy;
import guidemon.engine.stat_roller.strategies.NHEInflatedRollStrategy;
import guidemon.engine.stat_roller.strategies.NumberCruncherRollStrategy;
import guidemon.engine.stat_roller.strategies.PF2EAbilityArrayStrategy;
import guidemon.engine.stat_roller.strategies.StraightGachaRollStrategy;
import guidemon.engine.stat_roller.strategies.StraightRollStrategy;
import guidemon.engine.stat_roller.strategies.SwingCardDeckStrategy;
import guidemon.engine.stat_roller.strategies.TenRollStrategy;
import guidemon.engine.stat_roller.strategies.ThreeUpThreeDownRollStrategy;
import guidemon.engine.stat_roller.strategies.VariedStandardArrayRollStrategy;
import guidemon.engine.stat_roller.strategies.pointbuy.CustomPointBuyStrategy;
import guidemon.engine.stat_roller.strategies.pointbuy.DND5EStandardPointBuyStrategy;
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
public class StatRollerViewController {
    public static final String RESOURCES_VIEW_PATH = "/guidemon/view/"; 

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
            case FIXED -> {
                try {
                    goToAssignment(mode, stats);

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

            case CHOOSE -> {
                try {
                    goToAssignment(mode, stats);

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

            case POINT_BUY -> {
                if (pointBuyConfig.isPresent()) { 
                    openPointBuy(pointBuyConfig.get()); 

                } else { 
                    System.out.println("Point Buy assignment selected, but no config provided. Aborting.");

                }
            }
        }
    }

    private StatAssignmentMode promptAssignmentMode() {
        ChoiceDialog<StatAssignmentMode> dialog = new ChoiceDialog<>(StatAssignmentMode.CHOOSE, StatAssignmentMode.values());
        dialog.setTitle("Choose Assignment Mode");
        dialog.setHeaderText("How should ability scores be assigned?");
        return dialog.showAndWait().orElse(StatAssignmentMode.FIXED);
    }

    //* opens PointBuyConfigPrompt.fxml */
    //* opens PointBuyView.fxml */
    private Optional<PointBuyConfig> promptPointBuyConfig(IStatRollingStrategy strategy) {
        try {
            String pointBuyConfigPromptPath = "" + RESOURCES_VIEW_PATH + "PointBuyConfigPrompt.fxml"; 
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pointBuyConfigPromptPath));
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

    //* opens PointBuyView.fxml */
    private void openPointBuy(PointBuyConfig config) { 
        try {
            String PointBuyViewPath = "" + RESOURCES_VIEW_PATH + "PointBuyView.fxml"; 
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PointBuyViewPath));
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

    //* opens StatAssignmentView.fxml */ 
    private void goToAssignment(StatAssignmentMode mode, int[] inOrder) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/guidemon/view/StatAssignmentView.fxml"));
        Parent root = loader.load();
        StatAssignmentController ctrl = loader.getController();

        // Convert to List<Integer> in order
        List<Integer> scores = Arrays.stream(inOrder).boxed().toList();

        // Tell the controller which mode & which values to show
        ctrl.configure(mode, scores);

        Stage stage = new Stage(); // or reuse your primary stage / scene swapper
        stage.setTitle("Assign Ability Scores");
        stage.setScene(new Scene(root));
        stage.show();
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