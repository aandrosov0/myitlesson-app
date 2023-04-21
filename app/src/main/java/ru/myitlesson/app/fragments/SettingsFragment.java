package ru.myitlesson.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;

import ru.myitlesson.app.R;
import ru.myitlesson.app.activities.MainActivity;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.app_main_preferences, rootKey);

        // TODO: Bind preferences
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.preferences_fragment, container, false);
        ViewGroup settingsContainerView = layout.findViewById(R.id.settings_container_view);

        MainActivity mainActivity = (MainActivity) requireActivity();

        Toolbar toolbar = layout.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.settings);

        toolbar.setNavigationIcon(R.drawable.arrow_back);

        mainActivity.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> mainActivity.setFragmentInContainer(R.id.fragment_container_view, ProfileFragment.class));

        View settingsView = super.onCreateView(inflater, settingsContainerView, savedInstanceState);
        settingsContainerView.addView(settingsView);
        return layout;
    }
}
