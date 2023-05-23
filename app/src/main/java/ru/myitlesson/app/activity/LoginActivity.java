package ru.myitlesson.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ru.myitlesson.app.R;
import ru.myitlesson.app.animation.CharacterByCharacterAnimation;
import ru.myitlesson.app.api.Client;

public class LoginActivity extends Activity {

    private TextInputEditText usernameInputEditText;
    private TextInputEditText passwordInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        new Thread(() -> auth(username.toString(), password.toString())).start();
    }

    private void auth(String username, String password) {
        Client client = Client.getInstance();

        if(!Client.showDialogIfApiError(this, () -> client.login(username, password))) {
            saveToken(client.api().getToken(), client.api().getUserId());

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
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
