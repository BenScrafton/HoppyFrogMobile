package com.example.hoppyfrog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Animation
{
    Bitmap spriteSheet;
    int numFrames;
    int spriteWidth;
    int spriteHeight;

    float timeBetweenFrames;
    boolean playOnce = false;

    public Animation(Context context, int p_spriteSheetId, int p_numFrames,
                     int p_spriteWidth, int p_spriteHeight, float p_scale, float p_timeBetweenFrames, boolean p_playOnce)
    {
        spriteWidth = p_spriteWidth;
        spriteHeight = p_spriteHeight;
        numFrames = p_numFrames;
        timeBetweenFrames = p_timeBetweenFrames;

        playOnce = p_playOnce;

        spriteSheet = BitmapFactory.decodeResource(context.getResources(), p_spriteSheetId);
        spriteSheet = Bitmap.createScaledBitmap(spriteSheet, spriteWidth * numFrames, spriteHeight, false);
    }
}
