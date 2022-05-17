package com.example.desop.Login;

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

import com.example.desop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class activityFogotPassword extends AppCompatActivity {

    private EditText edt_email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogot_password);

        edt_email = findViewById(R.id.edt_QuenMatKhau);
        Button button = findViewById(R.id.btn_XacNhan);
        auth = FirebaseAuth.getInstance();
        Button btn_back = findViewById(R.id.btn_Back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPassword();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), activitySignIn.class));
                finish();
            }
        });
    }

    private boolean Check(String name) {
        return !TextUtils.isEmpty(name);
    }

    private void ForgotPassword() {
        String strEmail = edt_email.getText().toString().trim();
        if (!Check(strEmail)) {
            edt_email.setError("Email không được để trống");
            edt_email.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edt_email.getText().toString().trim()).matches()) {
            edt_email.setError("Email không đúng định dạng");
            edt_email.requestFocus();
        } else {
            auth.sendPasswordResetEmail(strEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(activityFogotPassword.this, activitySignIn.class));
                        ShowToast("Hãy kiểm tra email!");
                        finish();
                    } else {
                        ShowToast("Email không tồn tại!");
                    }
                }
            });
        }
    }

    private void ShowToast(String messenger) {
        Toast.makeText(this, messenger, Toast.LENGTH_SHORT).show();
    }
}