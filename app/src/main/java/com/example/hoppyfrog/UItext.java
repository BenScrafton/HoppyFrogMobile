package com.example.hoppyfrog;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class UItext extends Component
{
    private Vector2 position;
    private Paint paint;
    public String text;

    UItext(GameObject p_gameObject, String p_text, Paint p_paint, Vector2 p_position)
    {
        id = "UI_TEXT";
        componentType = ComponentType.UI_TEXT;
        text = p_text;
        gameObject = p_gameObject;
        paint = p_paint;
        position = p_position;
    }

    public void SetText(String p_text)
    {
        text = p_text;
    }

    public void SetPaint(Paint p_paint)
    {
        paint = p_paint;
    }

    public void SetPosition(Vector2 p_position)
    {
        position = p_position;
    }

    public void render(Canvas canvas)
    {
        canvas.drawText(text, 0, text.length(), position.x, position.y, paint);
    }
}

