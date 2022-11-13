package com.example.hoppyfrog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;

public class Animator extends Component
{
    Bitmap spriteSheet;
    int numFrames;
    int spriteWidth;
    int spriteHeight;
    int currentFrame = 0;
    Vector2 scale;

    float timeBetweenFrames;
    float timer = 0.0f;

    private Rect frameToDraw;
    private RectF whereToDraw;

    Animation[] animations;
    int animationIndex = 0;

    public Animator(Context context, GameObject p_gameObject, Vector2 p_scale, Animation[] p_animations)
    {
        id = "ANIMATOR";

        gameObject = p_gameObject;
        animations = p_animations;

        scale = p_scale;

        frameToDraw = new Rect(0,0, animations[animationIndex].spriteWidth, animations[animationIndex].spriteHeight);
        whereToDraw = new RectF(100, 100, 200, 200);
    }

    @Override
    public void update()
    {
        //super.update();
        updateAnimFrame();
    }

    void updateAnimFrame()
    {
        timer += Time.getInstance().deltaTime;

        if(timer >= animations[animationIndex].timeBetweenFrames)
        {
            //Next Frame
            if(currentFrame < animations[animationIndex].numFrames - 1)
            {
                currentFrame++;
                timer = 0.0f;
            }
            else
            {
                if(!animations[animationIndex].playOnce)
                {
                    currentFrame = 0;
                    timer = 0.0f;
                }
            }
            frameToDraw.left = currentFrame * animations[animationIndex].spriteWidth;
            frameToDraw.right = frameToDraw.left + animations[animationIndex].spriteWidth;
        }
    }

    public void changeAnimation(int index)
    {
        animationIndex = index;
        timer = 0.0f;
        currentFrame = 0;
    }

    public void setAnimationIndex(int index)
    {
        if(currentFrame > animations[index].numFrames - 1)
        {
            currentFrame = 0;
        }
        animationIndex = index;
    }

    public void draw(Canvas canvas)
    {
        Log.e("Animation", currentFrame + " " + animationIndex);

        frameToDraw.left = currentFrame * animations[animationIndex].spriteWidth;
        frameToDraw.right = frameToDraw.left + animations[animationIndex].spriteWidth;

        whereToDraw.set(gameObject.position.x, gameObject.position.y, gameObject.position.x + scale.x, gameObject.position.y + scale.y);
        canvas.drawBitmap(animations[animationIndex].spriteSheet, frameToDraw, whereToDraw, null);
    }
}
