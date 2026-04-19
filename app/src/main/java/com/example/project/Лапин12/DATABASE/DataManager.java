package com.example.project.Лапин12.DATABASE;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    private Context context;
    private List<Item> itemList;
    private SharedPreferences sharedPreferences;
    private int nextId;

    private DataManager(Context context) {
        this.context = context;
        this.itemList = new ArrayList<>();
        this.sharedPreferences = context.getSharedPreferences("app_data", Context.MODE_PRIVATE);
        loadData();
    }

    public static synchronized DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context.getApplicationContext());
        }
        return instance;
    }

    private void loadData() {
        // Загружаем сохраненные данные
        int size = sharedPreferences.getInt("list_size", 0);
        nextId = sharedPreferences.getInt("next_id", 1);

        for (int i = 0; i < size; i++) {
            int id = sharedPreferences.getInt("item_" + i + "_id", 0);
            String title = sharedPreferences.getString("item_" + i + "_title", "");
            String shortText = sharedPreferences.getString("item_" + i + "_shortText", "");
            int price = sharedPreferences.getInt("item_" + i + "_price", 0);
            boolean isFavorite = sharedPreferences.getBoolean("item_" + i + "_favorite", false);

            if (id != 0) {
                itemList.add(new Item(id, title, shortText, price, isFavorite));
            }
        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("list_size", itemList.size());
        editor.putInt("next_id", nextId);

        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            editor.putInt("item_" + i + "_id", item.getId());
            editor.putString("item_" + i + "_title", item.getTitle());
            editor.putString("item_" + i + "_shortText", item.getShortText());
            editor.putInt("item_" + i + "_price", item.getPrice());
            editor.putBoolean("item_" + i + "_favorite", item.isFavorite());
        }
        editor.apply();
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(itemList);
    }

    public List<Item> getFavoriteItems() {
        List<Item> favorites = new ArrayList<>();
        for (Item item : itemList) {
            if (item.isFavorite()) {
                favorites.add(item);
            }
        }
        return favorites;
    }

    public void addItem(String title, String shortText, int price) {
        Item newItem = new Item(nextId, title, shortText, price, false);
        itemList.add(newItem);
        nextId++;
        saveData();
    }

    public void updateItem(Item item) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getId() == item.getId()) {
                itemList.set(i, item);
                break;
            }
        }
        saveData();
    }

    public void deleteItem(int id) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getId() == id) {
                itemList.remove(i);
                break;
            }
        }
        saveData();
    }

    public void toggleFavorite(int id) {
        for (Item item : itemList) {
            if (item.getId() == id) {
                item.setFavorite(!item.isFavorite());
                break;
            }
        }
        saveData();
    }

    public Item getItemById(int id) {
        for (Item item : itemList) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}