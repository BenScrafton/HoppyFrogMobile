package com.example.hoppyfrog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class ScoreSaveSystem extends Component
{
    private Context context;
    private static final String SHARED_PREFS = "sharedPrefs_score";
    private int highScore = 0;
    private UItext highScoreUIText;

    public ScoreSaveSystem(GameObject p_gameObject, Context p_context)
    {
        id = "SCORE_SAVE_SYSTEM";

        componentType = ComponentType.SCORE_SAVE_SYSTEM;
        gameObject = p_gameObject;
        context = p_context;
        highScoreUIText = gameObject.<UItext>getComponentOfType(ComponentType.UI_TEXT);

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        LoadData();
    }

    public void updateHighScore(int p_highScore)
    {
        if(p_highScore > highScore)
        {
            highScore = p_highScore;
            SaveData();
        }
    }

    public void SaveData()
    {
        //CHANGE SHARED PREFS
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("SCORE", highScore);
        editor.apply();
        highScoreUIText.text = Integer.toString(highScore);
    }

    void LoadData()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        highScore = sharedPreferences.getInt("SCORE", 0);
        highScoreUIText.text = Integer.toString(highScore);
    }

    public int GetHighScore()
    {
        return highScore;
    }
}
