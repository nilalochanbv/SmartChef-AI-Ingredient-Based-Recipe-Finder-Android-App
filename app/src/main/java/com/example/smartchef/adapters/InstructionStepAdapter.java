package com.example.smartchef.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartchef.R;
import com.example.smartchef.models.InstructionStep;

import java.util.List;

public class InstructionStepAdapter extends RecyclerView.Adapter<InstructionStepAdapter.ViewHolder> {

    private final Context context;
    private final List<InstructionStep> steps;

    public InstructionStepAdapter(Context context, List<InstructionStep> steps) {
        this.context = context;
        this.steps = steps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_instruction_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InstructionStep step = steps.get(position);
        
        String stepNumStr = String.format("%02d", step.getStepNumber());
        holder.tvStepNumber.setText(stepNumStr);
        holder.tvInstructionText.setText(step.getText());

        if (step.getTimerSeconds() > 0) {
            holder.layoutTimer.setVisibility(View.VISIBLE);
            int mins = step.getTimerSeconds() / 60;
            holder.tvStepTime.setText(mins > 0 ? mins + " mins" : step.getTimerSeconds() + " secs");
        } else {
            holder.layoutTimer.setVisibility(View.GONE);
        }

        // Hide bottom connector on the last step
        if (position == getItemCount() - 1) {
            holder.viewConnector.setVisibility(View.INVISIBLE);
        } else {
            holder.viewConnector.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return steps != null ? steps.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStepNumber, tvInstructionText, tvStepTime;
        View viewConnector, layoutTimer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStepNumber = itemView.findViewById(R.id.tv_step_number);
            tvInstructionText = itemView.findViewById(R.id.tv_instruction_text);
            tvStepTime = itemView.findViewById(R.id.tv_step_time);
            viewConnector = itemView.findViewById(R.id.view_timeline_connector);
            layoutTimer = itemView.findViewById(R.id.layout_step_timer);
        }
    }
}
