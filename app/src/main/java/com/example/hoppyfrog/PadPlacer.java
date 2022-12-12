package com.example.hoppyfrog;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PadPlacer
{
    private LillyPad[] lillyPads = new LillyPad[10];
    private float minDisplacement;
    private float maxDisplacement;
    private Vector2 startPos;
    private int frontIndex = 9;

    public PadPlacer(Context context, List<GameObject> p_gameObjects, Vector2 p_startPos, float p_minDisplacement, float p_maxDisplacement)
    {
        startPos = p_startPos;
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
            p_gameObjects.add(l);

            curDisplacement.y -= 500;
            lillyPads[i-1] = l;
        }
    }

    public void Reset()
    {
        Vector2 curDisplacement = new Vector2(0,0);

        int index = 0;
        for (LillyPad l : lillyPads)
        {
            float xDisplace = 0;
            if(index != 0)
            {
                xDisplace = (float) Math.floor(Math.random()*(maxDisplacement-minDisplacement+1)+minDisplacement);
            }

            l.position = new Vector2(startPos.x + xDisplace, startPos.y + curDisplacement.y);
            l.<BoxCollider>getComponentOfType(ComponentType.BOX_COLLIDER).UpdateBounds();

            curDisplacement.y -= 500;
            index++;
        }

        frontIndex = lillyPads.length - 1;
    }

    public void update()
    {
        int index = 0;
        for (LillyPad l : lillyPads)
        {
            float distFromCamera = l.position.y - GameView.mainCamera.position.y;

            if(distFromCamera > 3000)
            {
                l.position.y = lillyPads[frontIndex].position.y - 500;
                l.position.x = startPos.x + (float) Math.floor(Math.random()*(maxDisplacement-minDisplacement+1)+minDisplacement);
                frontIndex = index;
            }
            index++;
        }
    }
}
