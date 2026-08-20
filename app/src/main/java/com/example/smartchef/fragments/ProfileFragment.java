package com.example.smartchef.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.smartchef.R;
import com.example.smartchef.utils.ShoppingListManager;

import java.util.Set;

public class ProfileFragment extends Fragment {

    private RelativeLayout btnShopping, btnAbout;
    private TextView tvShoppingCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnShopping = view.findViewById(R.id.btn_setting_shopping);
        btnAbout = view.findViewById(R.id.btn_setting_about);
        tvShoppingCount = view.findViewById(R.id.tv_shopping_count);

        updateShoppingCount();

        btnShopping.setOnClickListener(v -> {
            Set<String> items = ShoppingListManager.getInstance(requireContext()).getShoppingList();
            if (items.isEmpty()) {
                Toast.makeText(getContext(), "Shopping list is currently empty!", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(requireContext())
                        .setTitle("🛒 My Shopping List")
                        .setItems(items.toArray(new String[0]), null)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        btnAbout.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("About SmartChef AI")
                    .setMessage("SmartChef AI v1.0\n\n\"Cook smarter with what you already have.\"\n\nBuilt with native Android Java & Material Design 3 for premium food discovery.")
                    .setPositiveButton("Awesome!", null)
                    .show();
        });

        return view;
    }

    private void updateShoppingCount() {
        Set<String> items = ShoppingListManager.getInstance(requireContext()).getShoppingList();
        int count = items != null ? items.size() : 0;
        tvShoppingCount.setText(count + " item" + (count == 1 ? "" : "s") + " ›");
    }
}
