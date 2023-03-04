module fr.thesakyo.JavaFX {

    requires javafx.fxml;
    requires javafx.web;

    opens fr.thesakyo.JavaFX to javafx.fxml;
    exports fr.thesakyo.JavaFX;

    opens fr.thesakyo.JavaFX.view to javafx.fxml;
    exports fr.thesakyo.JavaFX.view;

    opens fr.thesakyo.JavaFX.model to javafx.fxml;
    exports fr.thesakyo.JavaFX.model;
}