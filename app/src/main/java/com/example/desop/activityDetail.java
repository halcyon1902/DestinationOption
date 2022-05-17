package com.example.desop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desop.Login.activitySignIn;
import com.example.desop.Map.MapActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class activityDetail extends AppCompatActivity {

    private TextView tv_email, tv_sdt;
    private TextInputEditText tiet_ten_dia_diem, tiet_dia_chi, tiet_mo_ta;
    private DiaDiem diaDiem;
    private FirebaseDatabase database;
    private ImageView imageView, img_back;
    private Button btn_map, btn_favorite;

    private Boolean isInMyFavorite = false;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initUI();
        displayDetail();
        checkFavorite();

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activityDetail.this, MapActivity.class);
                intent.putExtra("clickMap", diaDiem);
                startActivity(intent);
            }
        });
        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInMyFavorite) {
                    removeFavorite();
                } else {
                    addFavorite();
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

    }

    private void checkFavorite() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        String tenDiaDiem = tiet_ten_dia_diem.getText().toString();
        reference.child(auth.getUid()).child("Favorites").child(tenDiaDiem).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                database = FirebaseDatabase.getInstance();
                Intent intent = getIntent();
                diaDiem = (DiaDiem) intent.getSerializableExtra("clickLocation");

                isInMyFavorite = snapshot.exists(); //true if exists, false nguoc lai
                if (isInMyFavorite) {
                    //exists favorite
                    btn_favorite.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_baseline_favorite, 0, 0);
                } else {
                    btn_favorite.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_baseline_favorite_border_white, 0, 0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addFavorite() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String moTa = tiet_mo_ta.getText().toString();
        String tenDiaDiem = tiet_ten_dia_diem.getText().toString();
        String diaChi = tiet_dia_chi.getText().toString();
        String url = diaDiem.getUrl();

        DiaDiem diaDiem1 = new DiaDiem(moTa, tenDiaDiem, diaChi, url);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        reference.child(auth.getUid()).child("Favorites").child(tenDiaDiem)
                .setValue(diaDiem1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showToast("Đã thêm vào danh sách yêu thích!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Thêm vào danh sách yêu thích thất bại !");
                    }
                });
    }

    private void removeFavorite() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String moTa = tiet_mo_ta.getText().toString();
        String tenDiaDiem = tiet_ten_dia_diem.getText().toString();
        String diaChi = tiet_dia_chi.getText().toString();
        String url = diaDiem.getUrl();

        DiaDiem diaDiem1 = new DiaDiem(moTa, tenDiaDiem, diaChi, url);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        reference.child(auth.getUid()).child("Favorites").child(tenDiaDiem)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showToast("Đã xóa khỏi danh sách yêu thích!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Xóa khỏi danh sách yêu thích thất bại !");
                    }
                });

    }

    private void displayDetail() {
        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        diaDiem = (DiaDiem) intent.getSerializableExtra("clickLocation");
        Picasso.get().load(diaDiem.getUrl()).into(imageView);
        tiet_ten_dia_diem.setText(diaDiem.getTenDiaDiem());
        tiet_dia_chi.setText(diaDiem.getDiaChi());
        tiet_mo_ta.setText(diaDiem.getMoTa());
    }

    private void initUI() {
        tv_email = findViewById(R.id.tv_TenDiaDiem);
        tiet_ten_dia_diem = findViewById(R.id.tiet_ten_dia_diem);
        tiet_dia_chi = findViewById(R.id.tiet_dia_chi);
        tiet_mo_ta = findViewById(R.id.tiet_mo_ta);
        imageView = findViewById(R.id.img_hinhanh);
        btn_map = findViewById(R.id.btn_map);
        btn_favorite = findViewById(R.id.btn_favorite);
        img_back = findViewById(R.id.img_back_detail);

    }

    private void showToast(String mess) {
        Toast.makeText(this, mess, Toast.LENGTH_LONG).show();
    }

}

