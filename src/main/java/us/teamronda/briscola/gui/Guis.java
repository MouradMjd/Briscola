package us.teamronda.briscola.gui;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Guis {

    START("start.fxml"),
    TABLE("table.fxml");

    private final String name;

    public String getPath() {
        return "/us/teamronda/briscola/gui/fxmls/" + name;
    }
}
