package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.project.FragmentSPISOK.FavoritesFragment;
import com.example.project.FragmentSPISOK.FragmentSPISOK;
import com.example.project.FragmentSPISOK.ProfileFragment;
import com.example.project.Тронин11.ui.auth.LoginActivity;
import com.example.project.Тронин11.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.project.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = SessionManager.getInstance(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Загружаем главный фрагмент по умолчанию
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FragmentSPISOK())
                    .commit();
        }

        // Обработчик нажатий на пункты нижнего меню
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                selectedFragment = new FragmentSPISOK();
            } else if (itemId == R.id.navigation_favorites) {
                selectedFragment = new FavoritesFragment();
            } else if (itemId == R.id.navigation_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }

    // Метод для выхода (может вызываться из ProfileFragment)
    public void logout() {
        sessionManager.logout();
        Toast.makeText(this, "Вы вышли из системы", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}