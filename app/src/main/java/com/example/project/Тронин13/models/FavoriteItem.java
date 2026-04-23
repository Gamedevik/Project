package com.example.project.Тронин13.models;

public class FavoriteItem {
    private int id;
    private int userId;
    private int itemId;
    private String title;
    private String shortText;
    private int price;

    public FavoriteItem(int id, int userId, int itemId, String title, String shortText, int price) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.title = title;
        this.shortText = shortText;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getShortText() { return shortText; }
    public void setShortText(String shortText) { this.shortText = shortText; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
}