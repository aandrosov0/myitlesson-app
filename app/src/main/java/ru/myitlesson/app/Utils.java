package ru.myitlesson.app;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import ru.myitlesson.app.repository.RepositoryResult;


import java.io.IOException;

public final class Utils extends AppCompatActivity {

    public static <T> void handleRepositoryError(RepositoryResult.Error<T> error, Context context) {
        Exception exception = error.exception;

        if(exception instanceof IOException) {
            new MaterialAlertDialogBuilder(context)
                    .setMessage(R.string.server_connection_error_message)
                    .show();
        }
    }

    public static void startActivity(Context context, Class<?> activityClass) {
        context.startActivity(new Intent(context, activityClass));
    }

    public static void startActivityWithIntExtra(Context context, Class<?> activityClass, String extra, int extraValue) {
        Intent intent = new Intent(context, activityClass);
        intent.putExtra(extra, extraValue);
        context.startActivity(intent);
    }

    public static void showAlertDialog(Context context, String message) {
        new MaterialAlertDialogBuilder(context).setCancelable(true).setMessage(message).show();
    }

    public static void setFragmentInContainer(int containerId, Class<? extends Fragment> fragmentClass, FragmentManager manager) {
        manager.beginTransaction()
                .replace(containerId, fragmentClass, null)
                .setReorderingAllowed(true)
                .commit();
    }

    public static Fragment setFragmentInContainer(int containerId, Fragment fragment, FragmentManager manager) {
        manager.beginTransaction()
                .replace(containerId, fragment, null)
                .setReorderingAllowed(true)
                .commitNow();

        return manager.findFragmentById(containerId);
    }
}
