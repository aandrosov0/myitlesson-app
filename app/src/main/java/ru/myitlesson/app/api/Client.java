package ru.myitlesson.app.api;

import ru.myitlesson.api.MyItLessonClient;
import ru.myitlesson.api.exception.APIException;

import java.io.IOException;

public class Client {

    public interface OnError {

        void error() throws IOException;
    }

    public interface Execution {

        void run() throws IOException, APIException;
    }

    public interface ErrorCallback {

        void callback(Exception exception);
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

    public static void handleApiException(Execution execution, ErrorCallback callback) {
        try {
            execution.run();
        } catch (IOException|APIException e) {
            callback.callback(e);
        }
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

    public boolean isAuthorized() {
        return api != null;
    }
}
