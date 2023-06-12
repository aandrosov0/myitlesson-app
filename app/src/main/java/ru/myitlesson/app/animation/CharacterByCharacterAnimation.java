package ru.myitlesson.app.animation;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.core.os.HandlerCompat;

public class CharacterByCharacterAnimation implements Runnable {

    private final TextView textView;
    private final CharSequence text;
    private final Handler handler;

    private int index;
    private long delay;

    public CharacterByCharacterAnimation(TextView textView, CharSequence text) {
        this.handler = HandlerCompat.createAsync(Looper.getMainLooper());
        this.textView = textView;
        this.text = text;
        this.textView.setText("");
    }

    @Override
    public void run() {
        if(index < text.length()) {
            textView.setText(text.subSequence(0, ++index));
            handler.postDelayed(this, delay);
        }
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
