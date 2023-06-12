package ru.myitlesson.app.repository;

public interface RepositoryCallback<T> {

    void onComplete(RepositoryResult<T> result);
}
