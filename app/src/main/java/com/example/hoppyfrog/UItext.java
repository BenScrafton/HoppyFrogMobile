package com.example.hoppyfrog;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class UItext extends Component
{
    Paint paint;
    String text;

    UItext(GameObject p_gameObject, String text, Paint p_paint)
    {

        id = "UI_TEXT";
        gameObject = p_gameObject;
        paint = p_paint;


    }

    public void SetText(String p_text)
    {
        text = p_text;
    }

    public void SetPaint(Paint p_paint)
    {
        paint = p_paint;
    }

    public void render(Canvas canvas)
    {
        canvas.drawText(text, 0, text.length(), gameObject.position.x, gameObject.position.y, paint);
    }
}

