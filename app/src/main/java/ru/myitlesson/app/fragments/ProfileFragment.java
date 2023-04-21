package ru.myitlesson.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.myitlesson.app.R;
import ru.myitlesson.app.activities.MainActivity;
import ru.myitlesson.app.adapter.CourseAdapter;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.profile_fragment, container, false);

        MainActivity mainActivity = (MainActivity) requireActivity();

        CourseAdapter courseAdapter = new CourseAdapter();

        // TODO: make custom adapter of user's courses
        final RecyclerView coursesRecyclerView = layout.findViewById(R.id.courses_recycler_view);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        coursesRecyclerView.setAdapter(courseAdapter);
        coursesRecyclerView.setLayoutManager(horizontalLayoutManager);

        final Button settingsButton = layout.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(view -> mainActivity.setFragmentInContainer(R.id.fragment_container_view, SettingsFragment.class));

        final Button notificationsButton = layout.findViewById(R.id.notifications_button);
        notificationsButton.setOnClickListener(view -> mainActivity.setFragmentInContainer(R.id.fragment_container_view, NotificationsFragment.class));

        return layout;
    }
}
