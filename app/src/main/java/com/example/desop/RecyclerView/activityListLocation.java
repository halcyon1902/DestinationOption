package com.example.desop.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desop.DiaDiem;
import com.example.desop.MainScreen.MainScreen;
import com.example.desop.Province;
import com.example.desop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class activityListLocation extends AppCompatActivity {
    TextView tv;
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;
    private List<DiaDiem> list;
    private EditText editText;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_location);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.Rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        databaseReference = database.getReference("province").child(intent.getStringExtra("clickProvince"));
        locationAdapter = new LocationAdapter(this, list);
        recyclerView.setAdapter(locationAdapter);
        editText = findViewById(R.id.et_search);
        img_back = findViewById(R.id.img_back);

        getList();


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

    private void filter(String keysearch) {
        ArrayList<DiaDiem> filterlist = new ArrayList<>();
        for (DiaDiem item : list) {
            if (item.getTenDiaDiem().toLowerCase().contains(keysearch.toLowerCase())) {
                filterlist.add(item);
            }
        }
        locationAdapter.filterList(filterlist);
    }

    public void getList() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    DiaDiem diaDiem = child.getValue(DiaDiem.class);
                    list.add(diaDiem);
                }
                locationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activityListLocation.this, "Khong the lay du lieu!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}