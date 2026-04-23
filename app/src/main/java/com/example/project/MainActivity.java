package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project.FragmentSPISOK.FragmentSPISOK;
import com.example.project.Тронин11.ui.auth.LoginActivity;
import com.example.project.Тронин11.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = SessionManager.getInstance(this);
        btnLogout = findViewById(R.id.btnLogout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FragmentSPISOK())
                    .commit();
        }

        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            Toast.makeText(this, "Вы вышли из системы", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}