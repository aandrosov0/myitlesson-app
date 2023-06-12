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
import ru.myitlesson.api.entity.LessonEntity;
import ru.myitlesson.app.App;
import ru.myitlesson.app.R;
import ru.myitlesson.app.activity.EditorActivity;
import ru.myitlesson.app.repository.LessonRepository;
import ru.myitlesson.app.repository.RepositoryResult;

import java.util.List;

public class LessonEditorFragment extends Fragment implements EditorActivity.OnSubjectEventListener {

    private final EditorActivity editorActivity;

    private final CourseEditorFragment courseEditorFragment;
    private final ModuleEditorFragment moduleEditorFragment;

    private final LessonRepository lessonRepository;

    private Editable titleEditable;
    private Editable contentEditable;

    private LessonEntity lesson;

    public LessonEditorFragment(EditorActivity editorActivity) {
        super(R.layout.fragment_subject_editor);

        this.editorActivity = editorActivity;
        this.lessonRepository = App.getLessonRepository();

        this.courseEditorFragment = editorActivity.getCourseEditorFragment();
        this.moduleEditorFragment = editorActivity.getModuleEditorFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleEditable = ((TextInputEditText) view.findViewById(R.id.title_text_input_edit_text)).getEditableText();
        contentEditable = ((TextInputEditText) view.findViewById(R.id.description_text_input_edit_text)).getEditableText();
    }

    @Override
    public void onSave() {
        lesson.setTitle(titleEditable.toString());
        lesson.setContent(contentEditable.toString());

        lessonRepository.add(lesson, moduleEditorFragment.getModule().getId(), result -> {
            if(result instanceof RepositoryResult.Success) {
                editorActivity.runOnUiThread(() -> Toast.makeText(editorActivity, "Lesson added " + ((RepositoryResult.Success<Integer>) result).data, Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    public void onRemove() {
        lessonRepository.delete(lesson.getId(), result -> {
            if(result instanceof RepositoryResult.Success) {
                editorActivity.runOnUiThread(() -> Toast.makeText(editorActivity, "Lesson removed " + lesson.getId(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    public void onView() {

    }

    @Override
    public void onInsertedInContainer() {
        courseEditorFragment.makeChoiceDialog((dialog, which) -> {
            if(which == DialogInterface.BUTTON_NEUTRAL) {
                editorActivity.navigateByToolbarMenuItem(R.id.type_action);
            } else {
                moduleEditorFragment.makeChoiceDialog((dialog1, which1) -> {
                    if(which1 == DialogInterface.BUTTON_NEUTRAL) {
                        editorActivity.navigateByToolbarMenuItem(R.id.type_action);
                    } else {
                        makeChoiceDialog((d, w) -> {});
                    }
                });
            }
        });
    }

    public void makeChoiceDialog(DialogInterface.OnClickListener onClickListener) {
        lessonRepository.getModuleLessons(moduleEditorFragment.getModule().getId(), result -> {
            if(result instanceof RepositoryResult.Success) {
                List<LessonEntity> lessons = ((RepositoryResult.Success<List<LessonEntity>>) result).data;
                String[] lessonTitles = lessons.stream().map(LessonEntity::getTitle).toArray(String[]::new);

                editorActivity.runOnUiThread(new MaterialAlertDialogBuilder(editorActivity)
                        .setCancelable(false)
                        .setTitle(R.string.choice_lesson_action)
                        .setSingleChoiceItems(lessonTitles, -1, (dialog, which) -> {
                            lesson = lessons.get(which);

                            if(titleEditable != null && contentEditable != null) {
                                titleEditable.replace(0, titleEditable.length(), lesson.getTitle());
                                contentEditable.replace(0, contentEditable.length(), lesson.getContent());
                            }

                            onClickListener.onClick(dialog, which);
                            dialog.dismiss();
                        }).setNeutralButton(R.string.new_subject_action, ((dialog, which) -> {
                            lesson = new LessonEntity("", "", moduleEditorFragment.getModule().getId());
                            onClickListener.onClick(dialog, which);
                        }))::show);
            }
        });
    }
}
