package com.example.hoppyfrog;

public class CollisionData
{
    public boolean isColliding;
    public CollisionSide side;

    CollisionData(boolean p_isColliding, CollisionSide p_side)
    {
       isColliding = false;
       side = CollisionSide.NONE;
    }
}
