package ru.myitlesson.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import ru.myitlesson.api.MyItLessonClient;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.api.entity.UserEntity;
import ru.myitlesson.app.AppUtils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.activity.EditorActivity;
import ru.myitlesson.app.activity.MainActivity;
import ru.myitlesson.app.adapter.ListAdapter;
import ru.myitlesson.app.api.ApiExecutor;
import ru.myitlesson.app.api.Client;
import ru.myitlesson.app.binder.OwnCourseViewBinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private MainActivity mainActivity;

    private TextView usernameTextView;

    private TextView roleTextView;

    private RecyclerView coursesRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.profile_fragment, container, false);

        mainActivity = (MainActivity) requireActivity();

        coursesRecyclerView = layout.findViewById(R.id.courses_recycler_view);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        coursesRecyclerView.setLayoutManager(horizontalLayoutManager);

        usernameTextView = layout.findViewById(R.id.username_text_view);
        roleTextView = layout.findViewById(R.id.role_text_view);

        final Button settingsButton = layout.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(view -> AppUtils.setFragmentInContainer(R.id.fragment_container_view, SettingsFragment.class, getParentFragmentManager()));

        final Button notificationsButton = layout.findViewById(R.id.notifications_button);
        notificationsButton.setOnClickListener(view -> AppUtils.setFragmentInContainer(R.id.fragment_container_view, NotificationsFragment.class, getParentFragmentManager()));

        final FloatingActionButton addButton = layout.findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> AppUtils.startActivity(requireContext(), EditorActivity.class));

        new ApiExecutor(this::loadData, exception -> AppUtils.handleException(exception, getContext())).start();

        return layout;
    }

    private void loadData() throws IOException {
        MyItLessonClient api = Client.getInstance().api();

        UserEntity user = api.user().get(api.getUserId());

        List<CourseEntity> ownedCourses = new ArrayList<>();

        for(final int courseId : user.getAuthoredCourses()) {
            CourseEntity course = api.course().get(courseId);
            ownedCourses.add(course);
        }

        mainActivity.runOnUiThread(() -> usernameTextView.setText(user.getUsername()));
        mainActivity.runOnUiThread(() -> roleTextView.setText(user.getRole().toString()));
        mainActivity.runOnUiThread(() -> coursesRecyclerView.setAdapter(new ListAdapter<>(ownedCourses, OwnCourseViewBinder.class)));
    }
}
