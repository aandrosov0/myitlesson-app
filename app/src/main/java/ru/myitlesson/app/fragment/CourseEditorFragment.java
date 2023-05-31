package ru.myitlesson.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import org.jetbrains.annotations.NotNull;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.api.entity.UserEntity;
import ru.myitlesson.app.R;
import ru.myitlesson.app.activity.EditorActivity;

import java.io.IOException;
import java.util.Objects;

public class CourseEditorFragment extends Fragment implements EditorActivity.OnSubjectEventListener {

    private TextInputEditText titleTextInputEditText;
    private TextInputEditText descriptionTextInputEditText;

    private CourseEntity course;

    private EditorActivity editorActivity;

    public CourseEditorFragment() {
        super(R.layout.subject_editor_layout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editorActivity = (EditorActivity) requireActivity();

        titleTextInputEditText = view.findViewById(R.id.title_text_input_edit_text);
        descriptionTextInputEditText = view.findViewById(R.id.description_text_input_edit_text);
    }

    @Override
    public void save(@NotNull UserEntity user) throws IOException {
        course.setTitle(Objects.requireNonNull(titleTextInputEditText.getText()).toString());
        course.setDescription(Objects.requireNonNull(descriptionTextInputEditText.getText()).toString());

        editorActivity.getApi().course().add(course, user);
        editorActivity.runOnUiThread(() -> Toast.makeText(editorActivity, R.string.saved, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void view() {

    }

    @Override
    public void setCourse(@Nullable CourseEntity course) {
        if(course == null) {
            course = new CourseEntity();
        }

        this.course = course;
        editorActivity.runOnUiThread(() -> titleTextInputEditText.setText(this.course.getTitle()));
        editorActivity.runOnUiThread(() -> descriptionTextInputEditText.setText(this.course.getDescription()));
    }

    @Override
    public void remove(@NotNull UserEntity user) throws IOException {
        if(course.getId() != 0) {
            editorActivity.getApi().course().remove(course.getId());
            editorActivity.runOnUiThread(() -> Toast.makeText(editorActivity, R.string.removed, Toast.LENGTH_SHORT).show());
        }
    }
}
