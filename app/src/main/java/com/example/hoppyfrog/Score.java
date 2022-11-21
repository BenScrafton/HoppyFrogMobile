package com.example.hoppyfrog;

import android.graphics.Color;
import android.graphics.Paint;

public class Score extends GameObject
{
    public Score()
    {
        position = new Vector2(0,0);


        //--------------------COMPONENT_SETUP--------------------//

        //-----UI_TEXT_SETUP-----//
        Paint paint =  new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(120.0f);

        UItext UI = new UItext(this, "Score: " ,paint);
        components.add(UI);
    }

    @Override
    public void update()
    {
        super.update();
    }
}
