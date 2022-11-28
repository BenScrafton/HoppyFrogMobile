package com.example.hoppyfrog;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class App
{
    public static void Restart(Activity activity, Intent intent)
    {
        activity.finish();

        Intent intentNew = new Intent(activity, GameActivity.class);
        activity.startActivity(intent);
    }
}
