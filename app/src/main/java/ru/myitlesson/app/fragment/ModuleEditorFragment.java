package ru.myitlesson.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import org.jetbrains.annotations.NotNull;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.api.entity.ModuleEntity;
import ru.myitlesson.api.entity.UserEntity;
import ru.myitlesson.app.AppUtils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.activity.EditorActivity;
import ru.myitlesson.app.api.ApiExecutor;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class ModuleEditorFragment extends Fragment implements EditorActivity.OnSubjectEventListener {

    protected TextInputEditText titleTextInputEditText;
    protected TextInputEditText contentTextInputEditText;

    protected EditorActivity editorActivity;

    protected CourseEntity course;
    protected ModuleEntity module;

    public ModuleEditorFragment() {
        super(R.layout.subject_editor_layout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editorActivity = (EditorActivity) requireActivity();

        titleTextInputEditText = view.findViewById(R.id.title_text_input_edit_text);
        contentTextInputEditText = view.findViewById(R.id.description_text_input_edit_text);
    }

    @Override
    public void save(@NotNull UserEntity user) throws IOException {
        module.setTitle(Objects.requireNonNull(titleTextInputEditText.getText()).toString());
        module.setContent(Objects.requireNonNull(contentTextInputEditText.getText()).toString());

        editorActivity.getApi().module().add(module, course);
        editorActivity.runOnUiThread(() -> Toast.makeText(editorActivity, R.string.saved, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void remove(@NotNull UserEntity user) throws IOException {
        editorActivity.getApi().module().remove(module.getId());
        editorActivity.runOnUiThread(() -> Toast.makeText(editorActivity, R.string.removed, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void view() {

    }

    @Override
    public void setCourse(CourseEntity course) throws IOException {
        if(course == null) {
            editorActivity.runOnUiThread(() -> editorActivity.navigateByToolbarMenuItem(R.id.type_item));
            return;
        }

        List<ModuleEntity> modules = editorActivity.getClient().getEntities(course.getModules(), editorActivity.getApi().module()::get);
        String[] moduleTitles = modules.stream().map(ModuleEntity::getTitle).toArray(String[]::new);

        MaterialAlertDialogBuilder moduleDialog = new MaterialAlertDialogBuilder(editorActivity)
                .setCancelable(false)
                .setTitle(R.string.choice_module)
                .setSingleChoiceItems(moduleTitles, 0, (dialog, which) -> module = modules.get(which))
                .setPositiveButton(android.R.string.ok, (d, w) -> new ApiExecutor(() -> setModule(module), e -> AppUtils.handleException(e, editorActivity)).start())
                .setNeutralButton(R.string.new_subject, (d, w) -> new ApiExecutor(() -> setModule(null), e -> AppUtils.handleException(e, editorActivity)).start());

        editorActivity.runOnUiThread(moduleDialog::show);
    }

    public void setModule(ModuleEntity module) throws IOException {
        if(module == null) {
            module = new ModuleEntity();
        }

        titleTextInputEditText.setText(module.getTitle());
        contentTextInputEditText.setText(module.getContent());
        this.module = module;
    }
}
