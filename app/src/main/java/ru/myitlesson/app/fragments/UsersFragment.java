package ru.myitlesson.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.myitlesson.app.R;
import ru.myitlesson.app.adapter.UserAdapter;

public class UsersFragment extends Fragment {

    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.users_fragment, container, false);

        UserAdapter userAdapter = new UserAdapter();

        RecyclerView usersRecyclerView = layout.findViewById(R.id.users_recycle_view);

        usersRecyclerView.setAdapter(userAdapter);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return layout;
    }
}