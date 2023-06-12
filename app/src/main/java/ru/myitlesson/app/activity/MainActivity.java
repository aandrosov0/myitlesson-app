package ru.myitlesson.app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.myitlesson.app.Utils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.fragment.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Utils.setFragmentInContainer(R.id.fragment_container_view, CoursesFragment.class, getSupportFragmentManager());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(item -> setFragmentByNavigationItem(item.getItemId()));
    }

    public boolean setFragmentByNavigationItem(int itemId) {
        if(itemId == R.id.courses_page) {
            Utils.setFragmentInContainer(R.id.fragment_container_view, CoursesFragment.class, getSupportFragmentManager());
            return true;
        }

        if(itemId == R.id.users_page) {
            Utils.setFragmentInContainer(R.id.fragment_container_view, MyCoursesFragment.class, getSupportFragmentManager());
            return true;
        }

        if(itemId == R.id.profile_page) {
            Utils.setFragmentInContainer(R.id.fragment_container_view, ProfileFragment.class, getSupportFragmentManager());
            return true;
        }

        return false;
    }
}
