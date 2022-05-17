package com.example.desop.RecyclerView;

import android.content.Context;
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
import com.example.desop.R;
import com.example.desop.activityDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    List<DiaDiem> list;
    Context context;

    public LocationAdapter(Context context, List<DiaDiem> list) {
        this.list = list;
        this.context = context;
    }

    @Override

    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public void filterList(ArrayList<DiaDiem> filterList) {
        list = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listlocation, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiaDiem diaDiem = list.get(position);// lay vi tri
        if (diaDiem == null)
            return;
        holder.tenDiadDiem.setText(diaDiem.getTenDiaDiem());
        Picasso.get().load(diaDiem.getUrl()).into(holder.imageView);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activityDetail.class);
                intent.putExtra("clickLocation", diaDiem);
                context.startActivity(intent);

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView tenDiadDiem;
        ImageView imageView;
        String url;

        public ViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.ln_linear);
            tenDiadDiem = view.findViewById(R.id.tv_TenDiaDiem);
            imageView = view.findViewById(R.id.image);
        }
    }
}
