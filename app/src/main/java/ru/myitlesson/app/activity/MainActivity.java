package ru.myitlesson.app.activity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.myitlesson.app.AppUtils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.fragment.*;


public class MainActivity extends ClientActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        AppUtils.setFragmentInContainer(R.id.fragment_container_view, CoursesFragment.class, getSupportFragmentManager());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(item -> setFragmentByNavigationItem(item.getItemId()));
    }

    public boolean setFragmentByNavigationItem(int itemId) {
        if(itemId == R.id.courses_item) {
            AppUtils.setFragmentInContainer(R.id.fragment_container_view, CoursesFragment.class, getSupportFragmentManager());
            return true;
        }

        if(itemId == R.id.users_item) {
            AppUtils.setFragmentInContainer(R.id.fragment_container_view, UsersFragment.class, getSupportFragmentManager());
            return true;
        }

        if(itemId == R.id.profile_item) {
            AppUtils.setFragmentInContainer(R.id.fragment_container_view, ProfileFragment.class, getSupportFragmentManager());
            return true;
        }

        return false;
    }
}
