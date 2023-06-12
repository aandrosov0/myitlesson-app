package ru.myitlesson.app.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ru.myitlesson.api.ApiError;
import ru.myitlesson.api.MyItLessonClient;
import ru.myitlesson.app.App;
import ru.myitlesson.app.Utils;
import ru.myitlesson.app.R;
import ru.myitlesson.app.animation.CharacterByCharacterAnimation;
import ru.myitlesson.app.repository.LoginRepository;
import ru.myitlesson.app.repository.RepositoryResult;

public class LoginActivity extends Activity {

    private TextInputEditText usernameInputEditText;
    private TextInputEditText passwordInputEditText;

    private final LoginRepository loginRepository = App.getLoginRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginFromPreferences();

        setContentView(R.layout.activity_login);
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
            usernameInputLayout.setError(getString(R.string.invalid_username_message));
            return;
        }

        if((password == null) || (password.length() < 8)) {
            passwordInputLayout.setError(getString(R.string.invalid_password_message));
            return;
        }

        loginRepository.makeLoginRequest(username.toString(), password.toString(), this::onLoginRequestComplete);
    }

    private void onLoginRequestComplete(RepositoryResult<MyItLessonClient> result) {
        if(result instanceof RepositoryResult.Success) {
            MyItLessonClient client = ((RepositoryResult.Success<MyItLessonClient>) result).data;
            ApiError error = client.getError();

            if(error != null && error.getCode() == ApiError.Code.AUTHORIZATION) {
                runOnUiThread(() -> Utils.showAlertDialog(this, getString(R.string.incorrect_data_message)));
                return;
            } else if(error != null && error.getCode() == ApiError.Code.NOT_FOUND) {
                runOnUiThread(() -> Utils.showAlertDialog(this, error.getMessage()));
                return;
            }

            saveUserCredentials();
            Utils.startActivity(this, MainActivity.class);
        } else {
            runOnUiThread(() -> Utils.handleRepositoryError((RepositoryResult.Error<MyItLessonClient>) result, this));
        }
    }

    private void saveUserCredentials() {
        CharSequence username = usernameInputEditText.getText();
        CharSequence password = passwordInputEditText.getText();

        if(username == null || password == null || username.length() == 0 || password.length() == 0) {
            return;
        }

        getPreferences(MODE_PRIVATE).edit()
                .putString(getString(R.string.username_key), username.toString())
                .putString(getString(R.string.password_key), password.toString())
                .apply();
    }

    private void loginFromPreferences() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        String username = preferences.getString(getString(R.string.username_key), null);
        String password = preferences.getString(getString(R.string.password_key), null);

        if(username == null || password == null) {
            return;
        }

        loginRepository.makeLoginRequest(username, password, this::onLoginRequestComplete);
    }
}
