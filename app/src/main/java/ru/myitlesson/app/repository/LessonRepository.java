package ru.myitlesson.app.repository;

import ru.myitlesson.api.entity.LessonEntity;
import ru.myitlesson.api.entity.ModuleEntity;
import ru.myitlesson.app.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LessonRepository {

    public void get(int id, RepositoryCallback<LessonEntity> callback) {
        App.getExecutorService().execute(() -> {
            try {
                LessonEntity lesson = App.getClient().lesson().get(id);
                callback.onComplete(new RepositoryResult.Success<>(lesson));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }

    public void getModuleLessons(int moduleId, RepositoryCallback<List<LessonEntity>> callback) {
        App.getExecutorService().execute(() -> {
            try {
                ModuleEntity module = App.getClient().module().get(moduleId);
                List<LessonEntity> lessons = new ArrayList<>();

                for(int lessonId : module.getLessons()) {
                    lessons.add(App.getClient().lesson().get(lessonId));
                }

                callback.onComplete(new RepositoryResult.Success<>(lessons));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }

    public void add(LessonEntity lesson, int moduleId, RepositoryCallback<Integer> callback) {
        App.getExecutorService().execute(() -> {
            try {
                int id = App.getClient().lesson().add(lesson, moduleId);
                callback.onComplete(new RepositoryResult.Success<>(id));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }

    public void delete(int id, RepositoryCallback<Object> callback) {
        App.getExecutorService().execute(() -> {
            try {
                App.getClient().lesson().delete(id);
                callback.onComplete(new RepositoryResult.Success<>(null));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }
}
