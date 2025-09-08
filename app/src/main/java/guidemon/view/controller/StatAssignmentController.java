package guidemon.view.controller;

import java.net.URL; // FIXED, CHOOSE
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import guidemon.engine.stat_roller.StatAssignmentMode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Drag-and-drop stat assignment controller.
 * - FIXED mode: auto-fills STR→DEX→CON→INT→WIS→CHA.
 * - CHOOSE mode: tiles start in the pool (tray).
 * - Drop outside any slot returns to pool.
 * - Dropping onto an occupied slot swaps tiles (slot↔slot). From pool → occupant goes back to pool.
 * - Modifiers update live using floor((score-10)/2).
 */
public class StatAssignmentController implements Initializable {

    // Keep a registry of tiles by id
    private static final DataFormat TILE_ID_FMT = new DataFormat("application/x-tile-id");
    private final Map<String, StackPane> tileById = new HashMap<>();

    // ====== FXML ======
    @FXML private HBox poolBox;
    @FXML private Label poolTitle;

    @FXML private StackPane strSlot, dexSlot, conSlot, intSlot, wisSlot, chaSlot;
    @FXML private Label strModLabel, dexModLabel, conModLabel, intModLabel, wisModLabel, chaModLabel;
    @FXML private Button confirmButton;

    // ====== Model ======
    public enum Ability { STR, DEX, CON, INT, WIS, CHA }
    private static final List<Ability> ABILITY_ORDER =
            Arrays.asList(Ability.STR, Ability.DEX, Ability.CON, Ability.INT, Ability.WIS, Ability.CHA);

    private StatAssignmentMode mode = StatAssignmentMode.FIXED;
    private List<Integer> availableScores = new ArrayList<>();

    private final Map<Ability, StackPane> abilitySlots = new EnumMap<>(Ability.class);
    private final Map<Ability, Label> abilityModLabels = new EnumMap<>(Ability.class);
    private final List<StackPane> tiles = new ArrayList<>(6);

    private boolean configuredOnce = false;
    
    // True only for the *current* drag if a drop target actually handled it.
    private boolean dragDropHandled = false;

    // ====== Styles ======
    private final DropShadow hoverGlow = new DropShadow(12, Color.web("#66ccff"));

    private static final String SLOT_EMPTY_STYLE =
            "-fx-background-color: #3a3b3f; -fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: black; -fx-border-width: 1;";
    private static final String SLOT_FILLED_STYLE =
            "-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: black; -fx-border-width: 1;";
    private static final String TILE_STYLE =
            "-fx-background-color: #fdb86d; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: black; -fx-border-width: 1; -fx-padding: 6 12;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // System.out.println("[DBG] initialize()"); //DEBUG

        // Map slots & labels
        abilitySlots.put(Ability.STR, strSlot);
        abilitySlots.put(Ability.DEX, dexSlot);
        abilitySlots.put(Ability.CON, conSlot);
        abilitySlots.put(Ability.INT, intSlot);
        abilitySlots.put(Ability.WIS, wisSlot);
        abilitySlots.put(Ability.CHA, chaSlot);

        abilityModLabels.put(Ability.STR, strModLabel);
        abilityModLabels.put(Ability.DEX, dexModLabel);
        abilityModLabels.put(Ability.CON, conModLabel);
        abilityModLabels.put(Ability.INT, intModLabel);
        abilityModLabels.put(Ability.WIS, wisModLabel);
        abilityModLabels.put(Ability.CHA, chaModLabel);

        // Start empty/grey + DnD hook - Prepare Slots
        abilitySlots.values().forEach(slot -> {
            slot.setPickOnBounds(true); //ensures empty area is hittable -  allow dropping on empty area
            slot.setStyle(SLOT_EMPTY_STYLE);

            hookSlotDnD(slot);
        });

        hookPoolDnD(); // allow intentional drop-to-pool

