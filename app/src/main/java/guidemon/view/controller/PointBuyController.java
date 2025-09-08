package guidemon.view.controller;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import guidemon.engine.stat_roller.PointBuyConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.HBox; 

//TODO: patch point buy

/* 
 * Accepts a PointBuyConfig
 * Enforces rules as user assigns points
 */
public class PointBuyController {
    // Value labels (the big numbers)
    @FXML private Label strValueLabel, dexValueLabel, conValueLabel, intValueLabel, wisValueLabel, chaValueLabel;

    // Modifier labels (e.g., +2, -1)
    @FXML private Label strModLabel, dexModLabel, conModLabel, intModLabel, wisModLabel, chaModLabel;

    // +/- buttons for each row (if your FXML has them)
    @FXML private Button strIncBtn, strDecBtn,
                        dexIncBtn, dexDecBtn,
                        conIncBtn, conDecBtn,
                        intIncBtn, intDecBtn,
                        wisIncBtn, wisDecBtn,
                        chaIncBtn, chaDecBtn;

    // Remaining points label (if present)
    @FXML private Label remainingPointsLabel;

    @FXML private Spinner<Integer> strSpinner;
    @FXML private Spinner<Integer> dexSpinner;
    @FXML private Spinner<Integer> conSpinner;
    @FXML private Spinner<Integer> intSpinner;
    @FXML private Spinner<Integer> wisSpinner;
    @FXML private Spinner<Integer> chaSpinner;
    @FXML private Label pointsAvailableLabel;
    @FXML private Label pointsUsedLabel;

    // Defaults (used only when config omits a value)
    private int baseScore  = 8;
    private int minScore   = 8;
    private int maxScore   = 15;
    private int totalPoints = 27; // Budget - injected from PointBuyConfigPromptController

    private Map<String, Integer> minBounds;
    private Map<String, Integer> maxBounds; 
    private List<Spinner<Integer>> spinners;
    private PointBuyConfig config; 
    // private int[] stats; 

    @FXML private HBox bulkBox;               // optional, for show/hide
    @FXML private Button bulkApplyButton;
    @FXML private Spinner<Integer> bulkSpinner;
    private IntegerSpinnerValueFactory bulkFactory;

    // Current ability scores (pre-racial). Initialize with your current state.
    private final Map<Ability, Integer> scores = new EnumMap<>(Ability.class);

    public void setConfig(PointBuyConfig cfg) {
        // 1) Merge config → controller fields (only override when provided)
        if (cfg != null) {
            this.totalPoints = cfg.getPoints();
            //TODO: actually enforce and load from configs 
            // if (cfg.getBaseValue() != null) 
            //     baseScore   = cfg.getBaseValue();
            // if (cfg.getPoints() != null) 
            //     totalPoints = cfg.getPoints();

            // 2) Seed initial scores if provided (may be partial)
            scores.clear();
            // if (initialScores != null) {
            //     scores.putAll(initialScores);
            // }

            // scores.putAll(cfg.getPoints()); //TODO: ability scores original from config 
        }

        // 3) Ensure all 6 abilities exist (fill missing with baseScore)
        ensureScoresInitialized();

        // 4) Now it’s safe to compute/update any UI
        updateBulkControls();          // spinner bounds/btn state
        refreshAllAbilityRows();       // <- your existing method to update the per-ability UI (mods/labels)
    }

