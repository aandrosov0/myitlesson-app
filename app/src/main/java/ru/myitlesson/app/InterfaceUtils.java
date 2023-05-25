package ru.myitlesson.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import ru.myitlesson.api.exception.TokenAuthException;
import ru.myitlesson.app.activity.LoginActivity;

import java.net.ConnectException;

public final class InterfaceUtils extends AppCompatActivity {

    public static void handleException(Exception exception, Context context) {
        String message = exception.getMessage();

        if(exception instanceof ConnectException) {
            message = "Failed connection to the Internet\n";
        }

        String finalMessage = message;

        new Handler(Looper.getMainLooper()).post(() -> makeAlertDialog(context, finalMessage, dialog -> {
            if((exception instanceof TokenAuthException) && !(context instanceof LoginActivity)) {
                startActivity(context, LoginActivity.class);
            }
        }));
    }

    public static void startActivity(Context context, Class<?> activityClass) {
        context.startActivity(new Intent(context, activityClass));
    }

    public static void startActivityWithIntExtra(Context context, Class<?> activityClass, String extra, int extraValue) {
        Intent intent = new Intent(context, activityClass);
        intent.putExtra(extra, extraValue);
        context.startActivity(intent);
    }

    public static void makeAlertDialog(Context context, String message, DialogInterface.OnCancelListener cancelListener) {
        new MaterialAlertDialogBuilder(context).setCancelable(true).setMessage(message).setOnCancelListener(cancelListener).show();
    }
}
