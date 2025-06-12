package guidemon.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import guidemon.engine.stats.IStatRollingStrategy;
import guidemon.engine.stats.PointBuyConfig;
import guidemon.engine.stats.strategies.pointbuy.CustomPointBuyStrategy;
import guidemon.engine.stats.strategies.pointbuy.DND5EStandardPointBuyStrategy;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//TODO: decouple stage, showAndWait(), and PointBuyStrategies
public class PointBuyConfigPromptController {
    // @FXML private Label pointBuyLabel; //TODO: unhook this label in fxml
    @FXML private TextField pointsField;
    @FXML private TextField baseScoresField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    // @FXML private Label totalPointsLabel;

    private IStatRollingStrategy strategy;
    private boolean confirmed = false;
    private PointBuyConfig finalConfig;
    private Stage stage; 

    public void setStage(Stage stage) { 
        this.stage = stage; 
    }

    public void setStrategy(IStatRollingStrategy strategy) {
        this.strategy = strategy;

        // Configure dynamic form
        if (strategy instanceof CustomPointBuyStrategy) {
            enableTextFields(true, true, true, true);


        } else if (strategy instanceof DND5EStandardPointBuyStrategy) {
            // Auto-fill config — no inputs needed
            finalConfig = ((DND5EStandardPointBuyStrategy) strategy).getPointBuyConfig();

            confirmed = true;
            closeWindow();

        } else {
            // Other point buy strategies: only allow maxBound entry
            enableTextFields(false, false, false, true);
        }
    }

    private void enableTextFields(boolean totalPoints, boolean baseValue, boolean minBound, boolean maxBound) {
        pointsField.setDisable(!totalPoints); 
        baseScoresField.setDisable(!baseValue);
        minField.setDisable(!minBound);
        maxField.setDisable(!maxBound); 

    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Optional<PointBuyConfig> getFinalConfig() {
        return Optional.ofNullable(finalConfig);
    }

    @FXML
    @SuppressWarnings("unused")
    private void handleConfirm() {
        if (strategy instanceof DND5EStandardPointBuyStrategy) { 
            return;
        } 

        int totalPoints = (strategy instanceof CustomPointBuyStrategy)
                ? parseOrZero(pointsField.getText())
                : sum(strategy.rollStats());

        int baseValue = (strategy instanceof CustomPointBuyStrategy)
                ? parseOrZero(baseScoresField.getText())
                : 1;

        int minBound = parseOrZero(minField.getText());

        //minimum cannot be below 1. 
        if(minBound < 1) { 
            minBound = 1; 
        }

        //TODO: Custom Bounds 
        Map<String, Integer> min = new HashMap<>(); 
        if(minBound != 0) { 
            min.put("STR", minBound); 
            min.put("DEX", minBound); 
            min.put("CON", minBound); 
            min.put("INT", minBound); 
            min.put("WIS", minBound); 
            min.put("CHA", minBound); 

        } else { 
            //if there is no minBound - set it to 1. 
            min.put("STR", 1); 
            min.put("DEX", 1); 
            min.put("CON", 1); 
            min.put("INT", 1); 
            min.put("WIS", 1); 
            min.put("CHA", 1); 
        }

        int maxBound = parseOrZero(maxField.getText());

        //TODO: Custom Bounds 
        Map<String, Integer> max = new HashMap<>(); 
        if(maxBound != 0) { 
            max.put("STR", maxBound); 
            max.put("DEX", maxBound); 
            max.put("CON", maxBound); 
            max.put("INT", maxBound); 
            max.put("WIS", maxBound); 
            max.put("CHA", maxBound); 

        }

        //there must always be a min bound to be enforced. i.e, min bound must always be populated 
        //max bound does not always have to be enforced, only enforce it if it is populated... 

        finalConfig = new PointBuyConfig(totalPoints, baseValue, true, min, max);
        // System.out.println(finalConfig.getMinValuePerStat()); //test
        confirmed = true;
        closeWindow();
    }

    private int parseOrZero(String text) {
        try {
            return Integer.parseInt(text.trim());

        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int sum(int[] array) {
        int total = 0;
        for (int i : array) total += i;
        return total;
    }

    // @FXML
    // private void onCancel() {
    //     ((Stage) totalPointsSpinner.getScene().getWindow()).close();
    // }

    private void closeWindow() {
        stage.close(); 

    }
}
