package com.example.project.Лапин12.DATABASE;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vehicles")
public class Vehicle {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;          // "T-34", "Bf 109", "Yamato"
    private String type;          // "tank", "plane", "ship"
    private int cost;             // промышленная цена (IC)
    private boolean isFavorite;   // избранное

    // Конструктор (без id)
    public Vehicle(String name, String type, int cost, boolean isFavorite) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.isFavorite = isFavorite;
    }

    // Геттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public int getCost() { return cost; }
    public boolean isFavorite() { return isFavorite; }

    // Сеттеры
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setCost(int cost) { this.cost = cost; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}
