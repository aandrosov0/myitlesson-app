package ru.myitlesson.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.api.entity.ModuleEntity;
import ru.myitlesson.app.R;
import ru.myitlesson.app.adapter.ListAdapter;
import ru.myitlesson.app.api.Client;
import ru.myitlesson.app.binder.ModuleViewBinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModulesFragment extends Fragment {

    private final List<ModuleEntity> modules = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.modules_fragment, container);

        RecyclerView modulesRecyclerView = layout.findViewById(R.id.modules_recycler_view);
        modulesRecyclerView.setAdapter(new ListAdapter<>(modules, ModuleViewBinder.class));
        modulesRecyclerView.addItemDecoration(new DividerItemDecoration(layout.getContext(), DividerItemDecoration.HORIZONTAL));

        return layout;
    }

    public void loadCourseData(CourseEntity course) throws IOException {
        for(int moduleId : course.getModules()) {
            modules.add(Client.getInstance().api().module().get(moduleId));
        }
    }
}
