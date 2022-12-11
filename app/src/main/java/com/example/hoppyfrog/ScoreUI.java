package com.example.hoppyfrog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;

public class ScoreUI extends GameObject
{
    private VerticalDistanceRecorder recorder;

    public ScoreUI(GameObject recordObject, Context context, HighScoreUI highScoreUI)
    {
        position = new Vector2(550,300);

        //--------------------COMPONENT_SETUP--------------------//

        //-----UI_TEXT_SETUP-----//
        Paint paint =  new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(130.0f);

        if(Build.VERSION.SDK_INT > 26)
        {
            Typeface tf = context.getResources().getFont(R.font.press_start_2);
            paint.setTypeface(tf);
        }

        UItext UI = new UItext(this, "Score: " ,paint, position);
        components.add(UI);

        //-----VERTICAL_DISTACNCE_RECORD_SETUP-----//
        recorder = new VerticalDistanceRecorder(this, recordObject, highScoreUI);
        components.add(recorder);
    }

    public void GameOver()
    {
        this.<UItext>getComponentOfType("UI_TEXT").SetPosition(new Vector2(550, 475));
    }

    public void Reset()
    {
        this.<UItext>getComponentOfType("UI_TEXT").SetPosition(new Vector2(550, 220));
        recorder.updatedHighScore = false;
    }

    @Override
    public void update()
    {
        super.update();
    }
}
