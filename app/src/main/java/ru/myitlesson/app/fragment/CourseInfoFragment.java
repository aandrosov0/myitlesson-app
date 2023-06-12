package ru.myitlesson.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.app.R;

public class CourseInfoFragment extends Fragment {

    private TextView descriptionTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_course_info, container);

        descriptionTextView = layout.findViewById(R.id.description_text_view);

        return layout;
    }

    public void loadCourse(CourseEntity courseEntity) {
        descriptionTextView.setText(courseEntity.getDescription());
    }
}
