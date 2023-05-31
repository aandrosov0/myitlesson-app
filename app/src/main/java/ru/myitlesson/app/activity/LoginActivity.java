package ru.myitlesson.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ru.myitlesson.app.AppUtils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.animation.CharacterByCharacterAnimation;
import ru.myitlesson.app.api.ApiExecutor;
import ru.myitlesson.app.api.Client;

import java.io.IOException;

public class LoginActivity extends Activity {

    private TextInputEditText usernameInputEditText;
    private TextInputEditText passwordInputEditText;

    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = Client.getInstance();
        new ApiExecutor(this::authFromPreferences, exception -> AppUtils.handleException(exception, this)).start();

        setContentView(R.layout.login_activity);
        final TextInputLayout usernameInputLayout = findViewById(R.id.username_input_layout);
        final TextInputLayout passwordInputLayout = findViewById(R.id.password_input_layout);

        usernameInputEditText = findViewById(R.id.username_input_edit_text);
        usernameInputEditText.setOnClickListener(view -> usernameInputLayout.setError(""));

        passwordInputEditText = findViewById(R.id.password_input_edit_text);
        passwordInputEditText.setOnClickListener(view -> passwordInputLayout.setError(""));

        final Button okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(view -> login(usernameInputLayout, passwordInputLayout));

        final Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(view -> finish());

        int loginAnimDelay = getResources().getInteger(R.integer.character_by_character_delay_fast);
        final TextView loginTextView = findViewById(R.id.login_message_text_view);
        CharSequence loginMessage = loginTextView.getText();

        CharacterByCharacterAnimation characterAnimation = new CharacterByCharacterAnimation(loginTextView, loginMessage);
        characterAnimation.setDelay(loginAnimDelay);
        characterAnimation.run();
    }

    private void login(TextInputLayout usernameInputLayout, TextInputLayout passwordInputLayout) {
        CharSequence username = usernameInputEditText.getText();
        CharSequence password = passwordInputEditText.getText();

        if((username == null) || (username.length() == 0)) {
            usernameInputLayout.setError(getString(R.string.invalid_username));
            return;
        }

        if((password == null) || (password.length() < 8)) {
            passwordInputLayout.setError(getString(R.string.invalid_password));
            return;
        }

        new ApiExecutor(() -> auth(username.toString(), password.toString()), exception -> AppUtils.handleException(exception, this)).start();
    }

    private void auth(String username, String password) throws IOException {
        client.login(username, password);
        saveToken(client.api().getToken(), client.api().getUserId());
        AppUtils.startActivity(this, MainActivity.class);
    }

    private void authFromPreferences() throws IOException {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(getString(R.string.user_token_key), null);
        final int id = sharedPreferences.getInt(getString(R.string.user_id_key), -1);

        if(token != null && id != -1) {
            client.login(token.split(" ")[1], id);
            AppUtils.startActivity(this, MainActivity.class);
        }
    }

    private void saveToken(String token, int id) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        sharedPreferences.edit()
                .putString(getString(R.string.user_token_key), token)
                .putInt(getString(R.string.user_id_key), id)
                .apply();
    }
}
