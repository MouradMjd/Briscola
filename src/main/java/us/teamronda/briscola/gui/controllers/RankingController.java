package us.teamronda.briscola.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.Setter;
import us.teamronda.briscola.LogicGame;
import us.teamronda.briscola.api.player.IPlayer;
import us.teamronda.briscola.gui.Guis;
import us.teamronda.briscola.gui.SceneSwitcher;
import us.teamronda.briscola.objects.Player;


import java.io.IOException;
import java.util.List;

public class RankingController extends SceneSwitcher {
    @Getter
    private static  RankingController instace;
    @FXML @Getter
    private AnchorPane pane;
    @FXML
    Button NextGame;
    @FXML
    VBox boxforid;

    public void initialize() {
        instace=this;
        setSceneHolder(NextGame);
    }
    @FXML
    public void onKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) switchTo(Guis.START);
    }

    @FXML
    public void onButtonClicked(ActionEvent event) throws IOException {
        switchTo(Guis.START);
    }
    public void putranking(List<IPlayer> players) {

        Font font=new Font(20);
        for (int i = 0; i < players.size(); i++) {
            Label l = new Label((i+1)+")"+players.get(i).getUsername());
            l.getStyleClass().add("Label");
            l.setFont(font);

            // Aggiungi la Label al pane
            boxforid.getChildren().add(l);
        }
    }



}
