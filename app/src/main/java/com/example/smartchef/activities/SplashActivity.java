package com.example.smartchef.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartchef.R;
import com.example.smartchef.update.UpdateInfo;
import com.example.smartchef.update.UpdateManager;

public class SplashActivity extends AppCompatActivity {

    private boolean isNavigated = false;
    private View vProgressFill;
    private FrameLayout flProgressTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Bind Views
        ImageView ivLogo = findViewById(R.id.iv_splash_logo);
        LinearLayout layoutTextGroup = findViewById(R.id.layout_text_group);

        ImageView ivTomato = findViewById(R.id.iv_ingredient_tomato);
        ImageView ivOnion = findViewById(R.id.iv_ingredient_onion);
        ImageView ivChili = findViewById(R.id.iv_ingredient_chili);
        ImageView ivLeafTop = findViewById(R.id.iv_ingredient_leaf_top);
        ImageView ivLeafBottom = findViewById(R.id.iv_ingredient_leaf_bottom);

        ImageView ivSparkle1 = findViewById(R.id.iv_sparkle_1);
        ImageView ivSparkle2 = findViewById(R.id.iv_sparkle_2);
        ImageView ivSparkle3 = findViewById(R.id.iv_sparkle_3);
        ImageView ivSparkle4 = findViewById(R.id.iv_sparkle_4);

        ImageView ivSteam = findViewById(R.id.iv_steam);
        ImageView ivBottomPot = findViewById(R.id.iv_bottom_pot);

        flProgressTrack = findViewById(R.id.fl_progress_track);
        vProgressFill = findViewById(R.id.v_progress_fill);

        // ----------------------------------------------------
        // 1. SET INITIAL STATES FOR ANIMATION
        // ----------------------------------------------------
        ivLogo.setAlpha(0f);
        ivLogo.setScaleX(0.3f);
        ivLogo.setScaleY(0.3f);

        layoutTextGroup.setAlpha(0f);
        layoutTextGroup.setTranslationY(80f);

        // Fly-in offset states for ingredients
        ivTomato.setAlpha(0f);
        ivTomato.setTranslationX(-180f);
        ivTomato.setTranslationY(-180f);

        ivOnion.setAlpha(0f);
        ivOnion.setTranslationX(180f);
        ivOnion.setTranslationY(-180f);

        ivChili.setAlpha(0f);
        ivChili.setTranslationX(-180f);
        ivChili.setTranslationY(100f);

        ivLeafTop.setAlpha(0f);
        ivLeafTop.setTranslationX(120f);
        ivLeafTop.setTranslationY(-120f);

        ivLeafBottom.setAlpha(0f);
        ivLeafBottom.setTranslationX(180f);
        ivLeafBottom.setTranslationY(120f);

        // Sparkle initial states
        ImageView[] sparkles = {ivSparkle1, ivSparkle2, ivSparkle3, ivSparkle4};
        for (ImageView s : sparkles) {
            s.setAlpha(0f);
            s.setScaleX(0.2f);
            s.setScaleY(0.2f);
        }

        // Steam initial state
        ivSteam.setAlpha(0f);
        ivSteam.setTranslationY(40f);

        // ----------------------------------------------------
        // 2. CHOREOGRAPH MULTI-STAGE ANIMATION SEQUENCE
        // ----------------------------------------------------

        // Stage 1: Logo Appears with Bounce
        ObjectAnimator logoAlpha = ObjectAnimator.ofFloat(ivLogo, View.ALPHA, 0f, 1f).setDuration(650);
        ObjectAnimator logoScaleX = ObjectAnimator.ofFloat(ivLogo, View.SCALE_X, 0.3f, 1f).setDuration(650);
        ObjectAnimator logoScaleY = ObjectAnimator.ofFloat(ivLogo, View.SCALE_Y, 0.3f, 1f).setDuration(650);
        logoScaleX.setInterpolator(new OvershootInterpolator(1.3f));
        logoScaleY.setInterpolator(new OvershootInterpolator(1.3f));

        AnimatorSet logoSet = new AnimatorSet();
        logoSet.playTogether(logoAlpha, logoScaleX, logoScaleY);

