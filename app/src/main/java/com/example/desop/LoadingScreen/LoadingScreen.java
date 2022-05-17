package com.example.desop.LoadingScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.desop.Login.activitySignIn;
import com.example.desop.R;

public class LoadingScreen extends AppCompatActivity {
    TextView appname;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        appname = findViewById(R.id.appname);
        lottie = findViewById(R.id.lottie);
        appname.animate().translationY(-1500).setDuration(2700).setStartDelay(0);
        lottie.animate().translationX(2000).setDuration(2000).setStartDelay(5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), activitySignIn.class));
                finish();
            }
        }, 8000);
    }
}