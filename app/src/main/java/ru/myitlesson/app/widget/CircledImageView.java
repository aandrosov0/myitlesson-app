package ru.myitlesson.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import ru.myitlesson.app.R;

class CircledImageView extends AppCompatImageView {

    private final Path circlePath = new Path();

    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float strokeWidth;
    private int strokeColor;


    public CircledImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        borderPaint.setStyle(Paint.Style.STROKE);

        TypedArray attrArr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircledImageView, 0, 0);

        try {
            strokeWidth = attrArr.getFloat(R.styleable.CircledImageView_android_strokeWidth, 0);
            strokeColor = attrArr.getColor(R.styleable.CircledImageView_android_strokeColor, 0);
        } finally {
            attrArr.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        borderPaint.setStrokeWidth(strokeWidth);
        borderPaint.setColor(strokeColor);

        circlePath.addCircle(centerX, centerY, centerX, Path.Direction.CW);
        canvas.clipPath(circlePath);

        Drawable drawable = getDrawable();
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);

        canvas.drawCircle(centerX, centerY, centerX, borderPaint);
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public float getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }
}