        // Stage 2: Ingredients Fly In
        AnimatorSet ingredientsSet = new AnimatorSet();
        ingredientsSet.setDuration(700);
        ingredientsSet.setInterpolator(new DecelerateInterpolator());
        ingredientsSet.setStartDelay(250);
        ingredientsSet.playTogether(
                ObjectAnimator.ofFloat(ivTomato, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(ivTomato, View.TRANSLATION_X, -180f, 0f),
                ObjectAnimator.ofFloat(ivTomato, View.TRANSLATION_Y, -180f, 0f),

                ObjectAnimator.ofFloat(ivOnion, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(ivOnion, View.TRANSLATION_X, 180f, 0f),
                ObjectAnimator.ofFloat(ivOnion, View.TRANSLATION_Y, -180f, 0f),

                ObjectAnimator.ofFloat(ivChili, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(ivChili, View.TRANSLATION_X, -180f, 0f),
                ObjectAnimator.ofFloat(ivChili, View.TRANSLATION_Y, 100f, 0f),

                ObjectAnimator.ofFloat(ivLeafTop, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(ivLeafTop, View.TRANSLATION_X, 120f, 0f),
                ObjectAnimator.ofFloat(ivLeafTop, View.TRANSLATION_Y, -120f, 0f),

                ObjectAnimator.ofFloat(ivLeafBottom, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(ivLeafBottom, View.TRANSLATION_X, 180f, 0f),
                ObjectAnimator.ofFloat(ivLeafBottom, View.TRANSLATION_Y, 120f, 0f)
        );

        // Stage 3: Sparkles Animate & Rotate
        AnimatorSet sparklesSet = new AnimatorSet();
        sparklesSet.setStartDelay(550);
        sparklesSet.setDuration(500);
        AnimatorSet.Builder sparkleBuilder = null;
        for (ImageView s : sparkles) {
            ObjectAnimator sa = ObjectAnimator.ofFloat(s, View.ALPHA, 0f, 1f);
            ObjectAnimator sx = ObjectAnimator.ofFloat(s, View.SCALE_X, 0.2f, 1f);
            ObjectAnimator sy = ObjectAnimator.ofFloat(s, View.SCALE_Y, 0.2f, 1f);
            if (sparkleBuilder == null) {
                sparkleBuilder = sparklesSet.play(sa).with(sx).with(sy);
            } else {
                sparkleBuilder.with(sa).with(sx).with(sy);
            }
        }

        // Stage 4: Steam Rises Up smoothly
        ObjectAnimator steamAlpha = ObjectAnimator.ofFloat(ivSteam, View.ALPHA, 0f, 0.9f).setDuration(800);
        ObjectAnimator steamRise = ObjectAnimator.ofFloat(ivSteam, View.TRANSLATION_Y, 40f, -25f).setDuration(1600);
        steamRise.setRepeatCount(ValueAnimator.INFINITE);
        steamRise.setRepeatMode(ValueAnimator.REVERSE);

        AnimatorSet steamSet = new AnimatorSet();
        steamSet.setStartDelay(700);
        steamSet.playTogether(steamAlpha, steamRise);

        // Stage 5: App Name & Tagline Fade In Upward
        ObjectAnimator textAlpha = ObjectAnimator.ofFloat(layoutTextGroup, View.ALPHA, 0f, 1f).setDuration(600);
        ObjectAnimator textTranslate = ObjectAnimator.ofFloat(layoutTextGroup, View.TRANSLATION_Y, 80f, 0f).setDuration(600);
        textTranslate.setInterpolator(new DecelerateInterpolator());

        AnimatorSet textSet = new AnimatorSet();
        textSet.setStartDelay(850);
        textSet.playTogether(textAlpha, textTranslate);

        // Start all animation sets
        logoSet.start();
        ingredientsSet.start();
        sparklesSet.start();
        steamSet.start();
        textSet.start();

        // Continuous subtle rotation for sparkles
        Animation rotateAnim = AnimationUtils.loadAnimation(this, R.anim.anim_sparkle_rotate);
        ivSparkle1.startAnimation(rotateAnim);
        ivSparkle3.startAnimation(rotateAnim);

        // Gentle pulse for bottom pot
        ivBottomPot.animate()
                .scaleX(1.05f)
                .scaleY(1.05f)
                .setDuration(900)
                .setStartDelay(600)
                .withEndAction(() -> ivBottomPot.animate().scaleX(1.0f).scaleY(1.0f).setDuration(900).start())
                .start();

        // ----------------------------------------------------
        // 3. ANIMATED PROGRESS BAR (0% -> 100%)
        // ----------------------------------------------------
        flProgressTrack.post(() -> {
            int trackWidth = flProgressTrack.getWidth();
            if (trackWidth > 0) {
                ValueAnimator progressAnim = ValueAnimator.ofInt(0, trackWidth);
                progressAnim.setDuration(2600);
                progressAnim.setInterpolator(new DecelerateInterpolator());
                progressAnim.addUpdateListener(animator -> {
                    int val = (Integer) animator.getAnimatedValue();
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) vProgressFill.getLayoutParams();
                    params.width = val;
                    vProgressFill.setLayoutParams(params);
                });
                progressAnim.start();
            }
        });

        // ----------------------------------------------------
        // 4. NAVIGATION HANDLER AFTER ANIMATION COMPLETION
        // ----------------------------------------------------
        new Handler(Looper.getMainLooper()).postDelayed(this::checkAppUpdate, 2800);
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
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
