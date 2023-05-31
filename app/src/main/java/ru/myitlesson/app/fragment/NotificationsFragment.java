package ru.myitlesson.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.myitlesson.app.R;
import ru.myitlesson.app.activity.MainActivity;
import ru.myitlesson.app.adapter.ListAdapter;
import ru.myitlesson.app.binder.NotificationViewBinder;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.notifications_fragment, container, false);
        MainActivity mainActivity = (MainActivity) requireActivity();

        Toolbar notificationToolbar = layout.findViewById(R.id.notification_toolbar);

        mainActivity.setSupportActionBar(notificationToolbar);
        notificationToolbar.setNavigationOnClickListener(view -> mainActivity.setFragmentByNavigationItem(R.id.profile_item));

        RecyclerView notificationsRecyclerView = layout.findViewById(R.id.notifications_recycler_view);

        notificationsRecyclerView.setAdapter(new ListAdapter<>(new ArrayList<>(), NotificationViewBinder.class));
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return layout;
    }
}