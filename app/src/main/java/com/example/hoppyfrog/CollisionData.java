package com.example.hoppyfrog;

public class CollisionData
{
    public boolean isColliding;
    public CollisionSide side;
    public GameObject gameObject;

    CollisionData(boolean p_isColliding, GameObject p_gameObject, CollisionSide p_side)
    {
       isColliding = false;
       side = CollisionSide.NONE;
       gameObject = p_gameObject;
    }
}
