package ru.myitlesson.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.myitlesson.api.exception.TokenAuthException;
import ru.myitlesson.app.R;
import ru.myitlesson.app.api.Client;
import ru.myitlesson.app.fragment.*;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth();

        setContentView(R.layout.main_activity);

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

    private void auth() {
        client = Client.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        final String token = sharedPreferences.getString(getString(R.string.user_token_key), null);
        final int id = sharedPreferences.getInt(getString(R.string.user_id_key), -1);

        if(client.api() == null && token != null) {
            AlertDialog dialog = new AlertDialog.Builder(this).setMessage(R.string.wait_message).setCancelable(false).show();
            new Thread(() -> authClient(token.split(" ")[1], id, dialog)).start();
        } else if(client.api() == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void authClient(String token, int id, AlertDialog dialog) {
        try {
            client.login(token, id);
            runOnUiThread(() -> setFragmentInContainer(R.id.fragment_container_view, CoursesFragment.class));
        } catch (IOException | TokenAuthException e) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        runOnUiThread(dialog::dismiss);
    }
}
