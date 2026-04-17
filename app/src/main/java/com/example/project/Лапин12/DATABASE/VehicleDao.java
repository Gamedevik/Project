package com.example.project.Лапин12.DATABASE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface VehicleDao {

    @Insert
    void insert(Vehicle vehicle);

    @Update
    void update(Vehicle vehicle);

    @Delete
    void delete(Vehicle vehicle);

    @Query("SELECT * FROM vehicles ORDER BY id DESC")
    List<Vehicle> getAllVehicles();

    @Query("SELECT * FROM vehicles WHERE isFavorite = 1")
    List<Vehicle> getFavoriteVehicles();

    @Query("SELECT * FROM vehicles WHERE type = :type")
    List<Vehicle> getVehiclesByType(String type);

    @Query("UPDATE vehicles SET isFavorite = :isFavorite WHERE id = :vehicleId")
    void updateFavoriteStatus(int vehicleId, boolean isFavorite);
}