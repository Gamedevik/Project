package com.example.project.Тронин13.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_ITEM_ID = "item_id";
    private static final String COLUMN_ITEM_TITLE = "item_title";
    private static final String COLUMN_ITEM_SHORT_TEXT = "item_short_text";
    private static final String COLUMN_ITEM_PRICE = "item_price";

    private static FavoriteDatabaseHelper instance;
    private SQLiteDatabase database;

    private FavoriteDatabaseHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    public static synchronized FavoriteDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteDatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_FAVORITES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER NOT NULL, " +
                COLUMN_ITEM_ID + " INTEGER NOT NULL, " +
                COLUMN_ITEM_TITLE + " TEXT NOT NULL, " +
                COLUMN_ITEM_SHORT_TEXT + " TEXT NOT NULL, " +
                COLUMN_ITEM_PRICE + " INTEGER NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    public boolean addFavorite(int userId, int itemId, String title, String shortText, int price) {
        if (isFavorite(userId, itemId)) {
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_ITEM_ID, itemId);
        values.put(COLUMN_ITEM_TITLE, title);
        values.put(COLUMN_ITEM_SHORT_TEXT, shortText);
        values.put(COLUMN_ITEM_PRICE, price);

        long result = database.insert(TABLE_FAVORITES, null, values);
        return result != -1;
    }

    public boolean removeFavorite(int userId, int itemId) {
        String where = COLUMN_USER_ID + " = ? AND " + COLUMN_ITEM_ID + " = ?";
        String[] whereArgs = {String.valueOf(userId), String.valueOf(itemId)};
        int result = database.delete(TABLE_FAVORITES, where, whereArgs);
        return result > 0;
    }

    public boolean isFavorite(int userId, int itemId) {
        String where = COLUMN_USER_ID + " = ? AND " + COLUMN_ITEM_ID + " = ?";
        String[] whereArgs = {String.valueOf(userId), String.valueOf(itemId)};
        Cursor cursor = database.query(TABLE_FAVORITES, null, where, whereArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Cursor getFavoritesByUser(int userId) {
        String where = COLUMN_USER_ID + " = ?";
        String[] whereArgs = {String.valueOf(userId)};
        return database.query(TABLE_FAVORITES, null, where, whereArgs, null, null, null);
    }
}