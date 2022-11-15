package com.example.hoppyfrog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
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

    GameObject[] gameObjects = new GameObject[12];

    AccelerometerInput accelerometerInput;

    Camera camera;
    PadPlacer padPlacer;

    GameObject background;

    public GameView(Context context)
    {
        super(context);

        surfaceHolder = getHolder();
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

        accelerometerInput = new AccelerometerInput(context, this);
        accelerometerInput.Resume();

        gameObjects[0] = new Frog(context);

        background = new Background(context);
        padPlacer = new PadPlacer(context, gameObjects, new Vector2((width / 2) - 100, height / 2 + 200), -500, 500);
        gameObjects[11] = new Lava(context);

        camera = new Camera(gameObjects[0], gameObjects, 200.0f);
    }

    public void SensorChanged(SensorEvent event)
    {
        gameObjects[0].<Movement>getComponentOfType("MOVEMENT").velocity = new Vector2( event.values[0] * -100 ,gameObjects[0].<Movement>getComponentOfType("MOVEMENT").velocity.y);
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
        background.update();
    }

    void render()
    {
        if(surfaceHolder.getSurface().isValid())
        {
            camera.update();

            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            background.<Animator>getComponentOfType("ANIMATOR").draw(canvas);

            for(GameObject g : gameObjects)
            {
                if(g != gameObjects[0])
                {
                    g.<Animator>getComponentOfType("ANIMATOR").draw(canvas);
                }
            }



            gameObjects[0].<Animator>getComponentOfType("ANIMATOR").draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                ((Frog) gameObjects[0]).Jump();
        }

        return super.onTouchEvent(event);
    }
}
