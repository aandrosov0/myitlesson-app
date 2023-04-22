package ru.myitlesson.app.fragments;

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
import ru.myitlesson.app.activities.MainActivity;
import ru.myitlesson.app.adapter.NotificationAdapter;

public class NotificationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout1 = inflater.inflate(R.layout.notifications_fragment, container, false);
        MainActivity mainActivity = (MainActivity) requireActivity();

        Toolbar notificationToolbar = layout1.findViewById(R.id.notification_toolbar);
        notificationToolbar.setTitle(R.string.notifications);

        notificationToolbar.setNavigationIcon(R.drawable.arrow_back);

        mainActivity.setSupportActionBar(notificationToolbar);
        notificationToolbar.setNavigationOnClickListener(view -> mainActivity.setFragmentInContainer(R.id.fragment_container_view, ProfileFragment.class));


        View layout2 = inflater.inflate(R.layout.notifications_fragment, container, false);

        NotificationAdapter notificationAdapter = new NotificationAdapter();

        RecyclerView notificationsRecyclerView = layout2.findViewById(R.id.notifications_recycler_view);

        notificationsRecyclerView.setAdapter(notificationAdapter);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
/*
            TODO: make a layout notifications_fragment.xml
                in notifications_fragment.xml must be:
                    1. Toolbar
                    2. Dynamic List represents notification_card layout

            TODO: make a layout notification_card.xml (see course_card.xml example)
                in notification_card.xml must be:
                    1. Title of Notification
                    2. Description of Notification
                    3. Button to close current Notification

            TODO: bind our notifications_fragment.xml to the onCreateView method of The NotificationsFragment class
                in onCreateView method:
                    1. Bind Toolbar with Back Navigation to The "My Profile" page (to make Back Navigation see SettingsFragment.java class)
                    2. Bind Dynamic List (it must display 3 or more notifications)
                    3. Bind Custom Adapter to Dynamic List (you must write it. For example realization you can see CourseAdapter.java class)
         */