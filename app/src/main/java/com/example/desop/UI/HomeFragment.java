package com.example.desop.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.desop.R;
import com.example.desop.RecyclerView.ListProvince;

public class HomeFragment extends Fragment {
    private TextView txtView_Explore;
    private CardView cardView_news, cardView_transport, cardView_tour, cardView_exchange, cardView_communicate, cardView_hotline, cardView_handbook;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initUI(view);
        txtView_Explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListProvince.class));
            }
        });
        cardView_transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://vietnam.travel/plan-your-trip/transport-within-vietnam");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        cardView_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://vnexpress.net/du-lich");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        cardView_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://travel.com.vn/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        cardView_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://portal.vietcombank.com.vn/Personal/TG/Pages/ty-gia.aspx?devicechannel=default");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        cardView_handbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://travel.com.vn/du-lich-bang-hinh-anh.aspx");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        cardView_hotline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cardView_communicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://translate.google.com/?hl=vi&sl=auto&tl=en&op=translate");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        return view;
    }


    private void initUI(View view) {
        txtView_Explore = view.findViewById(R.id.txtView_Explore);
        cardView_transport = view.findViewById(R.id.cardView_transport);
        cardView_news = view.findViewById(R.id.cardView_news);
        cardView_tour = view.findViewById(R.id.cardView_tour);
        cardView_exchange = view.findViewById(R.id.cardView_exchange);
        cardView_communicate = view.findViewById(R.id.cardView_communicate);
        cardView_hotline = view.findViewById(R.id.cardView_hotline);
        cardView_handbook = view.findViewById(R.id.cardView_handbook);
    }
}