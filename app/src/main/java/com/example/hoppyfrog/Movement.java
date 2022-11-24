package com.example.hoppyfrog;

import android.util.Log;

public class Movement extends Component
{
    GameObject gameObject;
    public Vector2 velocity = new Vector2(0,0);
    public boolean isDodging = false;

    float dodgeTimer = 0.0f;
    float dodgeCooldown = 0.2f;
    float dodgeDirection = 0;

    Vector2 lastMovement = new Vector2(0,0);


    public Movement(GameObject p_gameObject)
    {
        id = "MOVEMENT";
        gameObject = p_gameObject;
    }

    @Override
    public void update()
    {
        super.update();

        Vector2 startPos = new Vector2(gameObject.position.x, gameObject.position.y);

        if(isActive)
        {
            if(isDodging)
            {
                velocity.x += 50000 * Time.getInstance().deltaTime * dodgeDirection;
            }



            gameObject.position.x += velocity.x * Time.getInstance().deltaTime;
            gameObject.position.y -= velocity.y * Time.getInstance().deltaTime;
        }



        if(isDodging)
        {
            DodgeCooldown();
        }

        lastMovement =  new Vector2(gameObject.position.x - startPos.x, gameObject.position.y - startPos.y);
    }

    void DodgeCooldown()
    {
        dodgeTimer += Time.getInstance().deltaTime;

        if(dodgeTimer >= dodgeCooldown)
        {
            isDodging = false;
            dodgeTimer = 0.0f;
        }
    }

    public void Dodge(Vector2 direction)
    {
        if(!isDodging)
        {
            isDodging = true;

            if(direction.x > 0)
            {
                dodgeDirection = 1;
            }
            else if(direction.x < 0)
            {
                dodgeDirection = -1;
            }
        }
    }

    public Vector2 GetLastMove()
    {
        return lastMovement;
    }
}
