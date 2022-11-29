package com.example.hoppyfrog;

import androidx.appcompat.app.AppCompatActivity;

enum GameState
{
    BEGIN_PLAY,
    PLAYING,
    GAMEOVER,
}

public class GameStateManager
{
    public static  GameState gameState = GameState.PLAYING;
    AppCompatActivity appCompatActivity;
    GameActivity gameActivity;
    Lava lava;
    Movement lavaMovement;
    SplashScreen splashScreen;
    Animator backgroundAnimator;
    GameOverUI gameOverUI;
    static GameStateManager instance;
    HighScoreUI highScoreUI;
    HUD hud;
    ScoreUI scoreUI;

    public GameStateManager(AppCompatActivity p_appCompatActivity, GameActivity p_gameActivity, Lava p_lava,
                            SplashScreen p_splashScreen, Background p_background, GameOverUI p_gameOverUI,
                            HighScoreUI p_highScoreUI, HUD p_hud, ScoreUI p_scoreUI)
    {
        gameActivity = p_gameActivity;
        appCompatActivity = p_appCompatActivity;
        lava = p_lava;
        splashScreen = p_splashScreen;
        gameOverUI = p_gameOverUI;
        backgroundAnimator = p_background.<Animator>getComponentOfType("ANIMATOR");
        scoreUI = p_scoreUI;
        highScoreUI = p_highScoreUI;
        hud = p_hud;

        instance = this;
        SetGameState(GameState.BEGIN_PLAY);
    }

    public static GameStateManager GetInstance()
    {
        return instance;
    }


    public void SetGameState(GameState p_gameState)
    {
        gameState = p_gameState;

        switch (gameState)
        {
            case BEGIN_PLAY:
                HandleBeginPlayState();
                break;
            case PLAYING:
                HandlePlayingState();
                break;
            case GAMEOVER:
                HandleGameOverState();
                break;
        }
    }

    public GameState GetGameState()
    {
        return gameState;
    }

    void HandleBeginPlayState()
    {
        ResetGame();
        lava.isActive = false;
        splashScreen.isActive = true;
        backgroundAnimator.isActive = false;
        gameOverUI.isActive = false;
        highScoreUI.isActive = false;
        scoreUI.Reset();
        scoreUI.isActive = false;
        //hud.<Animator>getComponentOfType("ANIMATOR").animationIndex = 1;
        hud.isActive = false;
        //ResetLevel
        //ResetScore
        //Reset Frog
        //Show controls splash screen (tilt, swipe and tap)
    }

    void HandlePlayingState()
    {
        lava.isActive = true;
        splashScreen.isActive = false;
        backgroundAnimator.isActive = true;
        highScoreUI.isActive = false;
        hud.isActive = true;
        hud.<Animator>getComponentOfType("ANIMATOR").animationIndex = 0;
        scoreUI.isActive = true;

    }

    void HandleGameOverState()
    {
        gameOverUI.isActive = true;
        highScoreUI.isActive = true;
        scoreUI.GameOver();

        hud.<Animator>getComponentOfType("ANIMATOR").animationIndex = 1;
        gameActivity.Restart();




        //Show Click to play again
        //Show Score vs High Score
    }

    void ResetGame()
    {
        for(Layer layer : GameView.layers)
        {
            for(GameObject g : layer.gameObjects)
            {
                g.Reset();
            }
        }

        GameView.mainCamera.Reset();
        GameView.padPlacer.Reset();
        GameView.meteorManager.Reset();
    }
}

