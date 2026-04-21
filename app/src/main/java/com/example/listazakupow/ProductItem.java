package com.example.listazakupow;
public class ProductItem {
    private String name;
    private int quantity;
    private String category;

    public ProductItem(String name, int quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public String getCategory() { return category; }
}