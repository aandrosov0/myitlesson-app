package ru.myitlesson.app;

import ru.myitlesson.api.MyItLessonClient;
import ru.myitlesson.app.repository.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static final int MAX_THREADS_IN_THREAD_POOL = 4;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS_IN_THREAD_POOL);

    private static final LoginRepository loginRepository = new LoginRepository();

    private static final CourseRepository courseRepository = new CourseRepository();

    private static final UserRepository userRepository = new UserRepository();

    private static final ModuleRepository moduleRepository = new ModuleRepository();

    private static final LessonRepository lessonRepository = new LessonRepository();

    private static MyItLessonClient client;

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static MyItLessonClient getClient() {
        return client;
    }

    public static void setClient(MyItLessonClient client) {
        App.client = client;
    }

    public static LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public static CourseRepository getCourseRepository() {
        return courseRepository;
    }

    public static ModuleRepository getModuleRepository() {
        return moduleRepository;
    }

    public static LessonRepository getLessonRepository() {
        return lessonRepository;
    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }
}
