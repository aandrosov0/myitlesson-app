package ru.myitlesson.app.repository;

import ru.myitlesson.api.MyItLessonClient;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.api.entity.UserEntity;
import ru.myitlesson.app.App;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {

    public void get(int id, RepositoryCallback<CourseEntity> callback) {
        App.getExecutorService().execute(() -> {
            try {
                CourseEntity course = App.getClient().course().get(id);
                callback.onComplete(new RepositoryResult.Success<>(course));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }

    public void getAmount(int limit, int offset, RepositoryCallback<List<CourseEntity>> callback) {
        App.getExecutorService().execute(() -> {
            try {
                List<CourseEntity> courses = App.getClient().course().getAmount(limit, offset);
                callback.onComplete(new RepositoryResult.Success<>(courses));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }

    public void getUserCourses(int userId, RepositoryCallback<List<CourseEntity>> callback) {
        App.getExecutorService().execute(() -> {
            MyItLessonClient client = App.getClient();

            try {
                UserEntity user = client.user().get(userId);
                List<CourseEntity> courses = new ArrayList<>();

                for(int courseId : user.getCourses()) {
                    courses.add(client.course().get(courseId));
                }

                callback.onComplete(new RepositoryResult.Success<>(courses));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }

    public void getUserAuthoredCourses(int userId, RepositoryCallback<List<CourseEntity>> callback) {
        App.getExecutorService().execute(() -> {
            MyItLessonClient client = App.getClient();

            try {
                UserEntity user = client.user().get(userId);
                List<CourseEntity> courses = new ArrayList<>();

                for(int courseId : user.getAuthoredCourses()) {
                    courses.add(client.course().get(courseId));
                }

                callback.onComplete(new RepositoryResult.Success<>(courses));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }

    public void getImage(CourseEntity course, RepositoryCallback<File> callback) {
        App.getExecutorService().execute(() -> {
            try {
                File image = App.getClient().course().getImage(course);
                callback.onComplete(new RepositoryResult.Success<>(image));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }

    public void add(CourseEntity course, int authorId, RepositoryCallback<Integer> callback) {
        App.getExecutorService().execute(() -> {
            try {
                int id = App.getClient().course().add(course, authorId);
                callback.onComplete(new RepositoryResult.Success<>(id));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }

    public void delete(int id, RepositoryCallback<Integer> callback) {
        App.getExecutorService().execute(() -> {
            try {
                App.getClient().course().delete(id);
                callback.onComplete(new RepositoryResult.Success<>(id));
            } catch (IOException e) {
                callback.onComplete(new RepositoryResult.Error<>(e));
            }
        });
    }
}
