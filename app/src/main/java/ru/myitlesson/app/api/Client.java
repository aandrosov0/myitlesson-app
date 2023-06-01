package ru.myitlesson.app.api;

import ru.myitlesson.api.MyItLessonClient;
import ru.myitlesson.api.entity.Entity;
import ru.myitlesson.api.exception.APIException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client {

    public interface Execution {

        void run() throws IOException, APIException;
    }

    public interface ErrorCallback {

        void callback(Exception exception);
    }

    public interface GetEntity<T extends Entity> {

        T get(int id) throws IOException;
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

    public static MyItLessonClient getApi() {
        if(CLIENT == null) {
            return null;
        }

        return CLIENT.api;
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

    public <T extends Entity> List<T> getEntities(List<Integer> ids, GetEntity<T> getEntity) throws IOException {
        List<T> entities = new ArrayList<>();

        for(int id : ids) {
            entities.add(getEntity.get(id));
        }

        return entities;
    }
}
