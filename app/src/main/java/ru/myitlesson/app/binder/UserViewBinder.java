package ru.myitlesson.app.binder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import ru.myitlesson.api.entity.UserEntity;
import ru.myitlesson.app.R;
import ru.myitlesson.app.adapter.ListAdapter;

public class UserViewBinder extends ListAdapter.ViewBinder<UserEntity> {

    private final TextView nameTextView;
    private final TextView roleTextView;

    public UserViewBinder(@NonNull ViewGroup view) {
        super((ViewGroup) LayoutInflater.from(view.getContext()).inflate(R.layout.user_card, view, false));

        nameTextView = itemView.findViewById(R.id.nameTextView);
        roleTextView = itemView.findViewById(R.id.roleTextView);
    }

    @Override
    public void bind(UserEntity object) {
        nameTextView.setText(object.getUsername());
        roleTextView.setText(object.getRole().toString());
    }
}
