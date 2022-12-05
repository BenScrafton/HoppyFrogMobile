package com.example.hoppyfrog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class ScoreUI extends GameObject
{
    VerticalDistanceRecorder recorder;

    public ScoreUI(GameObject recordObject, Context context, HighScoreUI highScoreUI)
    {
        position = new Vector2(550,240);

        //--------------------COMPONENT_SETUP--------------------//

        //-----UI_TEXT_SETUP-----//
        Paint paint =  new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(170.0f);

        //Typeface tf = Typeface.createFromAsset(context.getAssets(), "font/press_start_2.ttf");
        //paint.setTypeface(tf);

        UItext UI = new UItext(this, "Score: " ,paint, position);
        components.add(UI);

        //-----VERTICAL_DISTACNCE_RECORD_SETUP-----//
        recorder = new VerticalDistanceRecorder(this, recordObject, highScoreUI);
        components.add(recorder);
    }

    public void GameOver()
    {
        this.<UItext>getComponentOfType("UI_TEXT").SetPosition(new Vector2(550, 465));
    }

    public void Reset()
    {
        this.<UItext>getComponentOfType("UI_TEXT").SetPosition(new Vector2(550, 210));
        recorder.updatedHighScore = false;
    }

    @Override
    public void update()
    {
        super.update();
    }
}
