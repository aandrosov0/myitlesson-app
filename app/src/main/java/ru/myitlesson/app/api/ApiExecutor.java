package ru.myitlesson.app.api;

public class ApiExecutor extends Thread {

    private final Client.Execution execution;

    private final Client.ErrorCallback errorCallback;

    public ApiExecutor(Client.Execution execution, Client.ErrorCallback errorCallback) {
        this.execution = execution;
        this.errorCallback = errorCallback;
    }

    @Override
    public void run() {
        Client.handleApiException(execution, errorCallback);
    }
}
