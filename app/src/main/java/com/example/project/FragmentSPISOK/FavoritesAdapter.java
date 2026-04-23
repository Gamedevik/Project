package com.example.project.FragmentSPISOK;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project.R;
import com.example.project.Тронин13.models.FavoriteItem;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<FavoriteItem> favorites;
    private OnFavoriteRemoveListener removeListener;

    public interface OnFavoriteRemoveListener {
        void onFavoriteRemove(FavoriteItem item);
    }

    public FavoritesAdapter(List<FavoriteItem> favorites, OnFavoriteRemoveListener removeListener) {
        this.favorites = favorites;
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteItem item = favorites.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvShortText.setText(item.getShortText());
        holder.tvPrice.setText(item.getPrice() + " ₽");

        holder.btnRemove.setOnClickListener(v -> removeListener.onFavoriteRemove(item));
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public void updateList(List<FavoriteItem> newFavorites) {
        this.favorites = newFavorites;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvShortText, tvPrice;
        Button btnRemove;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_fav_title);
            tvShortText = itemView.findViewById(R.id.tv_fav_short_text);
            tvPrice = itemView.findViewById(R.id.tv_fav_price);
            btnRemove = itemView.findViewById(R.id.btn_remove_favorite);
        }
    }
}