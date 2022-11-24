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
    GameState gameState = GameState.PLAYING;
    AppCompatActivity appCompatActivity;
    GameActivity gameActivity;

    GameStateManager(AppCompatActivity p_appCompatActivity, GameActivity p_gameActivity)
    {
        gameActivity = p_gameActivity;
        appCompatActivity = p_appCompatActivity;
    }

    void SetGameState(GameState p_gameState)
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

    void HandleBeginPlayState()
    {
        gameActivity.Restart();
        //ResetLevel
        //ResetScore
        //Reset Frog
        //Show controls splash screen (tilt, swipe and tap)
    }

    void HandlePlayingState()
    {

    }

    void HandleGameOverState()
    {
        //Show Click to play again
        //Show Score vs High Score
    }
}


