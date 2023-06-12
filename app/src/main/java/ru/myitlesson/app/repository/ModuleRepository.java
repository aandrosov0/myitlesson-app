package ru.myitlesson.app.repository;

import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.api.entity.ModuleEntity;
import ru.myitlesson.app.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ModuleRepository {

    public void getCourseModules(int courseId, RepositoryCallback<List<ModuleEntity>> callback) {
        App.getExecutorService().execute(() -> {
            try {
                CourseEntity course = App.getClient().course().get(courseId);
                List<ModuleEntity> modules = new ArrayList<>();
                for(int moduleId : course.getModules()) {
                    modules.add(App.getClient().module().get(moduleId));
                }

                callback.onComplete(new RepositoryResult.Success<>(modules));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }

    public void add(ModuleEntity module, int courseId, RepositoryCallback<Integer> callback) {
        App.getExecutorService().execute(() -> {
            try {
                int id = App.getClient().module().add(module, courseId);
                callback.onComplete(new RepositoryResult.Success<>(id));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }

    public void delete(int id, RepositoryCallback<Object> callback) {
        App.getExecutorService().execute(() -> {
            try {
                App.getClient().module().delete(id);
                callback.onComplete(new RepositoryResult.Success<>(null));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }
}
