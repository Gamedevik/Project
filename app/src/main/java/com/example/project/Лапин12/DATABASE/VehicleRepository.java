package com.example.project.Лапин12.DATABASE;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VehicleRepository {

    private VehicleDao vehicleDao;
    private ExecutorService executorService;
    private Handler mainHandler;

    public VehicleRepository(Context context) {
        VehicleDatabase db = VehicleDatabase.getInstance(context);
        vehicleDao = db.vehicleDao();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public void insert(Vehicle vehicle) {
        executorService.execute(() -> vehicleDao.insert(vehicle));
    }

    public void update(Vehicle vehicle) {
        executorService.execute(() -> vehicleDao.update(vehicle));
    }

    public void delete(Vehicle vehicle) {
        executorService.execute(() -> vehicleDao.delete(vehicle));
    }

    public void updateFavoriteStatus(int vehicleId, boolean isFavorite) {
        executorService.execute(() -> vehicleDao.updateFavoriteStatus(vehicleId, isFavorite));
    }

    public void getAllVehicles(final DataCallback<List<Vehicle>> callback) {
        executorService.execute(() -> {
            List<Vehicle> list = vehicleDao.getAllVehicles();
            if (callback != null) {
                mainHandler.post(() -> callback.onData(list));
            }
        });
    }

    public void getFavoriteVehicles(final DataCallback<List<Vehicle>> callback) {
        executorService.execute(() -> {
            List<Vehicle> list = vehicleDao.getFavoriteVehicles();
            if (callback != null) {
                mainHandler.post(() -> callback.onData(list));
            }
        });
    }

    public interface DataCallback<T> {
        void onData(T data);
    }
}