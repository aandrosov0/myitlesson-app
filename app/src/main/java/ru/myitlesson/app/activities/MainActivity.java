package ru.myitlesson.app.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.myitlesson.app.R;
import ru.myitlesson.app.fragments.*;

public class MainActivity extends AppCompatActivity {

    public static final String TOKEN_EXTRA = "token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(item -> setFragment(item.getItemId()));
    }

    private boolean setFragment(int itemId) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true);

        if(itemId == R.id.courses_item) {
            transaction.replace(R.id.fragment_container_view, CoursesFragment.class, null).commit();
            return true;
        }

        if(itemId == R.id.users_item) {
            transaction.replace(R.id.fragment_container_view, UsersFragment.class, null).commit();
            return true;
        }

        if(itemId == R.id.profile_item) {
            transaction.replace(R.id.fragment_container_view, ProfileFragment.class, null).commit();
            return true;
        }

        return false;
    }
}
