package com.example.hoppyfrog;

import android.graphics.Color;
import android.graphics.Paint;

public class Score extends GameObject
{
    public Score(GameObject recordObject)
    {
        position = new Vector2(10,100);

        //--------------------COMPONENT_SETUP--------------------//

        //-----UI_TEXT_SETUP-----//
        Paint paint =  new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(120.0f);

        UItext UI = new UItext(this, "Score: " ,paint);
        components.add(UI);

        //-----VERTICAL_DISTACNCE_RECORD_SETUP-----//
        VerticalDistanceRecorder recorder = new VerticalDistanceRecorder(this, recordObject);
        components.add(recorder);
    }

    @Override
    public void update()
    {
        super.update();
    }
}
