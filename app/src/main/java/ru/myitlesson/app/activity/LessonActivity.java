package ru.myitlesson.app.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.noties.markwon.Markwon;
import ru.myitlesson.api.entity.LessonEntity;
import ru.myitlesson.app.App;
import ru.myitlesson.app.R;
import ru.myitlesson.app.repository.RepositoryResult;

public class LessonActivity extends AppCompatActivity {

    public static final String LESSON_EXTRA = "LESSON_ID";

    private TextView contentTextView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        contentTextView = findViewById(R.id.content_text_view);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        App.getLessonRepository().get(getIntent().getIntExtra(LESSON_EXTRA, 0), this::onLessonGetCompleted);
    }

    private void onLessonGetCompleted(RepositoryResult<LessonEntity> result) {
        if(result instanceof RepositoryResult.Success) {
            LessonEntity lesson = ((RepositoryResult.Success<LessonEntity>) result).data;

            Markwon markwon = Markwon.create(this);

            runOnUiThread(() -> toolbar.setTitle(lesson.getTitle()));
            runOnUiThread(() -> markwon.setMarkdown(contentTextView, lesson.getContent()));
        }
    }
}
