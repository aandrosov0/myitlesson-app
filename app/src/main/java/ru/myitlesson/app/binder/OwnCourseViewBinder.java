package ru.myitlesson.app.binder;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.app.R;
import ru.myitlesson.app.activity.CourseActivity;

public class OwnCourseViewBinder extends CourseViewBinder {


    public OwnCourseViewBinder(@NonNull @NotNull ViewGroup view) {
        super(view);
    }

    @Override
    public void bind(CourseEntity object) {
        super.bind(object);

        Button actionButton = itemView.findViewById(R.id.add_button);
        actionButton.setText(R.string.open_course_action);
        actionButton.setOnClickListener(view -> openCourse(view.getContext(), object.getId()));
    }

    private void openCourse(Context context, int id) {
        Intent intent = new Intent(context, CourseActivity.class);
        intent.putExtra(CourseActivity.COURSE_EXTRA, id);
        context.startActivity(intent);
    }
}