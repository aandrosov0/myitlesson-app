package ru.myitlesson.app.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.app.App;
import ru.myitlesson.app.R;
import ru.myitlesson.app.activity.EditorActivity;
import ru.myitlesson.app.repository.CourseRepository;
import ru.myitlesson.app.repository.RepositoryResult;

import java.util.List;

public class CourseEditorFragment extends Fragment implements EditorActivity.OnSubjectEventListener {

    private CourseEntity course;

    private List<CourseEntity> courses;

    private final CourseRepository courseRepository;

    private Editable titleEditable;

    private Editable contentEditable;

    private final EditorActivity editorActivity;

    public CourseEditorFragment(EditorActivity editorActivity) {
        super(R.layout.fragment_subject_editor);

        this.editorActivity = editorActivity;

        courseRepository = App.getCourseRepository();
        courseRepository.getUserAuthoredCourses((int) App.getClient().getUserId(), this::onGetUserAuthoredCoursesCompleted);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputEditText titleTextInputEditText = view.findViewById(R.id.title_text_input_edit_text);
        TextInputEditText contentTextInputEditText = view.findViewById(R.id.description_text_input_edit_text);

        titleEditable = titleTextInputEditText.getEditableText();
        contentEditable = contentTextInputEditText.getEditableText();
    }

    @Override
    public void onSave() {
        course.setTitle(titleEditable.toString());
        course.setDescription(contentEditable.toString());

        courseRepository.add(course, (int) App.getClient().getUserId(), this::onAddCourseCompleted);
    }

    @Override
    public void onRemove() {
        courseRepository.delete(course.getId(), this::onDeleteCourseCompleted);
    }

    @Override
    public void onView() {

    }

    @Override
    public void onInsertedInContainer() {
        makeChoiceDialog((d, i) -> {});
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void makeChoiceDialog(DialogInterface.OnClickListener onClickListener) {
        String[] courseTitles = courses.stream().map(CourseEntity::getTitle).toArray(String[]::new);

        new MaterialAlertDialogBuilder(editorActivity)
                .setCancelable(false)
                .setTitle(R.string.choice_course_action)
                .setSingleChoiceItems(courseTitles, -1, (dialog, which) -> {
                    course = courses.get(which);

                    if(titleEditable != null && contentEditable != null) {
                        titleEditable.insert(0, course.getTitle());
                        contentEditable.insert(0, course.getDescription());
                    }

                    onClickListener.onClick(dialog, which);
                    dialog.dismiss();
                })
                .setNeutralButton(R.string.new_subject_action, (dialog, which) ->{
                    course = new CourseEntity();
                    onClickListener.onClick(dialog, which);
                })
                .show();
    }

    private void onGetUserAuthoredCoursesCompleted(RepositoryResult<List<CourseEntity>> result) {
        if(result instanceof RepositoryResult.Success) {
            courses = ((RepositoryResult.Success<List<CourseEntity>>) result).data;
        }
    }

    private void onAddCourseCompleted(RepositoryResult<Integer> result) {
        if(result instanceof RepositoryResult.Success) {
            requireView().post(() -> Toast.makeText(getContext(), "Course added " + ((RepositoryResult.Success<Integer>) result).data , Toast.LENGTH_SHORT).show());
        }
    }

    private void onDeleteCourseCompleted(RepositoryResult<Integer> result) {
        if(result instanceof RepositoryResult.Success) {
            requireView().post(() -> Toast.makeText(getContext(), "Course deleted " + ((RepositoryResult.Success<Integer>) result).data, Toast.LENGTH_SHORT).show());
        }
    }
}
