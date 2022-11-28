package com.example.hoppyfrog;

import android.util.Log;

public class FollowGameObject extends Component
{
    GameObject followObject;
    Movement followMovement;
    Movement ourMovement;

    public FollowGameObject(GameObject p_gameObject, GameObject p_followObject)
    {
        id = "FOLLOW_GAME_OBJECT";
        gameObject = p_gameObject;
        followObject = p_followObject;
        followMovement = followObject.<Movement>getComponentOfType("MOVEMENT");
        ourMovement = gameObject.<Movement>getComponentOfType("MOVEMENT");
    }

    @Override
    public void update()
    {
        super.update();

        if(followMovement != null)
        {
            if(ourMovement != null)
            {
                ourMovement.velocity = followMovement.velocity;
            }
            else
            {
                Log.e("FollowGameObject", "Can't follow object without Movement component on this gameObject");
            }
        }
        else
        {
            Log.e("FollowGameObject", "Can't follow object without Movement component on follow gameObject");
        }
    }
}
