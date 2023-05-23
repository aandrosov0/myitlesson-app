package ru.myitlesson.app.binder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.app.R;
import ru.myitlesson.app.adapter.ListAdapter;
import ru.myitlesson.app.api.Client;

import java.io.File;
import java.io.IOException;

public class CourseViewBinder extends ListAdapter.ViewBinder<CourseEntity> {

    protected final TextView titleTextView;
    protected final TextView bodyTextView;
    protected final ImageView imageView;

    public CourseViewBinder(@NonNull ViewGroup view) {
        super((ViewGroup) LayoutInflater.from(view.getContext()).inflate(R.layout.course_card,  view, false));

        titleTextView = itemView.findViewById(R.id.title_text_view);
        bodyTextView = itemView.findViewById(R.id.body_text_view);
        imageView = itemView.findViewById(R.id.image_view);
    }

    @Override
    public void bind(CourseEntity object) {
        titleTextView.setText(object.getTitle());
        bodyTextView.setText(object.getDescription());

        new Thread(() -> Client.showDialogIfApiError(titleTextView.getContext(), () -> loadCourseImage(object.getId()))).start();
    }

    private void loadCourseImage(int id) throws IOException {
        File image = Client.getInstance().api().course().imageGet(id);

        if(image != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
            imageView.post(() -> imageView.setImageBitmap(bitmap));
        }
    }
}
