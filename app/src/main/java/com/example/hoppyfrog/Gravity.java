package com.example.hoppyfrog;

import android.util.Log;

public class Gravity extends Component
{
    public Vector2 gravity = new Vector2(0, -9.81f);
    public Vector2 velocity = new Vector2(0, 0);
    public boolean grounded = false;

    public Gravity(GameObject p_gameObject, Vector2 p_gravity)
    {
        id = "GRAVITY";
        componentType = ComponentType.GRAVITY;
        gameObject = p_gameObject;
        gravity = p_gravity;
    }

    @Override
    public void update()
    {
        super.update();
        if(isActive)
        {
            updatePosition();
        }
    }

    void updatePosition()
    {
        if(!grounded)
        {
            velocity.x += gravity.x * Time.getInstance().deltaTime;
            velocity.y += gravity.y * Time.getInstance().deltaTime;

            gameObject.<Movement>getComponentOfType(ComponentType.MOVEMENT).velocity.x += gravity.x * Time.getInstance().deltaTime;
            gameObject.<Movement>getComponentOfType(ComponentType.MOVEMENT).velocity.y += gravity.y * Time.getInstance().deltaTime;
        }
    }

    void SetGrounded(boolean g)
    {
        grounded = g;
        if(g)
        {
            gameObject.<Movement>getComponentOfType(ComponentType.MOVEMENT).velocity.x = 0;
            gameObject.<Movement>getComponentOfType(ComponentType.MOVEMENT).velocity.y = 0;

            velocity.x = 0;
            velocity.y = 0;
        }
    }
}
