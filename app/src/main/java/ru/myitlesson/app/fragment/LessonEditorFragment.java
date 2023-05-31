package ru.myitlesson.app.fragment;

import android.widget.Toast;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import org.jetbrains.annotations.NotNull;
import ru.myitlesson.api.entity.LessonEntity;
import ru.myitlesson.api.entity.ModuleEntity;
import ru.myitlesson.api.entity.UserEntity;
import ru.myitlesson.app.R;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LessonEditorFragment extends ModuleEditorFragment {

    protected LessonEntity lesson;

    @Override
    public void save(@NotNull UserEntity user) throws IOException {
        lesson.setTitle(Objects.requireNonNull(titleTextInputEditText.getText()).toString());
        lesson.setContent(Objects.requireNonNull(contentTextInputEditText.getText()).toString());

        editorActivity.getApi().lesson().add(lesson, module);
        editorActivity.runOnUiThread(() -> Toast.makeText(editorActivity, R.string.saved, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void remove(@NotNull UserEntity user) throws IOException {
        editorActivity.getApi().lesson().remove(lesson.getId());
        editorActivity.runOnUiThread(() -> Toast.makeText(editorActivity, R.string.removed, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void setModule(ModuleEntity module) throws IOException {
        if(module == null) {
            editorActivity.navigateByToolbarMenuItem(R.id.type_item);
            return;
        }

        this.module = module;

        List<Integer> lessonIds = module.getLessons();

        List<LessonEntity> lessons = editorActivity.getClient().getEntities(lessonIds, editorActivity.getApi().lesson()::get);
        String[] lessonTitles = lessons.stream().map(LessonEntity::getTitle).toArray(String[]::new);

        MaterialAlertDialogBuilder lessonDialog = new MaterialAlertDialogBuilder(editorActivity)
                .setCancelable(false)
                .setTitle(R.string.choice_module)
                .setSingleChoiceItems(lessonTitles, 0, (dialog, which) -> lesson = lessons.get(which))
                .setPositiveButton(android.R.string.ok, (dialog, which) -> setLesson(lesson))
                .setNeutralButton(R.string.new_subject, (dialog, which) -> setLesson(null));

        editorActivity.runOnUiThread(lessonDialog::show);
    }

    public void setLesson(LessonEntity lesson) {
        if(lesson == null) {
            lesson = new LessonEntity("", "", this.module.getId());
        }

        titleTextInputEditText.setText(lesson.getTitle());
        contentTextInputEditText.setText(lesson.getContent());

        this.lesson = lesson;
    }
}
