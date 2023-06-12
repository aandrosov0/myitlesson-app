package ru.myitlesson.app.binder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.app.App;
import ru.myitlesson.app.R;
import ru.myitlesson.app.adapter.ListAdapter;
import ru.myitlesson.app.repository.CourseRepository;
import ru.myitlesson.app.repository.RepositoryResult;

import java.io.File;

public class CourseViewBinder extends ListAdapter.ViewBinder<CourseEntity> {

    protected final TextView titleTextView;
    protected final TextView bodyTextView;
    protected final ImageView imageView;

    protected final CourseRepository courseRepository;

    public CourseViewBinder(@NonNull ViewGroup view) {
        super((ViewGroup) LayoutInflater.from(view.getContext()).inflate(R.layout.card_course,  view, false));

        titleTextView = itemView.findViewById(R.id.title_text_view);
        bodyTextView = itemView.findViewById(R.id.body_text_view);
        imageView = itemView.findViewById(R.id.image_view);

        courseRepository = App.getCourseRepository();
    }

    @Override
    public void bind(CourseEntity course) {
        titleTextView.setText(course.getTitle());
        bodyTextView.setText(course.getDescription());

        courseRepository.getImage(course, this::onGetImageComplete);
    }

    private void onGetImageComplete(RepositoryResult<File> result) {
        if(result instanceof RepositoryResult.Success) {
            File image = ((RepositoryResult.Success<File>) result).data;

            if(image != null) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
                imageView.post(() -> imageView.setImageBitmap(imageBitmap));
            }
        }
    }
}
