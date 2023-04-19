package ru.myitlesson.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotificationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
