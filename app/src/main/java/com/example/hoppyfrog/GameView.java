package com.example.hoppyfrog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable
{
    volatile boolean playing = true;
    Thread gameThread;

    float deltaTime;

    SurfaceHolder surfaceHolder;
    Canvas canvas = new Canvas();

    GameObject[] gameObjects = new GameObject[11];

    AccelerometerInput accelerometerInput;

    Camera camera;

    PadPlacer padPlacer;

    public GameView(Context context)
    {
        super(context);

        surfaceHolder = getHolder();

        accelerometerInput = new AccelerometerInput(context, this);
        accelerometerInput.Resume();

        gameObjects[0] = new Frog(context);
        gameObjects[1] = new LillyPad(context, new Vector2(500, 1800));

        padPlacer = new PadPlacer(context, gameObjects, new Vector2(100, 1800), 200, 1000);


        camera = new Camera(gameObjects[0], gameObjects, 200.0f);
    }

    public void SensorChanged(SensorEvent event)
    {
        gameObjects[0].<Gravity>getComponentOfType("GRAVITY").gravity = new Vector2(event.values[0] * -200, -500);
    }

    public void resume()
    {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();

        accelerometerInput.Resume();
    }

    public void pause()
    {
        playing = false;
        try{
            gameThread.join();
        } catch (InterruptedException e){
            Log.e("GameView", "Interrupted");
        }

        accelerometerInput.Pause();
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


        padPlacer.update();
        camera.update();
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

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.e("TOUCH EVENT", "JUMP");

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.e("TOUCH EVENT", "JUMP2");
                gameObjects[0].position.y -= 10.0f;
                gameObjects[0].<Gravity>getComponentOfType("GRAVITY").grounded = false;
                gameObjects[0].<Movement>getComponentOfType("MOVEMENT").velocity = new Vector2(0, 700.0f);
                gameObjects[0].<Animator>getComponentOfType("ANIMATOR").changeAnimation(1);
        }

        return super.onTouchEvent(event);
    }
}
