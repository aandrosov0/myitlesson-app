package ru.myitlesson.app.binder;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import ru.myitlesson.api.entity.LessonEntity;
import ru.myitlesson.app.AppUtils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.activity.LessonActivity;
import ru.myitlesson.app.adapter.ListAdapter;

public class LessonViewBinder extends ListAdapter.ViewBinder<LessonEntity> {

    private final TextView titleTextView;

    public LessonViewBinder(@NonNull @NotNull ViewGroup view) {
        super((ViewGroup) LayoutInflater.from(view.getContext()).inflate(R.layout.lesson_item_layout, view, false));

        titleTextView = itemView.findViewById(R.id.title_text_view);
    }

    @Override
    public void bind(LessonEntity object) {
        String title = getAdapterPosition() + 1 + ". " + object.getTitle();
        titleTextView.setText(title);

        itemView.setOnClickListener(view -> openLesson(view.getContext(), object.getId()));
    }

    private void openLesson(Context context, int id) {
        AppUtils.startActivityWithIntExtra(context, LessonActivity.class, LessonActivity.LESSON_EXTRA, id);
    }
}
