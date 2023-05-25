package ru.myitlesson.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ru.myitlesson.api.entity.UserEntity;
import ru.myitlesson.app.InterfaceUtils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.adapter.ListAdapter;
import ru.myitlesson.app.api.ApiExecutor;
import ru.myitlesson.app.api.Client;
import ru.myitlesson.app.binder.UserViewBinder;

import java.io.IOException;
import java.util.List;

public class UsersFragment extends Fragment {

    public static final int MAX_USERS = 10;

    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.users_fragment, container, false);

        new ApiExecutor(this::loadData, exception -> InterfaceUtils.handleException(exception, getContext())).start();

        return layout;
    }

    private void loadData() throws IOException {
        Activity activity = requireActivity();
        List<UserEntity> users = Client.getInstance().api().user().getAll(MAX_USERS, 0);

        RecyclerView usersRecyclerView = activity.findViewById(R.id.users_recycle_view);
        activity.runOnUiThread(() -> usersRecyclerView.setAdapter(new ListAdapter<>(users, UserViewBinder.class)));
    }
}