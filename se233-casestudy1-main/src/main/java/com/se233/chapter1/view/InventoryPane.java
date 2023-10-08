package com.se233.chapter1.view;

import com.se233.chapter1.Launcher;
import com.se233.chapter1.controller.AllCustomHandler;
import com.se233.chapter1.model.item.BasedEquipment;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class InventoryPane extends ScrollPane {
    private ArrayList<BasedEquipment> equipmentArray;

    public InventoryPane() {}

    private Pane getDetailsPane() {
        Pane inventoryInfoPane = new HBox(10);
        inventoryInfoPane.setBorder(null);
        inventoryInfoPane.setPadding(new Insets(25, 25, 25, 25));

        if (equipmentArray != null) {
            ImageView[] imageViewList = new ImageView[equipmentArray.size()];
            for (int i = 0; i < equipmentArray.size(); i++) {
                imageViewList[i] = new ImageView();
                imageViewList[i].setImage(new Image(Launcher.class.getResource(
                        equipmentArray.get(i).getImagePath()).toString()));
                int finalI = i;
                imageViewList[i].setOnDragDetected(mouseEvent -> {
                    AllCustomHandler.onDragDetected(mouseEvent, equipmentArray.get(finalI), imageViewList[finalI]);
                });
                imageViewList[i].setOnDragDone(AllCustomHandler::onEquipDone);
            }
            inventoryInfoPane.getChildren().addAll(imageViewList);
        }
        return inventoryInfoPane;
    }

    public void drawPane(ArrayList<BasedEquipment> equipmentArray) {
        this.equipmentArray = equipmentArray;
        Pane inventoryInfo = getDetailsPane();
        this.setStyle("-fx-background-color: Red");
        this.setContent(inventoryInfo);
    }
}
