package ru.myitlesson.app.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.myitlesson.api.MyItLessonClient;
import ru.myitlesson.api.entity.UserEntity;
import ru.myitlesson.app.AppUtils;
import ru.myitlesson.app.api.ApiExecutor;
import ru.myitlesson.app.api.Client;

import java.io.IOException;

public abstract class ClientActivity extends AppCompatActivity {

    protected Client client;

    protected UserEntity user;

    protected MyItLessonClient api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = Client.getInstance();
        api = client.api();

        new ApiExecutor(this::onLoadApiData, exception -> AppUtils.handleException(exception, this)).start();
    }

    protected void onLoadApiData() throws IOException {
        user = api.user().get(api.getUserId());
    }

    public Client getClient() {
        return client;
    }

    public UserEntity getUser() {
        return user;
    }

    public MyItLessonClient getApi() {
        return api;
    }
}
