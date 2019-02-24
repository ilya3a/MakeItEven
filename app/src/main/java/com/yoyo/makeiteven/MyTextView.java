package com.yoyo.makeiteven;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyTextView extends TextView {
    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressLint("DrawAllocation") Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.primaryTextColor));
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/sagesans_regular.ttf");
        paint.setTypeface(custom_font);

        Rect rect = new Rect();
        paint.getTextBounds("", 0, "".length(), rect);

        canvas.drawText("", getWidth() - rect.width(), getHeight() - 50, paint);

    }
}