package com.example.hoppyfrog;

import android.util.Log;

public class VerticalDistanceRecorder extends Component
{
    private UItext uiText;
    private GameObject recordObject;
    private Movement movement;
    private float distanceTravelledUp = 0;

    private ScoreSaveSystem highScoreSaveSystem;
    private float startPositionY;

    public boolean updatedHighScore = false;

    public VerticalDistanceRecorder(GameObject p_gameObject, GameObject p_recordObject, HighScoreUI p_highScoreUI)
    {
        id = "VERT_DISTANCE_RECORDER";
        gameObject = p_gameObject;

        uiText = gameObject.<UItext>getComponentOfType(ComponentType.UI_TEXT);
        recordObject = p_recordObject;
        movement = recordObject.<Movement>getComponentOfType(ComponentType.MOVEMENT);
        highScoreSaveSystem = p_highScoreUI.<ScoreSaveSystem>getComponentOfType(ComponentType.SCORE_SAVE_SYSTEM);
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
