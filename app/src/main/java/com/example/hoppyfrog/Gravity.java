package com.example.hoppyfrog;

import android.util.Log;

public class Gravity extends Component
{
    Vector2 gravity = new Vector2(0, -9.81f);
    Vector2 velocity = new Vector2(0, 0);
    public boolean grounded = false;

    public Gravity(GameObject p_gameObject, Vector2 p_gravity)
    {
        id = "GRAVITY";
        gameObject = p_gameObject;
        gravity = p_gravity;
    }

    @Override
    public void update()
    {
        super.update();
        updatePosition();
    }

    void updatePosition()
    {
        if(!grounded)
        {
            velocity.x += gravity.x * Time.getInstance().deltaTime;
            velocity.y += gravity.y * Time.getInstance().deltaTime;

            gameObject.<Movement>getComponentOfType("MOVEMENT").velocity.x += gravity.x * Time.getInstance().deltaTime;
            gameObject.<Movement>getComponentOfType("MOVEMENT").velocity.y += gravity.y * Time.getInstance().deltaTime;



            //gameObject.position.x += velocity.x * Time.getInstance().deltaTime;
            //gameObject.position.y -= velocity.y * Time.getInstance().deltaTime;
        }
        else
        {
            gameObject.<Movement>getComponentOfType("MOVEMENT").velocity.x = 0;
            gameObject.<Movement>getComponentOfType("MOVEMENT").velocity.y = 0;

            velocity.x = 0;
            velocity.y = 0;
        }
    }
}
