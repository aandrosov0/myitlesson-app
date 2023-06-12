package ru.myitlesson.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;

import ru.myitlesson.app.R;
import ru.myitlesson.app.activity.MainActivity;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_app_main, rootKey);

        // TODO: Bind preferences
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_preferences, container, false);
        ViewGroup settingsContainerView = layout.findViewById(R.id.settings_container_view);

        MainActivity mainActivity = (MainActivity) requireActivity();

        Toolbar toolbar = layout.findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(view -> mainActivity.setFragmentByNavigationItem(R.id.profile_page));

        View settingsView = super.onCreateView(inflater, settingsContainerView, savedInstanceState);
        settingsContainerView.addView(settingsView);
        return layout;
    }
}
