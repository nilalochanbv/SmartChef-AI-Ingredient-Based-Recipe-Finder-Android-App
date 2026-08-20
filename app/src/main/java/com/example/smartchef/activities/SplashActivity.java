package com.example.smartchef.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartchef.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView ivPot = findViewById(R.id.iv_cooking_pot_anim);
        
        // Gentle pulse/bounce animation for cooking pot
        ivPot.animate()
                .translationY(-15f)
                .setDuration(800)
                .withEndAction(() -> ivPot.animate().translationY(0f).setDuration(800).start())
                .start();

        // 2 Second Timer then Navigate to MainActivity
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}
