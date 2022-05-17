package com.example.desop.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.desop.MainScreen.MainScreen;
import com.example.desop.R;
import com.example.desop.RecyclerView.activityListLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class activitySignUp extends AppCompatActivity {

    Button btn_back, btn_sign_up;
    EditText edt_HoTen, edt_Email, edt_Sdt, edt_MatKhau, edt_XacNhanMK;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activitySignUp.this, activitySignIn.class));
            }
        });
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUP();
            }
        });
    }

    private void initUI() {
        btn_back = findViewById(R.id.btn_TroVe);
        btn_sign_up = findViewById(R.id.btn_Tao);
        edt_Email = findViewById(R.id.edt_Email);
        edt_Sdt = findViewById(R.id.edt_Sdt);
        edt_HoTen = findViewById(R.id.edt_HoTen);
        edt_MatKhau = findViewById(R.id.edt_MatKhau);
        edt_XacNhanMK = findViewById(R.id.edt_XacNhanMatKhau);
        progressDialog = new ProgressDialog(this);
    }

    private boolean Check(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        return true;
    }

    public void onClickSignUP() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getUid();
        String strEmail = edt_Email.getText().toString().trim();
        String strMatKhau = edt_MatKhau.getText().toString().trim();
        String strHoTen = edt_HoTen.getText().toString().trim();
        String strSdt = edt_Sdt.getText().toString().trim();
        Intent intent = new Intent(activitySignUp.this, activityListLocation.class);
        if (!Patterns.EMAIL_ADDRESS.matcher(edt_Email.getText().toString().trim()).matches()) {
            edt_Email.setError("Email không đúng định dạng");
            edt_Email.requestFocus();
            return;
        }
        if (!Check(strEmail)) {
            edt_Email.setError("Email không được để trống");
            edt_Email.requestFocus();
            return;
        }
        if (!Check(strHoTen)) {
            edt_HoTen.setError("Họ tên không được để trống");
            edt_HoTen.requestFocus();
            return;
        }

        if (strMatKhau.length() < 6) {
            edt_MatKhau.setError("Mật khẩu không được ít hơn 6 kí tự !!!");
            edt_MatKhau.requestFocus();
            return;
        }
        if (!Check(strMatKhau)) {
            edt_MatKhau.setError("Mật khẩu không được để trống");
            edt_MatKhau.requestFocus();
            return;
        }
        if (!edt_XacNhanMK.getText().toString().trim().equals(strMatKhau)) {
            edt_XacNhanMK.setError("Mật khẩu không trùng khớp");
            edt_XacNhanMK.requestFocus();
            return;
        }

        if(strSdt.length()!=10){
            edt_Sdt.setError("Số điện thoại không đúng định dạng!");
            edt_Sdt.requestFocus();
            return;
        }

        progressDialog.show();
        auth.createUserWithEmailAndPassword(strEmail, strMatKhau)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            databaseReference = FirebaseDatabase.getInstance().getReference("user");
                            Map<String, Object> user = new HashMap<>();
                            user.put("email", strEmail);
                            user.put("name", strHoTen);
                            user.put("phone", strSdt);
                            databaseReference.child(auth.getCurrentUser().getUid()).setValue(user);
                            Toast.makeText(activitySignUp.this, "Tạo tài khoản thành công.",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activitySignUp.this, activitySignIn.class));
                            finishAffinity();
                        } else {
                            Toast.makeText(activitySignUp.this, "Tạo tài khoản thất bại.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}