package com.example.desop.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.desop.MainScreen.MainScreen;
import com.example.desop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activitySignIn extends AppCompatActivity {

    private Button btn_sign_in;
    private LinearLayout layoutSignUp;
    private TextInputEditText edt_Email, edt_MatKhau;
    private ProgressDialog progressDialog;
    private TextView textView;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initUi();

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignIn();
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){

                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(activitySignIn.this,"Checked",Toast.LENGTH_SHORT).show();

                }else if(!compoundButton.isChecked()){

                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(activitySignIn.this,"Unchecked",Toast.LENGTH_SHORT).show();

                }
            }
        });
        layoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activitySignIn.this, activitySignUp.class));
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activitySignIn.this, activityFogotPassword.class));
            }
        });
    }

    private void onClickSignIn() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String strEmail = edt_Email.getText().toString().trim();
        String strMatKhau = edt_MatKhau.getText().toString().trim();
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
        if (!Check(strMatKhau)) {
            edt_MatKhau.setError("Mật khẩu không được để trống");
            edt_MatKhau.requestFocus();
            return;
        }
        progressDialog.show();
        auth.signInWithEmailAndPassword(strEmail, strMatKhau)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            startActivity(new Intent(activitySignIn.this, MainScreen.class));
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(activitySignIn.this, "Sai tài khoản hoặc mật khẩu!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initUi() {
        progressDialog = new ProgressDialog(this);
        btn_sign_in = findViewById(R.id.btn_DangNhap);
        layoutSignUp = findViewById(R.id.layout_sign_up);
        textView = findViewById(R.id.tv_QuenMatKhau);
        edt_MatKhau = (TextInputEditText) findViewById(R.id.edt_MatKhau);
        edt_Email = (TextInputEditText) findViewById(R.id.edt_TenTaiKhoan);
        checkBox = findViewById(R.id.chbRememberMe);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");

        if(checkbox.equals("true")){
            startActivity(new Intent(activitySignIn.this, MainScreen.class));
            finish();
        }
        else if(checkbox.equals("false")){

        }
    }

    private boolean Check(String name) {
        return !TextUtils.isEmpty(name);
    }
}