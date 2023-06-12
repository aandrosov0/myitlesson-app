package ru.myitlesson.app.repository;

public class RepositoryResult<T> {

    private RepositoryResult() {

    }

    public static final class Success<T> extends RepositoryResult<T> {
        public T data;

        public Success(T data) {
            this.data = data;
        }
    }

    public static final class Error<T> extends RepositoryResult<T> {

        public java.lang.Exception exception;

        public Error(java.lang.Exception exception) {
            this.exception = exception;
        }
    }
}
