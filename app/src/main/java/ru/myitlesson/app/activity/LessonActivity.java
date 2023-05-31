package ru.myitlesson.app.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import io.noties.markwon.Markwon;
import ru.myitlesson.api.entity.LessonEntity;
import ru.myitlesson.app.R;

import java.io.IOException;

public class LessonActivity extends ClientActivity {

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
    }

    @Override
    protected void onLoadApiData() throws IOException {
        int id = getIntent().getIntExtra(LESSON_EXTRA, -1);
        LessonEntity lesson = api.lesson().get(id);

        Markwon markwon = Markwon.create(this);

        runOnUiThread(() -> toolbar.setTitle(lesson.getTitle()));
        runOnUiThread(() -> markwon.setMarkdown(contentTextView, lesson.getContent()));
    }
}