    @FXML
    private void initialize() {
        // wire up the spinner/button only
        bulkFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0);
        bulkSpinner.setValueFactory(bulkFactory);
        bulkSpinner.setEditable(false);
        bulkApplyButton.setDisable(true);
        // DO NOT call updateBulkControls() here — scores might not be ready yet
    }

    private void ensureScoresInitialized() {
        for (Ability a : Ability.values()) {
            scores.putIfAbsent(a, baseScore);
        }
    }

    public void refreshAllAbilityRows() {
        // STR
        refreshRow(Ability.STR,
            strValueLabel, strModLabel,
            strIncBtn, strDecBtn);

        // DEX
        refreshRow(Ability.DEX,
            dexValueLabel, dexModLabel,
            dexIncBtn, dexDecBtn);

        // CON
        refreshRow(Ability.CON,
            conValueLabel, conModLabel,
            conIncBtn, conDecBtn);

        // INT
        refreshRow(Ability.INT,
            intValueLabel, intModLabel,
            intIncBtn, intDecBtn);

        // WIS
        refreshRow(Ability.WIS,
            wisValueLabel, wisModLabel,
            wisIncBtn, wisDecBtn);

        // CHA
        refreshRow(Ability.CHA,
            chaValueLabel, chaModLabel,
            chaIncBtn, chaDecBtn);

        // Remaining points + bulk controls
        refreshRemainingLabel();
        updateBulkControls();   // <- your existing spinner/button bounds logic
    }

    /** Total points spent for all six scores (5e standard). */
    private int computeTotalSpentPoints(Map<Ability, Integer> s) {
        int sum = 0;
        for (Ability a : Ability.values()) {
            int v = s.getOrDefault(a, baseScore);
            sum += costOfScore(clamp(v));
        }
        return sum;
    }

    private int clamp(int v) {
        if (v < minScore) return minScore;
        if (v > maxScore) return maxScore;
        return v;
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
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, baseScore);
            spinner.setValueFactory(factory);

            // 2) Listen for changes
            spinner.valueProperty().addListener((obs, oldVal, newVal) -> updateRemainingPoints());
        }

        // 3) Initialize the remaining‐points display
        updateRemainingPoints();
    }; 

    /** Recomputes how many points have been spent & updates spinners + label. */
    private void updateRemainingPoints() {
        // Sum (currentValue – baseScore) across all spinners
        int used = spinners.stream()
                           .mapToInt(sp -> sp.getValue() - baseScore)
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

    private void setupBulkControls() {
        bulkFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0);
        bulkSpinner.setValueFactory(bulkFactory);
        bulkSpinner.setEditable(false);
        bulkApplyButton.setDisable(true); // disabled until bounds say otherwise
    }

    /** Recompute the spinner bounds whenever points/spend change. */
    private void updateBulkControls() {
        int spent = computeTotalSpentPoints(scores);
        int remaining = totalPoints - spent;

        // Per your spec (not the real cost curve): bounds are budget-based, not score-based.
        // Max positive: floor(remaining / 6)
        // Max negative: - floor(spent / 6)
        int max = remaining / 6;
        int min = - (spent / 6);

        // Keep a valid current value in range
        int cur = bulkFactory.getValue();
        if (cur < min) cur = min;
        if (cur > max) cur = max;

        bulkFactory.setMin(min);
        bulkFactory.setMax(max);
        bulkFactory.setValue(cur);

        // Disable if no effect possible
        bulkApplyButton.setDisable(min == 0 && max == 0);
    }

    @FXML
    @SuppressWarnings("unused")
    private void handleBulkApply() {
        int deltaPerAbility = bulkSpinner.getValue(); // can be positive or negative (down to min)

        if (deltaPerAbility == 0) return;

        if (deltaPerAbility > 0) {
            applyBulkIncrease(deltaPerAbility);
        } else {
            applyBulkDecrease(-deltaPerAbility); // pass magnitude
        }

        // After changing scores, refresh any UI you have for each ability/mod/points
        // e.g., refreshAbilityRows(); refreshPointsLabels();
        updateBulkControls();
    }

    /** Try to increase every ability by `steps` total, applied as +1 rounds. */
    private void applyBulkIncrease(int steps) {
        for (int step = 0; step < steps; step++) {
            boolean appliedAtLeastOne = false;

            for (Ability a : Ability.values()) {
                int val = scores.get(a);
                if (val >= maxScore) continue; // 5e pre-racial cap; adapt if yours differs

                int cost = costToRaiseByOne(val);
                int remaining = totalPoints - computeTotalSpentPoints(scores);
                if (cost <= remaining) {
                    scores.put(a, val + 1);
                    appliedAtLeastOne = true;
                }
            }
            // If in one full pass nothing applied, we’re out of budget or at caps everywhere
            if (!appliedAtLeastOne) break;
        }
    }

    /** Try to decrease every ability by `steps` total, applied as -1 rounds. */
    private void applyBulkDecrease(int steps) {
        for (int step = 0; step < steps; step++) {
            boolean appliedAtLeastOne = false;

            for (Ability a : Ability.values()) {
                int val = scores.get(a);
                if (val <= minScore) continue; // 5e floor; adapt if yours differs

                // We can always refund; no need to check budget here, but keep floor constraint
                scores.put(a, val - 1);
                appliedAtLeastOne = true;
            }
            if (!appliedAtLeastOne) break;
        }
    }

    /** Cost table per 5e PHB (pre-racial), relative to base 8. */
    private int costOfScore(int score) {
        // cost at value: 8->0, 9->1, 10->2, 11->3, 12->4, 13->5, 14->7, 15->9
        // Disallow <8 or >15 by clamping or throw if your UI shouldn’t permit it
        score = clamp(score);
        return switch (score) {
            case 8  -> 0;
            case 9  -> 1;
            case 10 -> 2;
            case 11 -> 3;
            case 12 -> 4;
            case 13 -> 5;
            case 14 -> 7;
            case 15 -> 9;
            default -> 0; // clamped above; “base 8” table assumed; unreachable
        };
    }

    /** Marginal cost to raise from `cur` to `cur+1`. */
    private int costToRaiseByOne(int cur) {
        // Using the table difference:
        return costOfScore(clamp(cur + 1)) - costOfScore(clamp(cur));
    }

    private int refundForLowerByOne(int cur) {
        return costOfScore(clamp(cur)) - costOfScore(clamp(cur - 1));
    }

    // Compute 5e-style modifier
    private String modText(int score) {
        int mod = Math.floorDiv(score - 10, 2);
        return (mod >= 0 ? "+" : "") + mod;
    }

    private boolean canIncrease(Ability a) {
        int v = scores.getOrDefault(a, baseScore);
        if (v >= maxScore) return false;
        int remaining = totalPoints - computeTotalSpentPoints(scores);
        int cost = costToRaiseByOne(v);
        return cost <= remaining;
    }

    private boolean canDecrease(Ability a) {
        int v = scores.getOrDefault(a, baseScore);
        return v > minScore;
    }

    // Null-safe control updater for one row
    private void refreshRow(Ability a,
                            Label valueLbl, Label modLbl,
                            Button incBtn, Button decBtn) {
        int v = scores.getOrDefault(a, baseScore);

        if (valueLbl != null) valueLbl.setText(Integer.toString(v));
        if (modLbl != null)   modLbl.setText(modText(v));

        if (incBtn != null) incBtn.setDisable(!canIncrease(a));
        if (decBtn != null) decBtn.setDisable(!canDecrease(a));
    }

    // Optional: remaining points label
    private void refreshRemainingLabel() {
        if (remainingPointsLabel != null) {
            int spent = computeTotalSpentPoints(scores);
            int remaining = totalPoints - spent;
            remainingPointsLabel.setText(Integer.toString(remaining));
        }
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