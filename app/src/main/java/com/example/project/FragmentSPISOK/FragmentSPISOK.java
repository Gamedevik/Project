package com.example.project.FragmentSPISOK;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project.R;
import com.example.project.Лапин12.DATABASE.DataManager;
import com.example.project.Лапин12.DATABASE.Item;
import java.util.List;

public class FragmentSPISOK extends Fragment implements ItemAdapter.OnItemClickListener, ItemAdapter.OnFavoriteClickListener {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private DataManager dataManager;
    private LinearLayout emptyLayout;
    private Button btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s_p_i_s_o_k, container, false);

        dataManager = DataManager.getInstance(getContext());

        recyclerView = view.findViewById(R.id.recycler_view);
        emptyLayout = view.findViewById(R.id.empty_layout);
        btnAdd = view.findViewById(R.id.btn_add);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnAdd.setOnClickListener(v -> openAddFragment());

        loadData();

        return view;
    }

    private void loadData() {
        List<Item> items = dataManager.getAllItems();

        if (items.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            adapter = new ItemAdapter(items, this, this);
            recyclerView.setAdapter(adapter);
        }
    }

    private void openAddFragment() {
        AddEditFragment fragment = new AddEditFragment();
        Bundle args = new Bundle();
        args.putBoolean("isEdit", false);
        fragment.setArguments(args);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onItemClick(Item item) {
        // Открываем экран с подробной информацией
        AddEditFragment fragment = new AddEditFragment();
        Bundle args = new Bundle();
        args.putBoolean("isEdit", true);
        args.putInt("itemId", item.getId());
        fragment.setArguments(args);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFavoriteClick(Item item) {
        dataManager.toggleFavorite(item.getId());
        loadData();
        Toast.makeText(getContext(), item.isFavorite() ? "Добавлено в избранное" : "Удалено из избранного", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}