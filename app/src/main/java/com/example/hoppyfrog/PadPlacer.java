package com.example.hoppyfrog;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class PadPlacer
{
    LillyPad[] lillyPads = new LillyPad[10];
    float minDisplacement;


    public PadPlacer(Context context, GameObject[] p_gameObjects, Vector2 startPos, float p_minDisplacement, float maxDisplacement)
    {
        Vector2 curDisplacement = startPos;
        minDisplacement = p_minDisplacement;

        for (int i = 1; i <= 10; i++)
        {
            LillyPad l = new LillyPad(context, new Vector2(startPos.x + curDisplacement.x, startPos.y));
            p_gameObjects[i] = l;
            curDisplacement.x += minDisplacement;

            lillyPads[i-1] = l;
        }
    }

    public void update()
    {
        for (LillyPad l : lillyPads)
        {
            if(l.position.x < -500)
            {
                Log.e("HELP ME ", "DISPLACE");
                l.position.x = lillyPads[9].position.x + minDisplacement;
            }
        }
    }
}
