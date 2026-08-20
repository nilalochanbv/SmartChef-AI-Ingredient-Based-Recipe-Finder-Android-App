package com.example.smartchef.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartchef.R;
import com.example.smartchef.models.InstructionStep;
import com.example.smartchef.models.Recipe;
import com.example.smartchef.utils.Constants;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Locale;

public class CookingModeActivity extends AppCompatActivity {

    private TextView btnClose, tvStepCounter, tvInstruction, tvTimerCountdown, btnTimerToggle;
    private MaterialButton btnPrev, btnNext;

    private Recipe recipe;
    private List<InstructionStep> steps;
    private int currentStepIndex = 0;

    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 300000; // Default 5 mins

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_mode);

        btnClose = findViewById(R.id.btn_close_cooking);
        tvStepCounter = findViewById(R.id.tv_cooking_step_counter);
        tvInstruction = findViewById(R.id.tv_cooking_instruction);
        tvTimerCountdown = findViewById(R.id.tv_timer_countdown);
        btnTimerToggle = findViewById(R.id.btn_timer_toggle);
        btnPrev = findViewById(R.id.btn_cooking_prev);
        btnNext = findViewById(R.id.btn_cooking_next);

        recipe = (Recipe) getIntent().getSerializableExtra(Constants.EXTRA_RECIPE);
        if (recipe != null && recipe.getInstructionSteps() != null && !recipe.getInstructionSteps().isEmpty()) {
            steps = recipe.getInstructionSteps();
        } else {
            finish();
            return;
        }

        btnClose.setOnClickListener(v -> finish());

        btnPrev.setOnClickListener(v -> {
            if (currentStepIndex > 0) {
                currentStepIndex--;
                displayStep(currentStepIndex, false);
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentStepIndex < steps.size() - 1) {
                currentStepIndex++;
                displayStep(currentStepIndex, true);
            } else {
                Toast.makeText(this, "Congratulations! You completed cooking " + recipe.getTitle() + "! 🎉", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnTimerToggle.setOnClickListener(v -> {
            btnTimerToggle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));
            if (isTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        displayStep(currentStepIndex, true);
    }

    private void displayStep(int index, boolean isNext) {
        if (steps == null || index < 0 || index >= steps.size()) return;

        InstructionStep step = steps.get(index);
        tvStepCounter.setText("STEP " + (index + 1) + " / " + steps.size());
        
        // Slide & Fade Animation for instruction text
        tvInstruction.startAnimation(AnimationUtils.loadAnimation(this, isNext ? R.anim.slide_in_right : R.anim.fade_in));
        tvInstruction.setText(step.getText());

        btnPrev.setEnabled(index > 0);
        btnNext.setText(index == steps.size() - 1 ? R.string.btn_finish_cooking : R.string.btn_next_step);

        // Reset Timer for Step
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isTimerRunning = false;
        timeLeftInMillis = (step.getTimerSeconds() > 0 ? step.getTimerSeconds() : 300) * 1000L;
        updateTimerText();
        btnTimerToggle.setText("Tap to Start ▶");
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                btnTimerToggle.setText("Time Up! 🔔");
                Toast.makeText(CookingModeActivity.this, "Step timer finished!", Toast.LENGTH_SHORT).show();
            }
        }.start();

        isTimerRunning = true;
        btnTimerToggle.setText("Pause ❚❚");
    }

    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isTimerRunning = false;
        btnTimerToggle.setText("Resume ▶");
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tvTimerCountdown.setText(timeFormatted);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
