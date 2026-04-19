package com.example.project.Лапин12.DATABASE;

public class Item {
    private int id;
    private String title;
    private String shortText;
    private int price;
    private boolean isFavorite;

    public Item(int id, String title, String shortText, int price, boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.shortText = shortText;
        this.price = price;
        this.isFavorite = isFavorite;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getShortText() { return shortText; }
    public void setShortText(String shortText) { this.shortText = shortText; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}