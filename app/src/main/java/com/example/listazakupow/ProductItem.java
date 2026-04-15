package com.example.listazakupow;

public class ProductItem {
    private  String itemName;
    private int itemQuantity;
    public ProductItem(String itemName, int itemQuantity){
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }
}
