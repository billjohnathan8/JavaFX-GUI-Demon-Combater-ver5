module guidemon {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    exports guidemon; // your Main class is here

    // FXML controllers live here in your repo:
    opens guidemon.view.controller to javafx.fxml;
}