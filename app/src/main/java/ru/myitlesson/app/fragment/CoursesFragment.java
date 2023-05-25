package ru.myitlesson.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.app.InterfaceUtils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.adapter.ListAdapter;
import ru.myitlesson.app.api.ApiExecutor;
import ru.myitlesson.app.api.Client;
import ru.myitlesson.app.binder.CourseViewBinder;

import java.io.IOException;
import java.util.List;

public class CoursesFragment extends Fragment {

    public static final int MAX_COURSES = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.courses_fragment, container, false);

        new ApiExecutor(this::loadData, exception -> InterfaceUtils.handleException(exception, getContext())).start();

        return layout;
    }

    private void loadData() throws IOException {
        Activity activity = requireActivity();
        List<CourseEntity> courses = Client.getInstance().api().course().getAll(MAX_COURSES, 0);

        RecyclerView coursesRecyclerView = activity.findViewById(R.id.courses_recycler_view);
        activity.runOnUiThread(() -> coursesRecyclerView.setAdapter(new ListAdapter<>(courses, CourseViewBinder.class)));
    }
}
