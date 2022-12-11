package com.example.hoppyfrog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;

public class HighScoreUI extends GameObject
{
    public HighScoreUI(Context context)
    {
        position.x = 500;
        position.y = 240;
        position = new Vector2(550,220);
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

        UItext UI = new UItext(this, "" ,paint, position);
        components.add(UI);

        //-----SCORE_SAVE_SYSTEM_SETUP-----//
        ScoreSaveSystem scoreSaveSystem = new ScoreSaveSystem(this, context);
        components.add(scoreSaveSystem);
    }
}
