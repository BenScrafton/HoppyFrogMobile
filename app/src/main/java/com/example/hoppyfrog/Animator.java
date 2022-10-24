package com.example.hoppyfrog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceHolder;

public class Animator extends Component
{
    Bitmap spriteSheet;
    int numFrames;
    int spriteWidth;
    int spriteHeight;
    int currentFrame = 0;
    float scale;

    float timeBetweenFrames;
    float timer = 0.0f;

    private Rect frameToDraw;
    private RectF whereToDraw;

    public Animator(Context context, GameObject p_gameObject, int p_spriteSheetId, int p_numFrames,
                    int p_spriteWidth, int p_spriteHeight, float p_scale, float p_timeBetweenFrames)
    {
        id = "ANIMATOR";

        gameObject = p_gameObject;

        spriteWidth = p_spriteWidth;
        spriteHeight = p_spriteHeight;
        numFrames = p_numFrames;
        scale = p_scale;
        timeBetweenFrames = p_timeBetweenFrames;

        spriteSheet = BitmapFactory.decodeResource(context.getResources(), p_spriteSheetId);
        spriteSheet = Bitmap.createScaledBitmap(spriteSheet, spriteWidth * numFrames, spriteHeight, false);

        frameToDraw = new Rect(0,0,spriteWidth,spriteHeight);
        whereToDraw = new RectF(100, 100, 200, 200);
    }

    @Override
    public void update()
    {
        super.update();
        updateAnimFrame();
    }

    void updateAnimFrame()
    {
        timer += Time.getInstance().deltaTime;

        if(timer >= timeBetweenFrames)
        {
            //Next Frame
            if(currentFrame < numFrames - 1)
            {
                currentFrame++;
            }
            else
            {
                currentFrame = 0;
            }

            timer = 0.0f;
            frameToDraw.left = currentFrame * spriteWidth;
            frameToDraw.right = frameToDraw.left + spriteWidth;
        }
    }

    public void draw(Canvas canvas)
    {
        whereToDraw.set(gameObject.position.x, gameObject.position.y, gameObject.position.x + scale, gameObject.position.y + scale);
        canvas.drawBitmap(spriteSheet, frameToDraw, whereToDraw, null);
    }
}
