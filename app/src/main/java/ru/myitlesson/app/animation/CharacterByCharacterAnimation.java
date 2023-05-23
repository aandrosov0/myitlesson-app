package ru.myitlesson.app.animation;

import android.os.Handler;
import android.widget.TextView;

public class CharacterByCharacterAnimation implements Runnable {

    private final TextView textView;
    private final CharSequence text;
    private final Handler handler;

    private int index;
    private long delay;

    public CharacterByCharacterAnimation(TextView textView, CharSequence text) {
        this.handler = new Handler();
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
