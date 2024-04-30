module us.teamronda.briscola {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens us.teamronda.briscola to javafx.fxml;
    exports us.teamronda.briscola;
}