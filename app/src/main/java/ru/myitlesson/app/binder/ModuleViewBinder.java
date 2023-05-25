package ru.myitlesson.app.binder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import ru.myitlesson.api.entity.LessonEntity;
import ru.myitlesson.api.entity.ModuleEntity;
import ru.myitlesson.app.InterfaceUtils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.adapter.ListAdapter;
import ru.myitlesson.app.api.ApiExecutor;
import ru.myitlesson.app.api.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModuleViewBinder extends ListAdapter.ViewBinder<ModuleEntity> {

    private final TextView titleTextView;
    private final TextView descriptionTextView;

    private final RecyclerView lessonsRecyclerView;

    public ModuleViewBinder(@NonNull ViewGroup view) {
        super((ViewGroup) LayoutInflater.from(view.getContext()).inflate(R.layout.module_card, view, false));

        titleTextView = itemView.findViewById(R.id.title_text_view);
        descriptionTextView = itemView.findViewById(R.id.description_text_view);
        lessonsRecyclerView = itemView.findViewById(R.id.lessons_recycler_view);

        lessonsRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void bind(ModuleEntity object) {
        String title = getAdapterPosition()+1 + ". " + object.getTitle();

        titleTextView.setText(title);
        descriptionTextView.setText(object.getContent());

        new ApiExecutor(() -> loadLessons(object), exception -> InterfaceUtils.handleException(exception, itemView.getContext())).start();
    }

    private void loadLessons(ModuleEntity module) throws IOException {
        List<LessonEntity> lessons = new ArrayList<>();
        for(int lessonId : module.getLessons()) {
            lessons.add(Client.getInstance().api().lesson().get(lessonId));
        }
        itemView.post(() -> lessonsRecyclerView.setAdapter(new ListAdapter<>(lessons, LessonViewBinder.class)));
    }
}
