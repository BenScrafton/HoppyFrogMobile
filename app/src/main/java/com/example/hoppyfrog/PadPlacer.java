package com.example.hoppyfrog;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class PadPlacer
{
    LillyPad[] lillyPads = new LillyPad[10];
    float minDisplacement;
    float maxDisplacement;

    int frontIndex = 9;

    public PadPlacer(Context context, GameObject[] p_gameObjects, Vector2 startPos, float p_minDisplacement, float p_maxDisplacement)
    {
        Vector2 curDisplacement = new Vector2(0,0);
        minDisplacement = p_minDisplacement;
        maxDisplacement = p_maxDisplacement;

        for (int i = 1; i <= 10; i++)
        {
            float xDisplace = 0;
            if(i != 1)
            {
                xDisplace = (float) Math.floor(Math.random()*(maxDisplacement-minDisplacement+1)+minDisplacement);
            }

            LillyPad l = new LillyPad(context, new Vector2(startPos.x + xDisplace, startPos.y + curDisplacement.y));
            p_gameObjects[i] = l;

            curDisplacement.y -= 500;
            lillyPads[i-1] = l;
        }
    }

    public void update()
    {
        int index = 0;
        for (LillyPad l : lillyPads)
        {
            if(l.position.y > 5000)
            {

                Log.e("HELL: ", "KILL ME");
                l.position.y = lillyPads[frontIndex].position.y - 500;
                //l.position.x =  (float) Math.floor(Math.random()*(maxDisplacement-minDisplacement+1)+minDisplacement);
                frontIndex = index;
            }
            index++;
        }
    }
}
