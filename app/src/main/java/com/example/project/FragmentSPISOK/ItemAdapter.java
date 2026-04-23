package com.example.project.FragmentSPISOK;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project.R;
import com.example.project.Лапин12.DATABASE.Item;
import com.example.project.Тронин13.data.FavoriteDatabaseHelper;
import com.example.project.Тронин11.utils.SessionManager;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private static final String TAG = "ITEM_ADAPTER";
    private List<Item> items;
    private OnItemClickListener itemClickListener;
    private OnFavoriteClickListener favoriteClickListener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Item item);
    }

    public ItemAdapter(List<Item> items, OnItemClickListener itemClickListener, OnFavoriteClickListener favoriteClickListener) {
        this.items = items;
        this.itemClickListener = itemClickListener;
        this.favoriteClickListener = favoriteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);

        Log.d(TAG, "=== НАЧАЛО onBindViewHolder ===");
        Log.d(TAG, "Позиция: " + position);
        Log.d(TAG, "Название предмета: " + item.getTitle());
        Log.d(TAG, "ID предмета: " + item.getId());

        holder.tvTitle.setText(item.getTitle());
        holder.tvShortText.setText(item.getShortText());
        holder.tvPrice.setText(item.getPrice() + " ₽");

        SessionManager sessionManager = SessionManager.getInstance(holder.itemView.getContext());
        FavoriteDatabaseHelper favoriteDb = FavoriteDatabaseHelper.getInstance(holder.itemView.getContext());

        int userId = sessionManager.getUserId();
        Log.d(TAG, "getUserId() возвращает: " + userId);
        Log.d(TAG, "isLoggedIn(): " + sessionManager.isLoggedIn());
        Log.d(TAG, "getUserEmail(): " + sessionManager.getUserEmail());

        boolean isFav = false;
        if (userId != -1) {
            isFav = favoriteDb.isFavorite(userId, item.getId());
            Log.d(TAG, "isFavorite(" + userId + ", " + item.getId() + ") = " + isFav);
        } else {
            Log.e(TAG, "ОШИБКА: userId = -1, пользователь не авторизован!");
        }

        if (userId != -1 && isFav) {
            holder.btnFavorite.setText("★");
            Log.d(TAG, "Установлен значок ★ (в избранном)");
        } else {
            holder.btnFavorite.setText("☆");
            Log.d(TAG, "Установлен значок ☆ (не в избранном)");
        }

        holder.itemView.setOnClickListener(v -> {
            Log.d(TAG, "Нажат элемент списка: " + item.getTitle());
            if (itemClickListener != null) {
                itemClickListener.onItemClick(item);
            }
        });

        holder.btnFavorite.setOnClickListener(clickView -> {
            Log.d(TAG, "=== НАЖАТА ЗВЕЗДОЧКА ===");
            Log.d(TAG, "Предмет: " + item.getTitle());
            Log.d(TAG, "ID предмета: " + item.getId());

            int uid = sessionManager.getUserId();
            Log.d(TAG, "Текущий userId из сессии: " + uid);

            if (uid == -1) {
                Log.e(TAG, "ОШИБКА: userId = -1, нельзя добавить в избранное!");
                Toast.makeText(clickView.getContext(), "Ошибка: пользователь не авторизован", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean currentlyFavorite = favoriteDb.isFavorite(uid, item.getId());
            Log.d(TAG, "Сейчас в избранном: " + currentlyFavorite);

            if (currentlyFavorite) {
                Log.d(TAG, "Удаляем из избранного...");
                boolean removed = favoriteDb.removeFavorite(uid, item.getId());
                Log.d(TAG, "Результат удаления: " + removed);
                if (removed) {
                    holder.btnFavorite.setText("☆");
                    Toast.makeText(clickView.getContext(), "Удалено из избранного", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "УСПЕШНО удалено из избранного");
                } else {
                    Log.e(TAG, "НЕ удалось удалить из избранного");
                }
            } else {
                Log.d(TAG, "Добавляем в избранное...");
                boolean added = favoriteDb.addFavorite(uid, item.getId(), item.getTitle(), item.getShortText(), item.getPrice());
                Log.d(TAG, "Результат добавления: " + added);
                if (added) {
                    holder.btnFavorite.setText("★");
                    Toast.makeText(clickView.getContext(), "Добавлено в избранное", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "УСПЕШНО добавлено в избранное");
                } else {
                    Log.e(TAG, "НЕ удалось добавить в избранное");
                }
            }

            if (favoriteClickListener != null) {
                favoriteClickListener.onFavoriteClick(item);
            }

            Log.d(TAG, "=== КОНЕЦ НАЖАТИЯ ЗВЕЗДОЧКИ ===");
        });

        Log.d(TAG, "=== КОНЕЦ onBindViewHolder ===");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateList(List<Item> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvShortText, tvPrice;
        Button btnFavorite;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvShortText = itemView.findViewById(R.id.tv_short_text);
            tvPrice = itemView.findViewById(R.id.tv_price);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
        }
    }
}