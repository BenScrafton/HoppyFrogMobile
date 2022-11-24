package com.example.hoppyfrog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.hardware.SensorEventListener;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity
{
    GameView gameView;
    AppCompatActivity appCompatActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this, this, this);
        setContentView(gameView);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        gameView.pause();
    }

    public void Restart()
    {
        App.Restart(this);
    }
}