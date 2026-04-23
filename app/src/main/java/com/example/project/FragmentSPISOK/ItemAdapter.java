package com.example.project.FragmentSPISOK;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project.R;
import com.example.project.Лапин12.DATABASE.Item;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
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
        holder.tvTitle.setText(item.getTitle());
        holder.tvShortText.setText(item.getShortText());
        holder.tvPrice.setText(item.getPrice() + " ₽");
        holder.btnFavorite.setText(item.isFavorite() ? "★" : "☆");

        holder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(item));
        holder.btnFavorite.setOnClickListener(v -> favoriteClickListener.onFavoriteClick(item));
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