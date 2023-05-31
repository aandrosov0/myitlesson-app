package ru.myitlesson.app.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.api.entity.UserEntity;
import ru.myitlesson.app.AppUtils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.api.ApiExecutor;
import ru.myitlesson.app.fragment.CourseEditorFragment;
import ru.myitlesson.app.fragment.LessonEditorFragment;
import ru.myitlesson.app.fragment.ModuleEditorFragment;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class EditorActivity extends ClientActivity {

    private OnSubjectEventListener subjectEventListener;

    private List<CourseEntity> courses;

    private Toolbar toolbar;

    public interface OnSubjectEventListener {

        void save(@NonNull UserEntity user) throws IOException;

        void remove(@NonNull UserEntity user) throws IOException;

        void view();

        void setCourse(@Nullable CourseEntity course) throws IOException;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.editor_activity);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> navigateByToolbarMenuItem(item.getItemId()));
    }

    @Override
    protected void onLoadApiData() throws IOException {
        super.onLoadApiData();
        courses = client.getEntities(user.getAuthoredCourses(), api.course()::get);
    }

    public boolean navigateByToolbarMenuItem(int itemId) {
        if(itemId == R.id.type_item) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.set_type)
                    .setSingleChoiceItems(R.array.subjects, -1, this::submitSubject)
                    .show();
            return true;
        }

        if(itemId == R.id.save_item) {
            new ApiExecutor(() -> subjectEventListener.save(user), exception -> AppUtils.handleException(exception, this)).start();
            return true;
        }

        if(itemId == R.id.view_item) {
            subjectEventListener.view();
            return true;
        }

        if(itemId == R.id.remove_item) {
            new ApiExecutor(() -> subjectEventListener.remove(user), exception -> AppUtils.handleException(exception, this)).start();
            return true;
        }

        new ApiExecutor(this::onLoadApiData, exception -> AppUtils.handleException(exception, this)).start();
        return false;
    }

    private void submitSubject(DialogInterface dialog, int which) {
        dialog.dismiss();

        String[] subjects = getResources().getStringArray(R.array.subjects);
        String subject = subjects[which];

        toolbar.getMenu().findItem(R.id.type_item).setTitle(subject);

        Fragment fragment = null;

        if(subject.equals(getString(R.string.course))) {
            fragment = AppUtils.setFragmentInContainer(R.id.fragment_container_view, CourseEditorFragment.class, getSupportFragmentManager());
        } else if(subject.equals(getString(R.string.module))) {
            fragment = AppUtils.setFragmentInContainer(R.id.fragment_container_view, ModuleEditorFragment.class, getSupportFragmentManager());
        } else if(subject.equals(getString(R.string.lesson))) {
            fragment = AppUtils.setFragmentInContainer(R.id.fragment_container_view, LessonEditorFragment.class, getSupportFragmentManager());
        }

        subjectEventListener = (OnSubjectEventListener) Objects.requireNonNull(fragment);

        String[] courseTitles = courses.stream().map(CourseEntity::getTitle).toArray(String[]::new);
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.choice_course)
                .setSingleChoiceItems(courseTitles, -1, this::checkCourse)
                .setNeutralButton(R.string.new_subject, this::checkCourse)
                .show();
    }

    private void checkCourse(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_NEUTRAL) {
            new ApiExecutor(() -> subjectEventListener.setCourse(null), exception -> AppUtils.handleException(exception, this)).start();
        } else {
            CourseEntity course = courses.get(which);
            new ApiExecutor(() -> subjectEventListener.setCourse(course), exception -> AppUtils.handleException(exception, this)).start();
        }

        dialog.dismiss();
    }
}
