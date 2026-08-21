package com.example.smartchef.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartchef.R;
import com.example.smartchef.update.UpdateInfo;
import com.example.smartchef.update.UpdateManager;

public class SplashActivity extends AppCompatActivity {

    private boolean isNavigated = false;

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

        // 1.5 Second Splash delay then check for online updates
        new Handler(Looper.getMainLooper()).postDelayed(this::checkAppUpdate, 1500);
    }

    private void checkAppUpdate() {
        UpdateManager.getInstance().checkForUpdate(this, new UpdateManager.OnUpdateCheckListener() {
            @Override
            public void onUpdateAvailable(UpdateInfo updateInfo) {
                if (isFinishing() || isDestroyed()) return;
                UpdateManager.getInstance().showUpdateDialog(SplashActivity.this, updateInfo, () -> {
                    proceedToMain();
                });
            }

            @Override
            public void onNoUpdateAvailable() {
                proceedToMain();
            }
        });
    }

    private synchronized void proceedToMain() {
        if (isNavigated || isFinishing()) return;
        isNavigated = true;
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
