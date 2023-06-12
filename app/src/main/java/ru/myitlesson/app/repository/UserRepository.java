package ru.myitlesson.app.repository;

import ru.myitlesson.api.entity.UserEntity;
import ru.myitlesson.app.App;

import java.io.IOException;

public class UserRepository {

    public void get(int id, RepositoryCallback<UserEntity> callback) {
        App.getExecutorService().execute(() -> {
            try {
                UserEntity user = App.getClient().user().get(id);
                callback.onComplete(new RepositoryResult.Success<>(user));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }
}
