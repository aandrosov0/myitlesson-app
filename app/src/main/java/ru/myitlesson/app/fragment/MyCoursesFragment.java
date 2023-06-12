package ru.myitlesson.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.app.App;
import ru.myitlesson.app.Utils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.activity.EditorActivity;
import ru.myitlesson.app.adapter.ListAdapter;
import ru.myitlesson.app.binder.OwnCourseViewBinder;
import ru.myitlesson.app.repository.CourseRepository;
import ru.myitlesson.app.repository.RepositoryResult;

import java.util.ArrayList;
import java.util.List;

public class MyCoursesFragment extends Fragment {

    private final List<CourseEntity> courses = new ArrayList<>();

    private final ListAdapter<CourseEntity, OwnCourseViewBinder> coursesAdapter = new ListAdapter<>(courses, OwnCourseViewBinder.class);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_page_my_courses, container, false);

        RecyclerView coursesRecyclerView = layout.findViewById(R.id.courses_recycler_view);
        coursesRecyclerView.setAdapter(coursesAdapter);

        FloatingActionButton addButton = layout.findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> Utils.startActivity(requireContext(), EditorActivity.class));

        CourseRepository courseRepository = App.getCourseRepository();
        courseRepository.getUserCourses((int) App.getClient().getUserId(), this::onGetUserCoursesCompleted);
        courseRepository.getUserAuthoredCourses((int) App.getClient().getUserId(), this::onGetUserCoursesCompleted);

        return layout;
    }

    public void onGetUserCoursesCompleted(RepositoryResult<List<CourseEntity>> result) {
        if(result instanceof RepositoryResult.Success) {
            courses.addAll(((RepositoryResult.Success<List<CourseEntity>>) result).data);
            new Handler(Looper.getMainLooper()).post(coursesAdapter::notifyDataSetChanged);
        } else {
            Utils.handleRepositoryError((RepositoryResult.Error<List<CourseEntity>>) result, getContext());
        }
    }
}
