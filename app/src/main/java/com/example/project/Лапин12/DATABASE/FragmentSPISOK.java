package com.example.project.Лапин12.DATABASE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.R;

public class FragmentSPISOK extends Fragment {


    public FragmentSPISOK() {

    }


    public static FragmentSPISOK newInstance(String param1, String param2) {
        FragmentSPISOK fragment = new FragmentSPISOK();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_s_p_i_s_o_k, container, false);
    }

}