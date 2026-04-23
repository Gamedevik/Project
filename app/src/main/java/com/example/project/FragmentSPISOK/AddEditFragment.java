package com.example.project.FragmentSPISOK;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.project.R;
import com.example.project.Лапин12.DATABASE.DataManager;
import com.example.project.Лапин12.DATABASE.Item;

public class AddEditFragment extends Fragment {

    private EditText etTitle, etShortText, etPrice;
    private Button btnSave, btnDelete;
    private DataManager dataManager;
    private boolean isEdit;
    private int itemId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);

        dataManager = DataManager.getInstance(getContext());

        etTitle = view.findViewById(R.id.et_title);
        etShortText = view.findViewById(R.id.et_short_text);
        etPrice = view.findViewById(R.id.et_price);
        btnSave = view.findViewById(R.id.btn_save);
        btnDelete = view.findViewById(R.id.btn_delete);

        Bundle args = getArguments();
        if (args != null) {
            isEdit = args.getBoolean("isEdit", false);
            if (isEdit) {
                itemId = args.getInt("itemId");
                loadItemData();
                btnDelete.setVisibility(View.VISIBLE);
            } else {
                btnDelete.setVisibility(View.GONE);
            }
        }

        btnSave.setOnClickListener(v -> saveItem());
        btnDelete.setOnClickListener(v -> showDeleteDialog());

        return view;
    }

    private void loadItemData() {
        Item item = dataManager.getItemById(itemId);
        if (item != null) {
            etTitle.setText(item.getTitle());
            etShortText.setText(item.getShortText());
            etPrice.setText(String.valueOf(item.getPrice()));
        }
    }

    private void saveItem() {
        String title = etTitle.getText().toString().trim();
        String shortText = etShortText.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("Введите название");
            return;
        }
        if (shortText.isEmpty()) {
            etShortText.setError("Введите описание");
            return;
        }
        if (priceStr.isEmpty()) {
            etPrice.setError("Введите цену");
            return;
        }

        int price = Integer.parseInt(priceStr);

        if (isEdit) {
            Item item = dataManager.getItemById(itemId);
            if (item != null) {
                item.setTitle(title);
                item.setShortText(shortText);
                item.setPrice(price);
                dataManager.updateItem(item);
                Toast.makeText(getContext(), "Изменено", Toast.LENGTH_SHORT).show();
            }
        } else {
            int newId = dataManager.getNextId();
            Item newItem = new Item(newId, title, shortText, price, false);
            dataManager.addItem(newItem);
            Toast.makeText(getContext(), "Добавлено", Toast.LENGTH_SHORT).show();
        }

        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Удаление")
                .setMessage("Вы уверены, что хотите удалить этот предмет?")
                .setPositiveButton("Да", (dialog, which) -> {
                    dataManager.deleteItem(itemId);
                    Toast.makeText(getContext(), "Удалено", Toast.LENGTH_SHORT).show();
                    requireActivity().getSupportFragmentManager().popBackStack();
                })
                .setNegativeButton("Нет", null)
                .show();
    }
}