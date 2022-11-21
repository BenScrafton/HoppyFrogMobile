package com.example.hoppyfrog;

import android.util.Log;

import java.util.List;

public class Camera
{
    //List<Layer> layers;
    List<List<GameObject>> layers;
    GameObject followObject;
    Vector2 lastPos;
    float moveSpeed;
    Vector2 deltaPos;

    public Camera(GameObject p_followObject, List<List<GameObject>> p_layers, float p_moveSpeed)
    {
        followObject = p_followObject;
        layers = p_layers;
        moveSpeed = p_moveSpeed;
        lastPos = new Vector2(followObject.position.x , followObject.position.y);
    }

    public void update()
    {
        deltaPos = new Vector2(followObject.position.x - lastPos.x,
                                        followObject.position.y - lastPos.y);

        for(List<GameObject> layer : layers)
        {
            for(GameObject object : layer)
            {
                object.position.x -= deltaPos.x; /// (Math.abs(deltaPos.x)) * moveSpeed * Time.getInstance().deltaTime;
                object.position.y -= deltaPos.y; /// (Math.abs(deltaPos.y)) * moveSpeed * Time.getInstance().deltaTime;

                if( object.<BoxCollider>getComponentOfType("BOXCOLLIDER") != null)
                {
                    object.<BoxCollider>getComponentOfType("BOXCOLLIDER").AccountForCameraUpdate(deltaPos);
                }
            }
        }
        lastPos = new Vector2(followObject.position.x , followObject.position.y);
    }

    public Vector2 GetDeltaPos()
    {
        return deltaPos;
    }
}
