package com.example.desop.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desop.MainScreen.MainScreen;
import com.example.desop.Map.MapActivity;
import com.example.desop.Province;
import com.example.desop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListProvince extends AppCompatActivity {

    TextView tv;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ProvinceAdapter provinAdapter;
    private List<Province> list;
    private EditText editText;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_province);


        recyclerView = findViewById(R.id.rcv_province);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("province");

        list = new ArrayList<>();
        provinAdapter = new ProvinceAdapter(this, list);
        recyclerView.setAdapter(provinAdapter);

        editText = findViewById(R.id.edt_searchProvince);
        img_back = findViewById(R.id.img_back);

        getProvince();


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


    }

    private void getProvince() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    Province province = new Province(child.getKey());
                    list.add(province);
                }
                provinAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filter(String keysearch) {
        ArrayList<Province> filterlist = new ArrayList<>();
        for (Province item : list) {
            if (item.getProvince().toLowerCase().contains(keysearch.toLowerCase())) {
                filterlist.add(item);
            }
        }
        provinAdapter.filterList(filterlist);
    }
}