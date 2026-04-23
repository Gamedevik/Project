package com.example.project.FragmentSPISOK;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project.R;
import com.example.project.Тронин13.data.FavoriteDatabaseHelper;
import com.example.project.Тронин13.models.FavoriteItem;
import com.example.project.Тронин11.utils.SessionManager;
import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements FavoritesAdapter.OnFavoriteRemoveListener {

    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private LinearLayout emptyLayout;
    private FavoriteDatabaseHelper favoriteDb;
    private SessionManager sessionManager;
    private List<FavoriteItem> favoriteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        favoriteDb = FavoriteDatabaseHelper.getInstance(getContext());
        sessionManager = SessionManager.getInstance(getContext());
        favoriteList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_view_favorites);
        emptyLayout = view.findViewById(R.id.empty_layout_favorites);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadFavorites();

        return view;
    }

    private void loadFavorites() {
        favoriteList.clear();
        int userId = sessionManager.getUserId();

        if (userId == -1) {
            Toast.makeText(getContext(), "Ошибка: пользователь не авторизован", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = favoriteDb.getFavoritesByUser(userId);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow("item_id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("item_title"));
                String shortText = cursor.getString(cursor.getColumnIndexOrThrow("item_short_text"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("item_price"));
                favoriteList.add(new FavoriteItem(id, userId, itemId, title, shortText, price));
            } while (cursor.moveToNext());
        }
        cursor.close();

        if (favoriteList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            adapter = new FavoritesAdapter(favoriteList, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onFavoriteRemove(FavoriteItem item) {
        favoriteDb.removeFavorite(item.getUserId(), item.getItemId());
        loadFavorites();
        Toast.makeText(getContext(), "Удалено из избранного", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }
}