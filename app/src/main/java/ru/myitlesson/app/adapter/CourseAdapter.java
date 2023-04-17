package ru.myitlesson.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.myitlesson.app.R;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private final TextView bodyTextView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title_text_view);
            bodyTextView = itemView.findViewById(R.id.body_text_view);
        }
    }

    public CourseAdapter() {
        // TODO: list of courses
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card, parent, false);

        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.titleTextView.setText(R.string.title);
        holder.bodyTextView.setText(R.string.body);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