        // Safe default if not configured yet //TODO: default if not configured yet?
        if (availableScores.isEmpty()) {
            availableScores = Arrays.asList(15, 14, 13, 12, 10, 8);
        }
        rebuildTilesFromScores();
        updateConfirmState();
    }

    /**
     * External configuration; call right after load() from previous view.
     */
    public void configure(StatAssignmentMode mode, List<Integer> scoresInOrder) {
        // System.out.println("[DBG] configure() called with " + mode + " -> " + scoresInOrder); //DEBUG

        if (configuredOnce) return;
        
        configuredOnce = true;
        
        // populate & (if FIXED) auto-place

        this.mode = (mode != null) ? mode : StatAssignmentMode.FIXED;
        this.availableScores = new ArrayList<>(scoresInOrder);

        poolTitle.setText(this.mode == StatAssignmentMode.FIXED
                ? "Available Scores (FIXED)"
                : "Available Scores (CHOOSE)");

        // Rebuild pool tiles
        rebuildTilesFromScores();

        if (this.mode == StatAssignmentMode.FIXED) {
            // Auto-place tiles to slots in order STR→DEX→CON→INT→WIS→CHA
            for (int i = 0; i < ABILITY_ORDER.size() && i < tiles.size(); i++) {
                StackPane tile = tiles.get(i);
                moveTileToSlotNoSwap(tile, abilitySlots.get(ABILITY_ORDER.get(i)));
            }
        }

        updateConfirmState();
    }

    // ================== UI Build ==================

    private void rebuildTilesFromScores() {
        // System.out.println("[DBG] rebuildTilesFromScores()"); //DEBUG

        tiles.clear();
        poolBox.getChildren().clear();

        for (Integer v : availableScores) {
            StackPane tile = makeTile(v);
            tiles.add(tile);
            poolBox.getChildren().add(tile);
        }
    }

    private StackPane makeTile(int value) {
        Label label = new Label(String.valueOf(value));
        label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        StackPane tile = new StackPane(label);
        tile.setMinSize(72,72); tile.setPrefSize(72,72); tile.setMaxSize(72,72);
        tile.setAlignment(Pos.CENTER);
        tile.setStyle(TILE_STYLE);

        // Store score and unique id on the node
        tile.getProperties().put("score", value);
        String tileId = java.util.UUID.randomUUID().toString();
        tile.getProperties().put("tileId", tileId);
        tileById.put(tileId, tile);

        // --- Drag start ---
        tile.setOnDragDetected(e -> {
            // identify current source slot (for swap)
            StackPane sourceSlot = (tile.getParent() instanceof StackPane sp && tile.getParent() != poolBox) ? sp : null;
            tile.getProperties().put("dragSourceSlot", sourceSlot);

            var db = tile.startDragAndDrop(TransferMode.MOVE);
            var cc = new ClipboardContent();
            cc.put(TILE_ID_FMT, tileId);        // <— carry the unique tile id
            cc.putString(Integer.toString(value)); // optional
            db.setContent(cc);

            // Optional drag view
            var snapshot = tile.snapshot(new javafx.scene.SnapshotParameters(), null);
            db.setDragView(snapshot, snapshot.getWidth()/2, snapshot.getHeight()/2);

            e.consume();
        });

        // --- Drag done fallback: if nobody completed the drop, return to pool ---
        tile.setOnDragDone(e -> {
            try {
                if (!dragDropHandled) {
                    // Nobody *reliably* handled the drop -> return to pool
                    moveTileToPool(tile);
                    updateConfirmState();
                }
            } finally {
                // reset for next gesture
                dragDropHandled = false;
                tile.getProperties().remove("dragSourceSlot");

                // System.out.println("onDragDone completed=" + e.isDropCompleted()); //DEBUG
                e.consume();
            }
        });

        return tile;
    }

    // ================== DnD Targets ==================

    private void hookSlotDnD(StackPane slot) {
        slot.setPickOnBounds(true);

        slot.setOnDragEntered(e -> {
            slot.setEffect(hoverGlow);                     // <<— effect only
            e.consume();
        });

        slot.setOnDragExited(e -> {
            slot.setEffect(null);                    // <<— remove effect only
            // DO NOT change base style here
            e.consume();
        });

        slot.setOnDragOver(e -> {
            var db = e.getDragboard();
            if (db.hasContent(TILE_ID_FMT)) {
                e.acceptTransferModes(TransferMode.MOVE); // always accept if it’s a tile
            }
            e.consume();
        });

        slot.setOnDragDropped(e -> {
            boolean success = false;
            try {
                if (e.isConsumed()) return; // respect targets

                var db = e.getDragboard();
                if (db.hasContent(TILE_ID_FMT)) {
                    // Resolve the exact tile being dragged
                    StackPane tile = resolveTileById(db.getContent(TILE_ID_FMT));
                    if (tile != null) {
                        StackPane occupant = getTileInSlot(slot);
                        StackPane sourceSlot = (StackPane) tile.getProperties().get("dragSourceSlot");

                        if (occupant != null && occupant != tile) {
                            if (sourceSlot != null) {
                                moveTileToSlotNoSwap(occupant, sourceSlot); // swap
                                refreshSlotVisuals(sourceSlot);             // <<— ensure filled

                            } else {
                                moveTileToPool(occupant);                   // came from pool
                            }
                        }

                        moveTileToSlotNoSwap(tile, slot);
                        refreshSlotVisuals(slot);

                        success = true;
                        dragDropHandled = true;                             // your flag

                        //  System.out.printf("DROP %s: tile=%s srcSlot=%s occupant=%s success=%s%n", abilityOf(slot), tile != null ? tile.getProperties().get("score") : null, sourceSlot != null ? abilityOf(sourceSlot) : null, occupant != null ? occupant.getProperties().get("score") : null, success); //DEBUG 
                        
                    }
                }
            } finally {
                e.setDropCompleted(success);      // critical - still do the right thing for JavaFX
                e.consume();                      // stop bubbling further
                updateConfirmState();

            }
        });
    }

    // Explicit pool as a drop target (optional but handy)
    private void hookPoolDnD() {
        poolBox.setOnDragOver(e -> {
            var db = e.getDragboard();
            if (db.hasContent(TILE_ID_FMT)) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });

        poolBox.setOnDragDropped(e -> {
            boolean success = false;
            try {
                if (e.isConsumed()) return; // respect targets

                var db = e.getDragboard();
                if (db.hasContent(TILE_ID_FMT)) {
                    StackPane tile = resolveTileById(db.getContent(TILE_ID_FMT));
                    if (tile != null) {
                        moveTileToPool(tile);
                        success = true;
                    }
                }
            } finally {
                dragDropHandled = true;           // mark handled
                e.setDropCompleted(success);
                e.consume();
                updateConfirmState();
            }
        });
    }

    // ================== Moves / Helpers ==================

    /** Move tile into destSlot without performing swap; caller already handled occupant. */
    private void moveTileToSlotNoSwap(StackPane tile, StackPane destSlot) {
        // Remove from old parent
        Parent p = tile.getParent();
        switch (p) {
            case StackPane oldSlot -> {
                oldSlot.getChildren().remove(tile);
                refreshSlotVisuals(oldSlot);          // <<— was: clear + grey
            }

            case HBox hBox -> hBox.getChildren().remove(tile);

            default -> {
                
            }
        }

        // Add to destination
        destSlot.getChildren().add(tile);
        refreshSlotVisuals(destSlot);             // <<— was: set filled + label
    }

    private void moveTileToPool(StackPane tile) {
        Parent p = tile.getParent();
        switch (p) {
            case StackPane oldSlot -> {
                oldSlot.getChildren().remove(tile);
                refreshSlotVisuals(oldSlot);          // keep it in sync
            }

            case HBox hBox -> hBox.getChildren().remove(tile);

            default -> {
                
            }
        }

        if (!poolBox.getChildren().contains(tile)) {
            poolBox.getChildren().add(tile);
        }
    }

    private StackPane getTileInSlot(StackPane slot) {
        for (Node n : slot.getChildren()) {
            if (n instanceof StackPane sp && sp.getProperties().containsKey("score")) {
                return sp;
            }
        }
        return null;
    }

    private int getTileScore(StackPane tile) {
        Object v = tile.getProperties().get("score");
        if (v instanceof Integer i) return i;
        // Fallback to label text
        for (Node n : tile.getChildren()) {
            if (n instanceof Label lbl) {
                return Integer.parseInt(lbl.getText());
            }
        }
        throw new IllegalStateException("Tile has no score");
    }

    private void clearModLabelForSlot(StackPane slot) {
        Ability a = abilityOf(slot);
        if (a != null) {
            Label mod = abilityModLabels.get(a);
            if (mod != null) { 
                mod.setText("");
            }
        }
    }

    private void updateModLabelForSlot(StackPane slot, int score) {
        Ability a = abilityOf(slot);
        if (a != null) {
            int mod = Math.floorDiv(score - 10, 2);
            abilityModLabels.get(a).setText((mod >= 0 ? "+" : "") + mod);
        }
    }

    private Ability abilityOf(StackPane slot) {
        for (Map.Entry<Ability, StackPane> e : abilitySlots.entrySet()) {
            if (e.getValue() == slot) { 
                return e.getKey();
            }
        }
        return null;
    }

    private boolean allAssigned() {
        for (StackPane slot : abilitySlots.values()) {
            if (getTileInSlot(slot) == null) { 
                return false;
            }
        }
        return true;
    }

    private void updateConfirmState() {
        boolean ready = allAssigned();
        confirmButton.setDisable(!ready);
        confirmButton.setStyle(ready
                ? "-fx-opacity: 1.0; -fx-background-radius: 10;"
                : "-fx-opacity: 0.6; -fx-background-radius: 10;");
    }

    private StackPane resolveTileById(Object idObj) {
        if (idObj == null) return null;
        return tileById.get(String.valueOf(idObj));
    }

    private void refreshSlotVisuals(StackPane slot) {
        StackPane t = getTileInSlot(slot);
        if (t == null) {
            slot.setStyle(SLOT_EMPTY_STYLE);
            clearModLabelForSlot(slot);

        } else {
            slot.setStyle(SLOT_FILLED_STYLE);
            updateModLabelForSlot(slot, getTileScore(t));
        }
    }

    @FXML
    private void handleConfirm() {
        if (!allAssigned()) return;
        Map<Ability, Integer> result = new EnumMap<>(Ability.class);
        abilitySlots.forEach((a, slot) -> {
            StackPane t = getTileInSlot(slot);
            result.put(a, t != null ? (int) t.getProperties().get("score") : null);
        });
        
        // TODO: push 'result' to your character model / next scene
        System.out.println("Assigned: " + result);
    }
}