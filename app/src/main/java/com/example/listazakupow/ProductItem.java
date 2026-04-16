package com.example.listazakupow;

public class ProductItem {
    private  String itemName;
    private int itemQuantity;
    private String itemCategory;
    public ProductItem(String itemName, int itemQuantity, String itemCategory){
        this.itemCategory = itemCategory;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }
}
