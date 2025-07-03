package guidemon.view.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import guidemon.engine.stat_roller.PointBuyConfig;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory; 

//TODO: patch point buy

/* 
 * Accepts a PointBuyConfig
 * Enforces rules as user assigns points
 */
public class PointBuyController {
    @FXML private Spinner<Integer> strSpinner;
    @FXML private Spinner<Integer> dexSpinner;
    @FXML private Spinner<Integer> conSpinner;
    @FXML private Spinner<Integer> intSpinner;
    @FXML private Spinner<Integer> wisSpinner;
    @FXML private Spinner<Integer> chaSpinner;
    @FXML private Label pointsAvailableLabel;
    @FXML private Label pointsUsedLabel;

    private int totalPoints;
    private int baseValue;

    private Map<String, Integer> minBounds;
    private Map<String, Integer> maxBounds; 
    private List<Spinner<Integer>> spinners;
    private PointBuyConfig config; 
    private int[] stats; 

    public void setConfig(PointBuyConfig config) {
        this.config = config;
        this.totalPoints = config.getPoints();
        this.baseValue = config.getBaseValue();
        this.minBounds = config.getMinValuePerStat();
        this.maxBounds = config.getMaxValuePerStat();

        // System.out.println(this.totalPoints); //test 
        // System.out.println(minBounds); //test 

        spinners = List.of(strSpinner, dexSpinner, conSpinner, intSpinner, wisSpinner, chaSpinner);

        setSpinners(); 
    }

    public void setSpinners() { 
                 // Collect all spinners in order:
        spinners = List.of(strSpinner, dexSpinner, conSpinner, intSpinner, wisSpinner, chaSpinner);

        for (Spinner<Integer> spinner : spinners) {
            // Derive stat name from fx:id, e.g. "strSpinner" → "STR"
            String stat = spinner.getId().replace("Spinner","").toUpperCase();
            
            int min = minBounds.get(stat); 
 
            int max; 
            if(!maxBounds.isEmpty()) { 
                max = maxBounds.get(stat); 

            } else { 
                max = Integer.MAX_VALUE; 
            }

            // 1) Give it a working factory
            SpinnerValueFactory<Integer> factory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, baseValue);
            spinner.setValueFactory(factory);

            // 2) Listen for changes
            spinner.valueProperty().addListener((obs, oldVal, newVal) -> updateRemainingPoints());
        }

        // 3) Initialize the remaining‐points display
        updateRemainingPoints();
    }; 

    /** Recomputes how many points have been spent & updates spinners + label. */
    private void updateRemainingPoints() {
        // Sum (currentValue – baseValue) across all spinners
        int used = spinners.stream()
                           .mapToInt(sp -> sp.getValue() - baseValue)
                           .sum();
        int availablePoints = totalPoints - used;

        // Update the labels
        pointsAvailableLabel.setText("Points Available: " + availablePoints);
        pointsUsedLabel.setText("Points Used: " + used);

        // Adjust each spinner's max so you can't overspend:
        for (Spinner<Integer> spinner : spinners) {
            SpinnerValueFactory.IntegerSpinnerValueFactory factory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) spinner.getValueFactory();
            int current = spinner.getValue();
            factory.setMax(current + availablePoints);
        }

    }

    //TODO: testing 
    public void getFinalStats() { 
        // Map<String, Integer> finalStats = new HashMap<>(); 
        // finalStats.put("str", ); 
        // finalStats.put("dex", spinners.get("dex").getValue()); 
        // finalStats.put("con", spinners.get("con").getValue()); 
        // finalStats.put("int", spinners.get("int").getValue());
        // finalStats.put("wis", spinners.get("wis").getValue());
        // finalStats.put("cha", spinners.get("cha").getValue()); 

        // String formattedString = String.format("[STR: %d, DEX: %d, CON: %d, INT: %d, WIS: %d, CHA: %d]", finalStats.get("str"), finalStats.get("dex"), finalStats.get("con"), finalStats.get("int"), finalStats.get("wis"), finalStats.get("cha"));
        // System.out.println(formattedString);

        Map<String,Integer> finalStats = spinners.stream().collect(Collectors.toMap(
            sp -> sp.getId().replace("Spinner","").toUpperCase(),
            Spinner::getValue
        ));

        System.out.println(finalStats);
    }

    @FXML
    @SuppressWarnings("unused")
    private void handleConfirm() {
        int totalUsed = Arrays.asList(strSpinner, dexSpinner, conSpinner, intSpinner, wisSpinner, chaSpinner)
            .stream().mapToInt(Spinner::getValue).sum() - (6 * config.getBaseValue());

        if (totalUsed > config.getPoints()) {
            // Show alert
            System.out.println("POINTS EXCEEDED. STOP.");

        } else {
            // Save values
            getFinalStats(); 

        }
    }

    //TODO: dynamically update the points available
}