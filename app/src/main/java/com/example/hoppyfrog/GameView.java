package com.example.hoppyfrog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable
{
    volatile boolean playing = true;
    Thread gameThread;

    float deltaTime;

    SurfaceHolder surfaceHolder;
    Canvas canvas = new Canvas();

    GameObject[] gameObjects = new GameObject[1];

    public GameView(Context context)
    {
        super(context);
        surfaceHolder = getHolder();
        gameObjects[0] = new Frog(context);
    }

    public void resume()
    {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause()
    {
        playing = false;
        try{
            gameThread.join();
        } catch (InterruptedException e){
            Log.e("GameView", "Interrupted");
        }
    }

    public void run()
    {
        while(playing)
        {
            long startFrameTime = System.currentTimeMillis();

            update();
            render();

            deltaTime = (System.currentTimeMillis() - startFrameTime) / 1000.0f;
            Time.getInstance().setDeltaTime(deltaTime);
        }
    }

    void update()
    {
        for(GameObject g : gameObjects)
        {
            g.update();
        }
    }


    void render()
    {
        if(surfaceHolder.getSurface().isValid())
        {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLUE);

            for(GameObject g : gameObjects)
            {
                g.<Animator>getComponentOfType("ANIMATOR").draw(canvas);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
