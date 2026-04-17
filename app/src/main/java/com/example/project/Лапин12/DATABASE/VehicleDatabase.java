package com.example.project.Лапин12.DATABASE;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Vehicle.class}, version = 1, exportSchema = false)
public abstract class VehicleDatabase extends RoomDatabase {

    public abstract VehicleDao vehicleDao();

    private static volatile VehicleDatabase INSTANCE = null;

    public static VehicleDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (VehicleDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            VehicleDatabase.class,
                            "hoi4_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}