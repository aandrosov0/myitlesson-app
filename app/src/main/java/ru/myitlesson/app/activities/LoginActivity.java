package ru.myitlesson.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ru.myitlesson.app.R;
import ru.myitlesson.app.animation.CharacterByCharacterAnimation;

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

        String token = generateToken(username, password);

//        TODO: get token from API and send it to the MainActivity.java class
//        if(token == null) {
//            usernameInputLayout.setError(" ");
//            passwordInputLayout.setError(getString(R.string.incorrect_data));
//            return;
//        }

        sendToken(token);
    }

    private String generateToken(CharSequence username, CharSequence password) {
        //TODO: Get token from API
        return "";
    }

    private void sendToken(String token) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.TOKEN_EXTRA, token);
        startActivity(intent);
    }
}
