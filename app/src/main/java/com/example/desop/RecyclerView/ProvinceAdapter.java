package com.example.desop.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desop.Province;
import com.example.desop.R;

import java.util.ArrayList;
import java.util.List;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder> {

    Context context;
    List<Province> list;

    public ProvinceAdapter(Context context, List<Province> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ProvinceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_province, parent, false);
        return new ProvinceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinceViewHolder holder, int position) {

        Province province = list.get(position);// lay vi tri
        if (province == null)
            return;
        holder.province.setText(province.getProvince());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activityListLocation.class);
                intent.putExtra("clickProvince", province.getProvince());
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

    public void filterList(ArrayList<Province> filterList) {
        list = filterList;
        notifyDataSetChanged();
    }

    public class ProvinceViewHolder extends RecyclerView.ViewHolder {

        TextView province;
        LinearLayout linearLayout;

        public ProvinceViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.layout_province);
            province = itemView.findViewById(R.id.tv_province);
        }
    }
}
