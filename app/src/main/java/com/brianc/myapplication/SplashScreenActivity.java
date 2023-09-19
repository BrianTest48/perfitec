package com.brianc.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class SplashScreenActivity extends AppCompatActivity {

    LinearLayout ll_contenedor_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ll_contenedor_logo = findViewById(R.id.ll_contenedor_logo);

        Animation mianimacion = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        ll_contenedor_logo.startAnimation(mianimacion);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}