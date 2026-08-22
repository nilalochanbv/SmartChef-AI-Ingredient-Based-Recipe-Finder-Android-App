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
import com.example.smartchef.update.UpdateInfo;
import com.example.smartchef.update.UpdateManager;
import com.example.smartchef.utils.ShoppingListManager;

import java.util.Set;

public class ProfileFragment extends Fragment {

    private RelativeLayout btnShopping, btnUpdate, btnAbout;
    private TextView tvShoppingCount, tvUpdateVersion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnShopping = view.findViewById(R.id.btn_setting_shopping);
        btnUpdate = view.findViewById(R.id.btn_setting_update);
        btnAbout = view.findViewById(R.id.btn_setting_about);
        tvShoppingCount = view.findViewById(R.id.tv_shopping_count);
        tvUpdateVersion = view.findViewById(R.id.tv_update_version);

        if (getContext() != null) {
            String currentVersion = UpdateManager.getInstance().getInstalledVersionName(requireContext());
            tvUpdateVersion.setText("v" + currentVersion + " ›");
        }

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

        btnUpdate.setOnClickListener(v -> checkAppUpdateManually());

        btnAbout.setOnClickListener(v -> {
            String version = getContext() != null ? UpdateManager.getInstance().getInstalledVersionName(requireContext()) : "1.0.0";
            new AlertDialog.Builder(requireContext())
                    .setTitle("About SmartChef AI")
                    .setMessage("SmartChef AI v" + version + "\n\n\"Cook smarter with what you already have.\"\n\nBuilt with native Android Java & Material Design 3 for premium food discovery.")
                    .setPositiveButton("Awesome!", null)
                    .show();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            UpdateManager.getInstance().resumePendingUpdateIfAny(getActivity());
        }
    }

    private void checkAppUpdateManually() {
        if (getContext() == null || getActivity() == null) return;

        Toast.makeText(getContext(), "Checking for updates...", Toast.LENGTH_SHORT).show();

        UpdateManager.getInstance().checkForUpdate(requireContext(), new UpdateManager.OnUpdateCheckListener() {
            @Override
            public void onUpdateAvailable(UpdateInfo updateInfo) {
                if (getActivity() == null || getActivity().isFinishing()) return;
                UpdateManager.getInstance().showUpdateDialog(getActivity(), updateInfo, null);
            }

            @Override
            public void onNoUpdateAvailable() {
                if (getContext() == null) return;
                Toast.makeText(getContext(), "You are already using the latest version of SmartChef AI!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateShoppingCount() {
        Set<String> items = ShoppingListManager.getInstance(requireContext()).getShoppingList();
        int count = items != null ? items.size() : 0;
        tvShoppingCount.setText(count + " item" + (count == 1 ? "" : "s") + " ›");
    }
}
