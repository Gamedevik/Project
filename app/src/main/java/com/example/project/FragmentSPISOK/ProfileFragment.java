package com.example.project.FragmentSPISOK;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.project.R;
import com.example.project.Тронин11.utils.SessionManager;

public class ProfileFragment extends Fragment {

    private TextView tvUserEmail;
    private Button btnLogout;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = SessionManager.getInstance(getContext());

        tvUserEmail = view.findViewById(R.id.tv_user_email);
        btnLogout = view.findViewById(R.id.btn_logout);

        String email = sessionManager.getUserEmail();
        tvUserEmail.setText(email);

        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            requireActivity().finish();
        });

        return view;
    }
}