package com.example.hoppyfrog;

import android.util.Log;

public class VerticalDistanceRecorder extends Component
{
    UItext uiText;
    GameObject recordObject;
    Movement movement;
    float distanceTravelledUp = 0;

    float lastPositionY;


    public VerticalDistanceRecorder(GameObject p_gameObject, GameObject p_recordObject)
    {
        id = "VERT_DISTANCE_RECORDER";
        gameObject = p_gameObject;
        uiText = gameObject.<UItext>getComponentOfType("UI_TEXT");
        recordObject = p_recordObject;
        movement = recordObject.<Movement>getComponentOfType("MOVEMENT");


        uiText.text = "distance: 0";
    }

    @Override
    public void update()
    {
        super.update();

        Log.e("V: ", Float.toString(movement.velocity.y));

        if(movement.lastMovement.y < 0.0f)
        {
            distanceTravelledUp += Math.abs(movement.lastMovement.y);
            uiText.text = "distance: " + Integer.toString((int)distanceTravelledUp);
        }
    }


}
