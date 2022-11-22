package com.example.hoppyfrog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.SensorEvent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable, View.OnTouchListener, GestureDetector.OnGestureListener
{
    volatile boolean playing = true;
    Thread gameThread;
    float deltaTime;

    SurfaceHolder surfaceHolder;
    Canvas canvas = new Canvas();
    public static Camera camera;

    List<List<GameObject>> layers = new ArrayList<>();
    List<GameObject> uiLayer = new ArrayList<>();

    PadPlacer padPlacer;
    MeteorManager meteorManager;
    GameObject player;

    AccelerometerInput accelerometerInput;
    GestureDetector gestureDetector;

    public GameView(Context context)
    {
        super(context);
        surfaceHolder = getHolder();

        //-----------INPUT_EVENT_LISTENERS_SETUP-----------//
        gestureDetector = new GestureDetector(context, this);
        setOnTouchListener(this);

        accelerometerInput = new AccelerometerInput(context, this);
        accelerometerInput.Resume();

        //-----------------SETUP_LAYERS-----------------//
        List<GameObject> foregroundObjects = new ArrayList<>();
        List<GameObject> midgroundObjects = new ArrayList<>();
        List<GameObject> backgroundObjects = new ArrayList<>();

        //---------SETUP_FOREGROUND---------//
        foregroundObjects.add(new Lava(context));//Lava

        player = new Frog(context);
        foregroundObjects.add(player);//Player

        meteorManager = new MeteorManager(context, foregroundObjects);//Meteors

        //---------SETUP_MIDGROUND---------//
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        padPlacer = new PadPlacer(context, midgroundObjects,
                    new Vector2((width / 2) - 100, height / 2 + 200), -500, 500);//LillyPads

        //---------SETUP_BACKGROUND---------//
        backgroundObjects.add(new Background(context, camera));

        //---------SETUP_UI---------//
        Score score = new Score(player);
        uiLayer.add(score);

        //---------SETUP_LAYERS_LIST---------//
        layers.add(backgroundObjects);
        layers.add(midgroundObjects);
        layers.add(foregroundObjects);

        //-----------CAMERA_SETUP-----------//
        //int ignoreLayers[1] = {0};

        camera = new Camera(player, layers, 200.0f, ignoreLayers);
    }

    public void SensorChanged(SensorEvent event)
    {
        Vector2 v = new Vector2(player.<Movement>getComponentOfType("MOVEMENT").velocity.x, player.<Movement>getComponentOfType("MOVEMENT").velocity.y);
        player.<Movement>getComponentOfType("MOVEMENT").velocity = new Vector2( (event.values[0] * -100), v.y);
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
        for(List<GameObject> layer : layers) // update main gameplay layers
        {
            for(GameObject gameObject : layer)
            {
                gameObject.update();
            }
        }

        for(GameObject uiElement : uiLayer)
        {
            uiElement.update();
        }

        padPlacer.update();
        meteorManager.update();
    }

    void render()
    {
        if(surfaceHolder.getSurface().isValid())
        {
            camera.update();

            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            int layerIndex = 0;

            for(List<GameObject> layer : layers) // render main gameplay layers
            {
                for (GameObject gameObject : layer)
                {
                    if(!gameObject.isActive)
                    {
                        continue;
                    }

                    if(gameObject.<Animator>getComponentOfType("ANIMATOR") != null)
                    {
                        gameObject.<Animator>getComponentOfType("ANIMATOR").draw(canvas);
                    }

                    if(gameObject.<UItext>getComponentOfType("UI_TEXT") != null)
                    {
                        gameObject.<UItext>getComponentOfType("UI_TEXT").render(canvas);
                    }

                    //Paralax
                    if(layerIndex == 0)
                    {
                        //gameObject.position.x += camera.GetDeltaPos().x * 0.95f;
                        //gameObject.position.y += camera.GetDeltaPos().y * 0.95f;
                    }
                }
                layerIndex++;
            }

            for(GameObject uiElement : uiLayer) // render ui layers
            {
                if(uiElement.<UItext>getComponentOfType("UI_TEXT") != null)
                {
                    uiElement.<UItext>getComponentOfType("UI_TEXT").render(canvas);
                }
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    //-----------------------GESTURES-----------------------//

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d("Gestures", "onTouch");
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent) {

        Log.d("Gestures", "onDown");
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent) {
        Log.d("Gestures", "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
        Log.d("Gestures", "onSingleTapUp");
        ((Frog) player).Jump();
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        Log.d("Gestures", "onScroll");

        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent) {
        Log.d("Gestures", "onLongPress");
    }

    @Override
    public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        Log.d("Gestures", "onFling");
        ((Frog) player).<Movement>getComponentOfType("MOVEMENT").Dodge(new Vector2(v, v1));
        return false;
    }
}
