package ru.myitlesson.app.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import ru.myitlesson.api.MyItLessonClient;

import java.io.IOException;

public class Client {

    public interface OnError {

        void error() throws IOException;
    }

    private static Client CLIENT;

    private MyItLessonClient api;

    private Client() {

    }

    public static Client getInstance() {
        if(CLIENT == null) {
            CLIENT = new Client();
        }

        return CLIENT;
    }

    public static boolean showDialogIfApiError(Context context, OnError onError) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);

        try {
            onError.error();
        } catch (Exception exception) {
            builder.setCancelable(true).setMessage(exception.toString());
            new Handler(Looper.getMainLooper()).post(builder::show);
            return true;
        }

        return false;
    }

    public MyItLessonClient api() {
        return api;
    }

    public void login(String username, String password) throws IOException {
        api = new MyItLessonClient(username, password);
    }

    public void login(String token, int id) throws IOException {
        api = new MyItLessonClient(token, id);
    }
}
