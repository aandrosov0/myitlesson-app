package ru.myitlesson.app.repository;

import ru.myitlesson.api.MyItLessonClient;
import ru.myitlesson.app.App;

import java.io.IOException;

public class LoginRepository {

    public void makeLoginRequest(String username, String password, RepositoryCallback<MyItLessonClient> callback) {
        App.getExecutorService().execute(() -> {
            try {
                MyItLessonClient client = new MyItLessonClient(username, password);
                callback.onComplete(new RepositoryResult.Success<>(client));
                App.setClient(client);
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }
}
