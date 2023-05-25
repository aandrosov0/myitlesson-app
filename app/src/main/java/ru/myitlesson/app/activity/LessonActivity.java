package ru.myitlesson.app.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.noties.markwon.Markwon;
import ru.myitlesson.api.entity.LessonEntity;
import ru.myitlesson.app.InterfaceUtils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.api.ApiExecutor;
import ru.myitlesson.app.api.Client;

import java.io.IOException;

public class LessonActivity extends AppCompatActivity {

    public static final String LESSON_EXTRA = "LESSON_ID";

    private TextView contentTextView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_activity);

        contentTextView = findViewById(R.id.content_text_view);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        int lessonId = getIntent().getIntExtra(LESSON_EXTRA, -1);
        new ApiExecutor(() -> loadLesson(lessonId), exception -> InterfaceUtils.handleException(exception, this)).start();
    }

    private void loadLesson(int id) throws IOException {
        LessonEntity lesson = Client.getInstance().api().lesson().get(id);
        Markwon markwon = Markwon.create(this);

        runOnUiThread(() -> toolbar.setTitle(lesson.getTitle()));
        runOnUiThread(() -> markwon.setMarkdown(contentTextView, lesson.getContent()));
    }
}
