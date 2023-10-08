package com.se233.chapter1.view;

import com.se233.chapter1.Launcher;
import com.se233.chapter1.controller.AllCustomHandler;
import com.se233.chapter1.model.item.Armor;
import com.se233.chapter1.model.item.Weapon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Stack;

public class EquipPane extends ScrollPane {
    private Weapon equippedWeapon;
    private Armor equippedArmor;

    public EquipPane() {
    }

    private Pane getDetailsPane() {
        VBox equipmentInfoPane = new VBox(10);
        equipmentInfoPane.setBorder(null);
        equipmentInfoPane.setAlignment(Pos.CENTER);
        equipmentInfoPane.setPadding(new Insets(25, 25, 25, 25));
        Label weaponLbl, armorLbl;
        StackPane weaponImgGroup = new StackPane();
        StackPane armorImgGroup = new StackPane();
        ImageView bg1 = new ImageView();
        ImageView bg2 = new ImageView();
        ImageView weaponImg = new ImageView();
        ImageView armorImg = new ImageView();
        bg1.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        bg2.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        weaponImgGroup.getChildren().add(bg1);
        armorImgGroup.getChildren().add(bg2);

        if (equippedWeapon != null) {
            weaponLbl = new Label("Weapon: \n" + equippedWeapon.getName());
            weaponImg.setImage(new Image(Launcher.class.getResource(equippedWeapon.getImagePath()).toString()));
            weaponImgGroup.getChildren().add(weaponImg);
        } else {
            weaponLbl = new Label("Weapon: ");
            weaponImg.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        }

        if (equippedArmor != null) {
            armorLbl = new Label("Armor: \n" + equippedArmor.getName());
            armorImg.setImage(new Image(Launcher.class.getResource(equippedArmor.getImagePath()).toString()));
            armorImgGroup.getChildren().add(armorImg);
        } else {
            armorLbl = new Label("Armor: ");
            armorImg.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        }

        weaponImgGroup.setOnDragOver(dragEvent -> {
            AllCustomHandler.onDragOver(dragEvent, "Weapon");
        });

        armorImgGroup.setOnDragOver(dragEvent -> {
            AllCustomHandler.onDragOver(dragEvent, "Armor");
        });

        weaponImgGroup.setOnDragDropped(dragEvent -> {
            AllCustomHandler.onDragDropped(dragEvent, weaponLbl, weaponImgGroup);
        });

        armorImgGroup.setOnDragDropped(dragEvent -> {
            AllCustomHandler.onDragDropped(dragEvent, armorLbl, armorImgGroup);
        });

        weaponImgGroup.setOnDragDetected(mouseEvent -> {
            AllCustomHandler.onDragDetected(mouseEvent, equippedWeapon, weaponImg);
        });

        weaponImgGroup.setOnDragDone(dragEvent -> {
            AllCustomHandler.dropDoneItemFromSlot(dragEvent, weaponLbl, weaponImgGroup);
        });

        armorImgGroup.setOnDragDetected(mouseEvent -> {
            AllCustomHandler.onDragDetected(mouseEvent, equippedArmor, armorImg);
        });

        armorImgGroup.setOnDragDone(dragEvent -> {
            AllCustomHandler.dropDoneItemFromSlot(dragEvent, weaponLbl, armorImgGroup);
        });


        equipmentInfoPane.getChildren().addAll(weaponLbl, weaponImgGroup, armorLbl, armorImgGroup);
        return equipmentInfoPane;
    }

    public void drawPane(Weapon equippedWeapon, Armor equippedArmor) {
        this.equippedWeapon = equippedWeapon;
        this.equippedArmor = equippedArmor;
        Pane equipmentInfo = getDetailsPane();
        this.setStyle("-fx-background-color: Red");
        this.setContent(equipmentInfo);
    }

}
