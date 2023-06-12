package ru.myitlesson.app.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import ru.myitlesson.app.Utils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.fragment.CourseEditorFragment;
import ru.myitlesson.app.fragment.LessonEditorFragment;
import ru.myitlesson.app.fragment.ModuleEditorFragment;

import java.util.Objects;

public class EditorActivity extends AppCompatActivity {

    private OnSubjectEventListener subjectEventListener;

    private Toolbar toolbar;

    private CourseEditorFragment courseEditorFragment;
    private ModuleEditorFragment moduleEditorFragment;
    private LessonEditorFragment lessonEditorFragment;

    public interface OnSubjectEventListener {

        void onSave();

        void onRemove();

        void onView();

        void onInsertedInContainer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editor);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> navigateByToolbarMenuItem(item.getItemId()));

        courseEditorFragment = new CourseEditorFragment(this);
        moduleEditorFragment = new ModuleEditorFragment(this);
        lessonEditorFragment = new LessonEditorFragment(this);
    }

    public boolean navigateByToolbarMenuItem(int itemId) {
        if(itemId == R.id.type_action) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.set_type_action)
                    .setSingleChoiceItems(R.array.subjects, -1, this::submitSubject)
                    .show();
            return true;
        }

        if(itemId == R.id.save_action) {
            subjectEventListener.onSave();
            return true;
        }

        if(itemId == R.id.view_action) {
            subjectEventListener.onView();
            return true;
        }

        if(itemId == R.id.remove_action) {
            subjectEventListener.onRemove();
            return true;
        }

        return false;
    }


    public CourseEditorFragment getCourseEditorFragment() {
        return courseEditorFragment;
    }

    public ModuleEditorFragment getModuleEditorFragment() {
        return moduleEditorFragment;
    }

    private void submitSubject(DialogInterface dialog, int which) {
        dialog.dismiss();

        String[] subjects = getResources().getStringArray(R.array.subjects);
        String subject = subjects[which];

        toolbar.getMenu().findItem(R.id.type_action).setTitle(subject);

        Fragment fragment = null;

        if(subject.equals(getString(R.string.course))) {
            fragment = Utils.setFragmentInContainer(R.id.fragment_container_view, courseEditorFragment, getSupportFragmentManager());
        } else if(subject.equals(getString(R.string.module))) {
            fragment = Utils.setFragmentInContainer(R.id.fragment_container_view, moduleEditorFragment, getSupportFragmentManager());
        } else if(subject.equals(getString(R.string.lesson))) {
            fragment = Utils.setFragmentInContainer(R.id.fragment_container_view, lessonEditorFragment, getSupportFragmentManager());
        }

        subjectEventListener = (OnSubjectEventListener) Objects.requireNonNull(fragment);
        subjectEventListener.onInsertedInContainer();
    }
}

