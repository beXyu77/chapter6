package com.se233.chapter1.controller;

import com.se233.chapter1.Launcher;
import com.se233.chapter1.model.character.BasedCharacter;
import com.se233.chapter1.model.character.BattleMageCharacter;
import com.se233.chapter1.model.item.Armor;
import com.se233.chapter1.model.item.BasedEquipment;
import com.se233.chapter1.model.item.Weapon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AllCustomHandler {
    static Logger logger = LogManager.getLogger(AllCustomHandler.class);
    public static class GenCharacterHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Launcher.setMainCharacter(GenCharacter.setupCharacter());
            Launcher.setEquippedWeapon(null);
            Launcher.setEquippedArmor(null);
            Launcher.setAllEquipments(GenItemList.setUpItemList());
            Launcher.refreshPane();
        }
    }

    public static class RemoveEquipmentBtnHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            Launcher.setEquippedArmor(null);
            Launcher.setEquippedWeapon(null);
            Launcher.getMainCharacter().removeAllEquipments();

            Launcher.setAllEquipments(GenItemList.setUpItemList());
            Launcher.refreshPane();
        }
    }

    public static void onDragDetected(MouseEvent event, BasedEquipment equipment, ImageView imgView) {
        Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(imgView.getImage());
        ClipboardContent content = new ClipboardContent();
        content.put(BasedEquipment.DATA_FORMAT, equipment);
        db.setContent(content);
        event.consume();
    }

    public static void onDragOver(DragEvent event, String type) {
        Dragboard dragboard = event.getDragboard();
        BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);
        if (dragboard.hasContent(BasedEquipment.DATA_FORMAT) && retrievedEquipment.getClass().getSimpleName().equals(type) && equippable(retrievedEquipment, Launcher.getMainCharacter())) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
    }

    protected static boolean equippable(BasedEquipment equipment, BasedCharacter character) {
        if (equipment instanceof Armor && character instanceof BattleMageCharacter) {
            return false;
        }
        if (equipment instanceof Weapon) {
            return ((Weapon) equipment).getDamageType().equals(character.getType());
        }

        return true;
    }

    public static void onDragDropped(DragEvent event, Label lbl, StackPane imgGroup) {
        boolean dragCompleted = false;
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        if (dragboard.hasContent(BasedEquipment.DATA_FORMAT)) {
            BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);
            logger.info(retrievedEquipment.getName());
            if (equippable(retrievedEquipment, Launcher.getMainCharacter())) {
                BasedCharacter character = Launcher.getMainCharacter();
                if (retrievedEquipment.getClass().getSimpleName().equals("Weapon")) {
                    if (Launcher.getEquippedWeapon() != null) {
                        allEquipments.add(Launcher.getEquippedWeapon());
                    }
                    Launcher.setEquippedWeapon((Weapon) retrievedEquipment);
                    character.equipWeapon((Weapon) retrievedEquipment);
                } else {
                    if (Launcher.getEquippedArmor() != null) {
                        allEquipments.add(Launcher.getEquippedArmor());
                    }
                    Launcher.setEquippedArmor((Armor) retrievedEquipment);
                    character.equipArmor((Armor) retrievedEquipment);

                }
                ImageView imgView = new ImageView();
                if (imgGroup.getChildren().size() != 1) {
                    imgGroup.getChildren().remove(1);
                    Launcher.refreshPane();
                }
                lbl.setText(retrievedEquipment.getClass().getSimpleName() + ":\n" + retrievedEquipment.getName());
                imgView.setImage(new Image(Launcher.class.getResource(retrievedEquipment.getImagePath()).toString()));
                imgGroup.getChildren().add(imgView);
            }
            dragCompleted = true;
        }
        event.setDropCompleted(dragCompleted);
    }

    public static void onEquipDone(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        int eqLength = allEquipments.size();
        for (int i = 0; i < eqLength; i++) {
            String equippedWeaponName = Launcher.getEquippedWeapon() == null ? "" : Launcher.getEquippedWeapon().getName();
            String equippedArmorName = Launcher.getEquippedArmor() == null ? "" : Launcher.getEquippedArmor().getName();
            if (allEquipments.get(i).getName().equals(equippedArmorName) || allEquipments.get(i).getName().equals(equippedWeaponName)) {
                allEquipments.remove(i);
                break;
            }
        }
        Launcher.setAllEquipments(allEquipments);
        Launcher.refreshPane();
    }

    public static void dropDoneItemFromSlot(DragEvent event, Label lbl, StackPane imgGroup) {
        Dragboard dragboard = event.getDragboard();
        BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);

        if (retrievedEquipment.getClass().getSimpleName().equals("Weapon")) {
            Launcher.setEquippedWeapon(null);
            Launcher.getMainCharacter().unequipWeapon();
        } else {
            Launcher.setEquippedArmor(null);
            Launcher.getMainCharacter().unequipArmor();
        }

        Launcher.getAllEquipments().add(retrievedEquipment);

        Launcher.refreshPane();


    }
}
