package ru.myitlesson.app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.myitlesson.app.R;
import ru.myitlesson.app.fragment.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        setFragmentInContainer(R.id.fragment_container_view, CoursesFragment.class);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(item -> setFragmentByNavigationItem(item.getItemId()));
    }

    public void setFragmentInContainer(int containerId, Class<? extends Fragment> fragmentClass) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragmentClass, null)
                .setReorderingAllowed(true)
                .commit();
    }

    private boolean setFragmentByNavigationItem(int itemId) {
        if(itemId == R.id.courses_item) {
            setFragmentInContainer(R.id.fragment_container_view, CoursesFragment.class);
            return true;
        }

        if(itemId == R.id.users_item) {
            setFragmentInContainer(R.id.fragment_container_view, UsersFragment.class);
            return true;
        }

        if(itemId == R.id.profile_item) {
            setFragmentInContainer(R.id.fragment_container_view, ProfileFragment.class);
            return true;
        }

        return false;
    }
}
