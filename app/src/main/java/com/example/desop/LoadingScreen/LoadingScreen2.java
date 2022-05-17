package com.example.desop.LoadingScreen;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.desop.R;

public class LoadingScreen2 extends AppCompatActivity {

    TextView goodbye;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen2);

        goodbye = findViewById(R.id.appname);
        lottie = findViewById(R.id.lottie);
        lottie.animate().translationX(2000).setDuration(2000).setStartDelay(3000);

        new Handler().postDelayed(() -> {
            finish();
            System.exit(0);
        }, 3000);

    }
}