package com.example.hoppyfrog;

import android.graphics.Canvas;

public class MainCamera extends GameObject
{
    public MainCamera(GameObject followObject)
    {
        position = new Vector2(0,0);

        //--------------------COMPONENT_SETUP--------------------//

        //-----CAMERA_SETUP-----//
        Camera camera = new Camera(this);
        components.add(camera);

        //-----MOVEMENT_SETUP-----//
        Movement movement = new Movement(this);
        components.add(movement);

        //-----FOLLOW_GAME_OBJECT_SETUP-----//
        FollowGameObject follower = new FollowGameObject(this, followObject);
        components.add(follower);
    }

    public void Reset()
    {
        position = new Vector2(0,0);
    }
}
