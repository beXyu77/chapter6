package com.se233.chapter1.controller;

import com.se233.chapter1.model.DamageType;
import com.se233.chapter1.model.item.Armor;
import com.se233.chapter1.model.item.BasedEquipment;
import com.se233.chapter1.model.item.Weapon;

import java.util.ArrayList;

public class GenItemList {
    public static ArrayList<BasedEquipment> setUpItemList() {
        ArrayList<BasedEquipment> itemLists = new ArrayList<>(5);
        itemLists.add(new Weapon("Sword", 10, DamageType.physical, "assets/sword.png"));
        itemLists.add(new Weapon("Gun", 15, DamageType.physical, "assets/gun.png"));
        itemLists.add(new Weapon("Armored Stick", 13, DamageType.physical, "assets/armoredstick.png"));
        itemLists.add(new Weapon("Staff", 20, DamageType.magical, "assets/staff.png"));
        itemLists.add(new Armor("Shirt", 0, 50, "assets/shirt.png"));
        itemLists.add(new Armor("Armor", 50, 0, "assets/armor.png"));
        return itemLists;
    }
}
