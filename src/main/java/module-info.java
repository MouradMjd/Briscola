module us.teamronda.briscola {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens us.teamronda.briscola to javafx.fxml;
    exports us.teamronda.briscola;

    exports us.teamronda.briscola.gui.components;
    opens us.teamronda.briscola.gui.components to javafx.fxml;

    exports us.teamronda.briscola.gui.components.card;
    opens us.teamronda.briscola.gui.components.card to javafx.fxml;
}