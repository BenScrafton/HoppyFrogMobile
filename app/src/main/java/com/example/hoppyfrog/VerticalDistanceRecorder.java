package com.example.hoppyfrog;

import android.util.Log;

public class VerticalDistanceRecorder extends Component
{
    UItext uiText;
    GameObject recordObject;
    Movement movement;
    float distanceTravelledUp = 0;

    ScoreSaveSystem highScoreSaveSystem;

    float startPositionY;

    boolean updatedHighScore = false;

    public VerticalDistanceRecorder(GameObject p_gameObject, GameObject p_recordObject, HighScoreUI p_highScoreUI)
    {
        id = "VERT_DISTANCE_RECORDER";
        gameObject = p_gameObject;

        uiText = gameObject.<UItext>getComponentOfType("UI_TEXT");
        recordObject = p_recordObject;
        movement = recordObject.<Movement>getComponentOfType("MOVEMENT");
        highScoreSaveSystem = p_highScoreUI.<ScoreSaveSystem>getComponentOfType("SCORE_SAVE_SYSTEM");
        startPositionY = recordObject.position.y;

        uiText.text = "distance: 0";
    }

    @Override
    public void update()
    {
        super.update();

        Log.e("V: ", Float.toString(movement.velocity.y));

        distanceTravelledUp = (startPositionY - recordObject.position.y) / 100;

        if( distanceTravelledUp < 0)
        {
            distanceTravelledUp = 0;
        }
        uiText.text = Integer.toString((int)distanceTravelledUp);


        if(GameView.gameManager.GetGameState() == GameState.GAMEOVER && !updatedHighScore)
        {
            highScoreSaveSystem.updateHighScore((int)distanceTravelledUp);
            updatedHighScore = true;
        }
    }


}
