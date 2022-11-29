package com.example.hoppyfrog;

import android.util.Log;

public class FollowGameObject extends Component
{
    GameObject followObject;
    Vector2 lastPos;
    Vector2 offset = new Vector2(0,0);
    public FollowGameObject(GameObject p_gameObject, GameObject p_followObject)
    {
        id = "FOLLOW_GAME_OBJECT";
        gameObject = p_gameObject;
        followObject = p_followObject;
        offset.x = followObject.position.x - gameObject.position.x;
        offset.y = followObject.position.y - gameObject.position.y;
    }

    @Override
    public void update()
    {
        super.update();

        gameObject.position.x = followObject.position.x - offset.x;
        gameObject.position.y = followObject.position.y - offset.y;

    }
}
