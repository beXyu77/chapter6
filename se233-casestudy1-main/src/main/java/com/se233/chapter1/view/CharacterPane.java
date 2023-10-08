package com.se233.chapter1.view;

import com.se233.chapter1.Launcher;
import com.se233.chapter1.controller.AllCustomHandler;
import com.se233.chapter1.model.character.BasedCharacter;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CharacterPane extends ScrollPane {
    private BasedCharacter character;

    public CharacterPane() {
    }

    private Pane getDetailsPane() {
        Pane characterInfoPane = new VBox(10);
        characterInfoPane.setBorder(null);
        characterInfoPane.setPadding(new Insets(25, 25, 25, 25));
        Label name, type, hp, atk, def, res;
        ImageView mainImage = new ImageView();

        if (this.character != null) {
            name = new Label("Name: " + this.character.getName());
            mainImage.setImage(new Image(Launcher.class.getResource(this.character.getImagePath()).toString()));
            hp = new Label("HP: " + this.character.getHp().toString() + "/" + this.character.getFullHp().toString());
            type = new Label("Type: " + this.character.getType().toString());
            atk = new Label("ATK: " + this.character.getPower().toString());
            def = new Label("DEF: " + this.character.getDefense().toString());
            res = new Label("RES: " + this.character.getResistance().toString());
        } else {
            name = new Label("Name: ");
            mainImage.setImage(new Image(Launcher.class.getResource("assets/unknown.png").toString()));
            hp = new Label("HP: ");
            type = new Label("Type: ");
            atk = new Label("ATK: ");
            def = new Label("DEF: ");
            res = new Label("RES: ");
        }
        Button genCharacter = new Button();
        genCharacter.setText("Generate Character");
        genCharacter.setOnAction(new AllCustomHandler.GenCharacterHandler());

        Button removeEquipmentBtn = new Button();
        removeEquipmentBtn.setText("Remove Equipments");
        removeEquipmentBtn.setOnAction(new AllCustomHandler.RemoveEquipmentBtnHandler());
        characterInfoPane.getChildren().addAll(name, mainImage, type, hp, atk, def, res, genCharacter, removeEquipmentBtn);
        return characterInfoPane;
    }

    public void drawPane(BasedCharacter character) {
        this.character = character;
        Pane chraacterInfoPane = getDetailsPane();
        this.setStyle("-fx-background-color: Red;");
        this.setContent(chraacterInfoPane);
    }
}
