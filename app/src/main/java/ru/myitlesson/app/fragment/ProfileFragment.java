package ru.myitlesson.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.myitlesson.api.entity.UserEntity;
import ru.myitlesson.app.App;
import ru.myitlesson.app.Utils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.repository.RepositoryResult;
import ru.myitlesson.app.repository.UserRepository;

public class ProfileFragment extends Fragment {

    private TextView usernameTextView;

    private TextView roleTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_page_my_profile, container, false);

        usernameTextView = layout.findViewById(R.id.username_text_view);
        roleTextView = layout.findViewById(R.id.role_text_view);

        final Button settingsButton = layout.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(view -> Utils.setFragmentInContainer(R.id.fragment_container_view, SettingsFragment.class, getParentFragmentManager()));

        final Button notificationsButton = layout.findViewById(R.id.notifications_button);
        notificationsButton.setOnClickListener(view -> Utils.setFragmentInContainer(R.id.fragment_container_view, NotificationsFragment.class, getParentFragmentManager()));

        UserRepository userRepository = App.getUserRepository();
        userRepository.get((int) App.getClient().getUserId(), this::onUserGetCompleted);
        return layout;
    }

    private void onUserGetCompleted(RepositoryResult<UserEntity> result) {
        if(result instanceof RepositoryResult.Success) {
            UserEntity user = ((RepositoryResult.Success<UserEntity>) result).data;

            usernameTextView.post(() -> usernameTextView.setText(user.getUsername()));
            roleTextView.post(() -> roleTextView.setText(user.getRole().toString()));
        }
    }
}
