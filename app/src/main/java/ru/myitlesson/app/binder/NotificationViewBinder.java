package ru.myitlesson.app.binder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import ru.myitlesson.app.R;
import ru.myitlesson.app.adapter.ListAdapter;

public class NotificationViewBinder extends ListAdapter.ViewBinder<Object> {

    private final TextView notificationTitle;
    private final TextView notificationDescription;

    public NotificationViewBinder(@NonNull ViewGroup view) {
        super((ViewGroup) LayoutInflater.from(view.getContext()).inflate(R.layout.notification_card, view, false));

        notificationTitle = itemView.findViewById(R.id.notification_title);
        notificationDescription = itemView.findViewById(R.id.notification_description);
    }

    @Override
    public void bind(Object object) {
        notificationTitle.setText(R.string.title);
        notificationDescription.setText(R.string.description);
    }
}
