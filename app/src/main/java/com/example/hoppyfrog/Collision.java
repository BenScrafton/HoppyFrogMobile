package com.example.hoppyfrog;

enum CollisionSide
{
    TOP,
    RIGHT,
    LEFT,
    BOTTOM,
    NONE,
}

public class Collision
{
    public CollisionSide collisionSide;
    public float overlapDistance;

    public Collision(CollisionSide p_collisionSide, float p_overlapDistance)
    {
        collisionSide = p_collisionSide;
        overlapDistance = p_overlapDistance;
    }

}
