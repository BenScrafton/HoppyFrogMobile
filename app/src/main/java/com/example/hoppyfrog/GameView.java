package com.example.hoppyfrog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.hardware.SensorEvent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable, View.OnTouchListener, GestureDetector.OnGestureListener
{
    volatile boolean playing = true;
    private Thread gameThread;
    private float deltaTime;

    private SurfaceHolder surfaceHolder;
    private Canvas canvas = new Canvas();

    public static MainCamera mainCamera;
    public static List<Layer> layers = new ArrayList<>();
    public static PadPlacer padPlacer;
    public static MeteorManager meteorManager;
    public static GameStateManager gameManager;
    public static SoundManager soundManager;

    private GameObject player;
    private AccelerometerInput accelerometerInput;
    private GestureDetector gestureDetector;
    private Context context;

    public GameView(Context p_context, AppCompatActivity appCompatActivity, GameActivity gameActivity)
    {
        super(p_context);

        context = p_context;
        surfaceHolder = getHolder();
        soundManager = new SoundManager(context);

        //-----------INPUT_EVENT_LISTENERS_SETUP-----------//
        gestureDetector = new GestureDetector(context, this);
        setOnTouchListener(this);

        accelerometerInput = new AccelerometerInput(context, this);
        accelerometerInput.Resume();

        //-----------------SETUP_LAYERS-----------------//
        List<GameObject> foregroundObjects = new ArrayList<>();
        List<GameObject> midgroundObjects = new ArrayList<>();
        List<GameObject> backgroundObjects = new ArrayList<>();
        List<GameObject> uiObjects = new ArrayList<>();

        //---------SETUP_FOREGROUND---------//
        Lava lava = new Lava(context);
        foregroundObjects.add(lava);//Lava

        player = new Frog(context);
        foregroundObjects.add(player);//Player

        meteorManager = new MeteorManager(context, foregroundObjects);//Meteors

        //---------SETUP_MIDGROUND---------//
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        padPlacer = new PadPlacer(context, midgroundObjects,
                    new Vector2((width / 2) - 100, height / 2 + 200), -500, 500);//LillyPads

        //---------SETUP_BACKGROUND---------//
        Background background = new Background(context);
        backgroundObjects.add(background);

        //---------SETUP_UI---------//
        HighScoreUI highScoreUI = new HighScoreUI(context);
        uiObjects.add(highScoreUI);

        ScoreUI score = new ScoreUI(player, context, highScoreUI);
        uiObjects.add(score);

        SplashScreen splashScreen = new SplashScreen(context);
        uiObjects.add(splashScreen);

        GameOverUI gameOverUI = new GameOverUI(context);
        uiObjects.add(gameOverUI);

        HUD hud = new HUD(context);
        uiObjects.add(hud);

        //---------SETUP_LAYERS_LIST---------//
        layers.add(new Layer(backgroundObjects, true));
        layers.add(new Layer(midgroundObjects, false));
        layers.add(new Layer(foregroundObjects, false));
        layers.add(new Layer(uiObjects, true));

        //-----------CAMERA_SETUP-----------//
        int ignoreLayers[] = {0,3};
        mainCamera = new MainCamera(player);
        gameManager = new GameStateManager(appCompatActivity, gameActivity, lava,
                                            splashScreen, background, gameOverUI, highScoreUI,
                                            hud, score);
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

    public void SensorChanged(SensorEvent event)
    {
        if(((Frog)player).isAlive && gameManager.gameState != GameState.BEGIN_PLAY)
        {
            Vector2 v = new Vector2(player.<Movement>getComponentOfType(ComponentType.MOVEMENT).velocity.x, player.<Movement>getComponentOfType(ComponentType.MOVEMENT).velocity.y);
            player.<Movement>getComponentOfType(ComponentType.MOVEMENT).velocity = new Vector2( (event.values[0] * -100), v.y);
        }
    }

    public void resume()
    {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
        accelerometerInput.Resume();
        soundManager.resumeSounds();
        soundManager.resumeMediaPlayers();
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
        soundManager.PauseMediaPlayers();
        soundManager.pauseSounds();
    }

    void update()
    {
        for(Layer layer : layers) // update main gameplay layers
        {
            for(GameObject gameObject : layer.gameObjects)
            {
                gameObject.update();
            }
        }

        if(gameManager.GetGameState() == GameState.PLAYING)
        {
            padPlacer.update();
            meteorManager.update();
            mainCamera.update();
        }
    }

    void render()
    {
        if(surfaceHolder.getSurface().isValid())
        {
            canvas = surfaceHolder.lockCanvas();
            mainCamera.<Camera>getComponentOfType(ComponentType.CAMERA).render(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    //-----------------------GESTURES-----------------------//

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent)
    {
          return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent)
    {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent)
    {


        switch (gameManager.GetGameState())
        {
            case BEGIN_PLAY:
                gameManager.SetGameState(GameState.PLAYING);
                break;
            case PLAYING:
                ((Frog) player).Jump();
                break;
            case GAMEOVER:
                gameManager.SetGameState(GameState.BEGIN_PLAY);
                break;
        }
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1)
    {

        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent)
    {

    }

    @Override
    public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1)
    {

        switch (gameManager.GetGameState())
        {
            case BEGIN_PLAY:
                break;
            case PLAYING:
                ((Frog) player).Dodge(new Vector2(v, v1));
                break;
            case GAMEOVER:
                break;
        }
        return false;
    }
}
