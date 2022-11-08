package com.example.hoppyfrog;

import android.util.Log;

public class Camera
{
    GameObject[] sceneObjects;
    GameObject followObject;
    Vector2 lastPos;
    float moveSpeed;

    public Camera(GameObject p_followObject, GameObject[] p_sceneObjects, float p_moveSpeed)
    {
        followObject = p_followObject;
        sceneObjects = p_sceneObjects;
        moveSpeed = p_moveSpeed;
        lastPos = new Vector2(followObject.position.x , followObject.position.y);
    }

    public void update()
    {
        Vector2 deltaPos = new Vector2(followObject.position.x - lastPos.x,
                                        followObject.position.y - lastPos.y);

        for(GameObject object : sceneObjects)
        {
            object.position.x -= deltaPos.x; /// (Math.abs(deltaPos.x)) * moveSpeed * Time.getInstance().deltaTime;
            object.position.y -= deltaPos.y; /// (Math.abs(deltaPos.y)) * moveSpeed * Time.getInstance().deltaTime;

            object.<BoxCollider>getComponentOfType("BOXCOLLIDER").AccountForCameraUpdate(deltaPos);
        }

        lastPos = new Vector2(followObject.position.x , followObject.position.y);
    }
}
