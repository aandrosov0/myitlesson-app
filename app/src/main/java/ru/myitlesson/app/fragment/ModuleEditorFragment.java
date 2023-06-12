package ru.myitlesson.app.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import ru.myitlesson.api.entity.ModuleEntity;
import ru.myitlesson.app.App;
import ru.myitlesson.app.R;
import ru.myitlesson.app.activity.EditorActivity;
import ru.myitlesson.app.repository.ModuleRepository;
import ru.myitlesson.app.repository.RepositoryResult;

import java.util.List;

public class ModuleEditorFragment extends Fragment implements EditorActivity.OnSubjectEventListener {

    private final EditorActivity editorActivity;

    private final CourseEditorFragment courseEditorFragment;

    private final ModuleRepository moduleRepository;

    private ModuleEntity module;

    private Editable titleEditable;
    private Editable contentEditable;

    public ModuleEditorFragment(EditorActivity editorActivity) {
        super(R.layout.fragment_subject_editor);

        this.editorActivity = editorActivity;
        this.courseEditorFragment = editorActivity.getCourseEditorFragment();
        this.moduleRepository = App.getModuleRepository();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleEditable = ((TextInputEditText) view.findViewById(R.id.title_text_input_edit_text)).getEditableText();
        contentEditable = ((TextInputEditText) view.findViewById(R.id.description_text_input_edit_text)).getEditableText();
    }

    @Override
    public void onSave() {
        module.setTitle(titleEditable.toString());
        module.setContent(contentEditable.toString());

        moduleRepository.add(module, courseEditorFragment.getCourse().getId(), (result) -> {
            if(result instanceof RepositoryResult.Success) {
                Log.d("TAG", "onSave: " + App.getClient().getError());
                editorActivity.runOnUiThread(() -> Toast.makeText(editorActivity, "Module added " + ((RepositoryResult.Success<Integer>) result).data, Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    public void onRemove() {
        moduleRepository.delete(module.getId(), (result) -> editorActivity.runOnUiThread(() -> Toast.makeText(editorActivity, "Module deleted " + module.getId(), Toast.LENGTH_SHORT).show()));
    }

    @Override
    public void onView() {

    }

    @Override
    public void onInsertedInContainer() {
        courseEditorFragment.makeChoiceDialog((d, i) -> {
            if(i == DialogInterface.BUTTON_NEUTRAL) {
                editorActivity.navigateByToolbarMenuItem(R.id.type_action);
            } else {
                makeChoiceDialog((dialog, which) -> {});
            }
        });
    }

    public void makeChoiceDialog(DialogInterface.OnClickListener onClickListener) {
        moduleRepository.getCourseModules(courseEditorFragment.getCourse().getId(), result -> {
            if(result instanceof RepositoryResult.Success) {
                List<ModuleEntity> modules = ((RepositoryResult.Success<List<ModuleEntity>>) result).data;
                String[] moduleTitles = modules.stream().map(ModuleEntity::getTitle).toArray(String[]::new);

                editorActivity.runOnUiThread(new MaterialAlertDialogBuilder(editorActivity)
                        .setCancelable(false)
                        .setTitle(R.string.choice_module_action)
                        .setSingleChoiceItems(moduleTitles, -1, (d, w) -> {
                            module = modules.get(w);

                            if(titleEditable != null && contentEditable != null) {
                                titleEditable.replace(0, titleEditable.length(), module.getTitle());
                                contentEditable.replace(0, contentEditable.length(), module.getContent());

                            }
                            onClickListener.onClick(d, w);
                            d.dismiss();
                        })
                        .setNeutralButton(R.string.new_subject_action, (d, w) -> {
                            module = new ModuleEntity();
                            onClickListener.onClick(d, w);
                        })::show);
            }
        });
    }

    public ModuleEntity getModule() {
        return module;
    }
}
