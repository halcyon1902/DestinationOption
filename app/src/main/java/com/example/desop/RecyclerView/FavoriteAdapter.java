package com.example.desop.RecyclerView;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desop.DiaDiem;
import com.example.desop.HobbyFragment;
import com.example.desop.R;
import com.example.desop.UI.HomeFragment;
import com.example.desop.activityDetail;
import com.google.firebase.database.core.Context;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter {

    HobbyFragment context;
    List<DiaDiem> list;

    public FavoriteAdapter(HobbyFragment context, List<DiaDiem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listlocation, parent, false);
        return  new ViewHolderFavorite(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DiaDiem diaDiem = list.get(position);// lay vi tri
        if (diaDiem == null)
            return;
        ViewHolderFavorite viewHolderFavorite = (ViewHolderFavorite) holder;
        viewHolderFavorite.tenDiadDiem.setText(diaDiem.getTenDiaDiem());
        Picasso.get().load(diaDiem.getUrl()).into(viewHolderFavorite.imageView);
        viewHolderFavorite.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), activityDetail.class);
                intent.putExtra("clickLocation", diaDiem);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public class ViewHolderFavorite extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView tenDiadDiem;
        ImageView imageView;

        public ViewHolderFavorite(@NonNull View itemView) {
            super(itemView);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.ln_linear);
            tenDiadDiem = (TextView) itemView.findViewById(R.id.tv_TenDiaDiem);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
