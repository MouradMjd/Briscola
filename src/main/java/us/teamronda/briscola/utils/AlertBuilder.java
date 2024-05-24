package us.teamronda.briscola.utils;

import javafx.scene.control.Alert;

/**
 * Small utility class to build {@link Alert} objects.
 */
public class AlertBuilder {

    public static AlertBuilder withType(Alert.AlertType type) {
        return new AlertBuilder(type);
    }

    private final Alert alert;

    private AlertBuilder(Alert.AlertType type) {
        if (type == null) throw new IllegalArgumentException("AlertType cannot be null");

        this.alert = new Alert(type);
        this.alert.setHeaderText(switch (type) {
            case ERROR -> "Fatal Error";
            case WARNING -> "Warning";
            default -> "Hey!";
        });
    }

    public AlertBuilder title(String title) {
        alert.setTitle(title);
        return this;
    }

    public AlertBuilder header(String header) {
        alert.setHeaderText(header);
        return this;
    }

    public AlertBuilder content(String content) {
        alert.setContentText(content);
        return this;
    }

    public Alert build() {
        return alert;
    }

    public void show() {
        alert.show();
    }

    public void showAndWait() {
        alert.showAndWait();
    }
}
