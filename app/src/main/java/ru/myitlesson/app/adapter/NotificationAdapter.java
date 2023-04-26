package ru.myitlesson.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.myitlesson.app.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        private final TextView notificationTitle;
        private final TextView notificationDescription;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            notificationTitle = itemView.findViewById(R.id.notification_title);
            notificationDescription = itemView.findViewById(R.id.notification_description);
        }
    }

    public NotificationAdapter() {
        // TODO: make ArrayList of notifications
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card, parent, false);

        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        holder.notificationTitle.setText(R.string.title);
        holder.notificationDescription.setText(R.string.description);

        // TODO: Bind ADD COURSE button
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
