package com.example.hoppyfrog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

public class ScoreUI extends GameObject
{
    public ScoreUI(GameObject recordObject, Context context, HighScoreUI highScoreUI)
    {
        position = new Vector2(550,240);

        //--------------------COMPONENT_SETUP--------------------//

        //-----UI_TEXT_SETUP-----//
        Paint paint =  new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(170.0f);

        UItext UI = new UItext(this, "Score: " ,paint, position);
        components.add(UI);

        //-----VERTICAL_DISTACNCE_RECORD_SETUP-----//
        VerticalDistanceRecorder recorder = new VerticalDistanceRecorder(this, recordObject, highScoreUI);
        components.add(recorder);
    }

    public void GameOver()
    {
        this.<UItext>getComponentOfType("UI_TEXT").SetPosition(new Vector2(550, 465));
    }

    public void Reset()
    {
        this.<UItext>getComponentOfType("UI_TEXT").SetPosition(new Vector2(550, 210));
    }

    @Override
    public void update()
    {
        super.update();
    }
}
