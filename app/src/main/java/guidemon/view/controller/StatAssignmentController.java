package guidemon.view.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class StatAssignmentController {
    @FXML private ComboBox<Integer> strBox;
    @FXML private ComboBox<Integer> dexBox;
    @FXML private ComboBox<Integer> conBox;
    @FXML private ComboBox<Integer> intBox;
    @FXML private ComboBox<Integer> wisBox;
    @FXML private ComboBox<Integer> chaBox;

    private final List<ComboBox<Integer>> boxes = new ArrayList<>();
    private final Map<ComboBox<Integer>, Integer> selections = new HashMap<>();
    private List<Integer> originalStats;
    // private List<Integer> originalStats and currentstats 

    //TODO: buggy behaviour - some values still noclip.
    //TODO: use for reset button that allows you to reallocate your stats if you are not happy.

    @FXML
    public void initialize() {
        boxes.addAll(List.of(strBox, dexBox, conBox, intBox, wisBox, chaBox));
        for (ComboBox<Integer> box : boxes) {
            box.setItems(FXCollections.observableArrayList());
            box.valueProperty().addListener((obs, oldVal, newVal) -> {
                selections.put(box, newVal);
                updateAvailableOptions();
            });
        }
    }

    public void setRolledStats(int[] stats) {
        originalStats = Arrays.stream(stats).boxed().collect(Collectors.toList());
        selections.clear();

        for (ComboBox<Integer> box : boxes) {
            box.setValue(null);
        }

        updateAvailableOptions();
    }

    private void updateAvailableOptions() {
        // Count how many times each value appears in the original rolled stats
        Map<Integer, Long> originalCounts = originalStats.stream()
                                                         .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        // Count how many times each value is already selected
        Map<Integer, Long> selectedCounts = selections.values().stream()
                                                               .filter(Objects::nonNull)
                                                               .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        for (ComboBox<Integer> box : boxes) {
            Integer current = box.getValue();

            // Temporarily exclude the current box’s value from selection count
            Map<Integer, Long> tempSelected = new HashMap<>(selectedCounts);
            if (current != null) {
                tempSelected.put(current, tempSelected.getOrDefault(current, 0L) - 1);
                if (tempSelected.get(current) <= 0) tempSelected.remove(current);
            }

            // Compute available options: original counts - selected counts
            List<Integer> newOptions = new ArrayList<>();
            for (Map.Entry<Integer, Long> entry : originalCounts.entrySet()) {
                int statValue = entry.getKey();
                long availableCount = entry.getValue() - tempSelected.getOrDefault(statValue, 0L);
                for (int i = 0; i < availableCount; i++) {
                    newOptions.add(statValue);
                }
            }

            Collections.sort(newOptions); // Optional: sort values
            ObservableList<Integer> items = box.getItems();
            items.setAll(newOptions);

            // Restore value if still valid
            if (current != null && Collections.frequency(newOptions, current) > 0) {
                box.setValue(current);

            } /*else {*/
            //     box.setValue(null);
            // }
        }
    }

    @FXML
    public void handleConfirm() {
        System.out.println("Final Stats Assigned:");
        for (ComboBox<Integer> box : boxes) {
            System.out.println(box.getId() + " → " + box.getValue());
        }
    }
}
