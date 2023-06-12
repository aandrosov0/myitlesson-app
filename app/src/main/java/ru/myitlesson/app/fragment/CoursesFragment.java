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

import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.app.App;
import ru.myitlesson.app.Utils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.adapter.ListAdapter;
import ru.myitlesson.app.binder.CourseViewBinder;
import ru.myitlesson.app.repository.CourseRepository;
import ru.myitlesson.app.repository.RepositoryResult;

import java.util.ArrayList;
import java.util.List;

public class CoursesFragment extends Fragment {

    public static final int MAX_COURSES = 10;

    private final List<CourseEntity> courses = new ArrayList<>();
    private final ListAdapter<CourseEntity, CourseViewBinder> coursesAdapter = new ListAdapter<>(courses, CourseViewBinder.class);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_page_courses, container, false);

        RecyclerView coursesRecyclerView = layout.findViewById(R.id.courses_recycler_view);
        coursesRecyclerView.setAdapter(coursesAdapter);

        CourseRepository courseRepository = App.getCourseRepository();
        courseRepository.getAmount(MAX_COURSES, 0, this::onGetAmountComplete);

        return layout;
    }

    private void onGetAmountComplete(RepositoryResult<List<CourseEntity>> result) {
        if(result instanceof RepositoryResult.Success) {
            courses.clear();
            courses.addAll(((RepositoryResult.Success<List<CourseEntity>>) result).data);
            new Handler(Looper.getMainLooper()).post(coursesAdapter::notifyDataSetChanged);
        } else {
            Utils.handleRepositoryError((RepositoryResult.Error<List<CourseEntity>>) result, getContext());
        }
    }
}
