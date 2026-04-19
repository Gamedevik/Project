package com.example.project.FragmentSPISOK;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project.R;
import com.example.project.Лапин12.DATABASE.DataManager;
import com.example.project.Лапин12.DATABASE.Item;
import java.util.List;

public class FragmentSPISOK extends Fragment {

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

        loadData();

        btnAdd.setOnClickListener(v -> showAddDialog());

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
            adapter = new ItemAdapter(items);
            recyclerView.setAdapter(adapter);
        }
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Добавить предмет");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit, null);
        com.google.android.material.textfield.TextInputEditText etTitle = dialogView.findViewById(R.id.et_title);
        com.google.android.material.textfield.TextInputEditText etShortText = dialogView.findViewById(R.id.et_short_text);
        com.google.android.material.textfield.TextInputEditText etPrice = dialogView.findViewById(R.id.et_price);

        builder.setView(dialogView);
        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String title = etTitle.getText().toString().trim();
            String shortText = etShortText.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(getContext(), "Введите название", Toast.LENGTH_SHORT).show();
                return;
            }
            if (shortText.isEmpty()) {
                Toast.makeText(getContext(), "Введите описание", Toast.LENGTH_SHORT).show();
                return;
            }
            if (priceStr.isEmpty()) {
                Toast.makeText(getContext(), "Введите цену", Toast.LENGTH_SHORT).show();
                return;
            }

            int price = Integer.parseInt(priceStr);
            dataManager.addItem(title, shortText, price);
            loadData();
            Toast.makeText(getContext(), "Добавлено", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Отмена", null);
        builder.show();
    }

    private void showEditDialog(Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Редактировать предмет");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit, null);
        com.google.android.material.textfield.TextInputEditText etTitle = dialogView.findViewById(R.id.et_title);
        com.google.android.material.textfield.TextInputEditText etShortText = dialogView.findViewById(R.id.et_short_text);
        com.google.android.material.textfield.TextInputEditText etPrice = dialogView.findViewById(R.id.et_price);

        etTitle.setText(item.getTitle());
        etShortText.setText(item.getShortText());
        etPrice.setText(String.valueOf(item.getPrice()));

        builder.setView(dialogView);
        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String title = etTitle.getText().toString().trim();
            String shortText = etShortText.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(getContext(), "Введите название", Toast.LENGTH_SHORT).show();
                return;
            }
            if (shortText.isEmpty()) {
                Toast.makeText(getContext(), "Введите описание", Toast.LENGTH_SHORT).show();
                return;
            }
            if (priceStr.isEmpty()) {
                Toast.makeText(getContext(), "Введите цену", Toast.LENGTH_SHORT).show();
                return;
            }

            int price = Integer.parseInt(priceStr);
            item.setTitle(title);
            item.setShortText(shortText);
            item.setPrice(price);
            dataManager.updateItem(item);
            loadData();
            Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_SHORT).show();
        });
        builder.setNeutralButton("Удалить", (dialog, which) -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Удаление")
                    .setMessage("Вы уверены?")
                    .setPositiveButton("Да", (d, w) -> {
                        dataManager.deleteItem(item.getId());
                        loadData();
                        Toast.makeText(getContext(), "Удалено", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Нет", null)
                    .show();
        });
        builder.setNegativeButton("Отмена", null);
        builder.show();
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
        private List<Item> items;

        ItemAdapter(List<Item> items) {
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Item item = items.get(position);
            holder.tvTitle.setText(item.getTitle());
            holder.tvShortText.setText(item.getShortText());
            holder.tvPrice.setText(item.getPrice() + " ₽");
            holder.btnFavorite.setText(item.isFavorite() ? "★" : "☆");

            holder.itemView.setOnClickListener(v -> showEditDialog(item));

            holder.btnFavorite.setOnClickListener(v -> {
                dataManager.toggleFavorite(item.getId());
                loadData();
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle, tvShortText, tvPrice;
            Button btnFavorite;

            ViewHolder(View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tv_title);
                tvShortText = itemView.findViewById(R.id.tv_short_text);
                tvPrice = itemView.findViewById(R.id.tv_price);
                btnFavorite = itemView.findViewById(R.id.btn_favorite);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}