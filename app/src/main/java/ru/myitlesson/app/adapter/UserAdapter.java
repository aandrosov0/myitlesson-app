package ru.myitlesson.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.myitlesson.app.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView roleTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);
            roleTextView = itemView.findViewById(R.id.roleTextView);
        }
    }

    public UserAdapter() {
        // TODO: list of users
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);

        return new UserAdapter.UserViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.nameTextView.setText(R.string.user_credentials);
        holder.roleTextView.setText(R.string.user_role);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
