package com.example.hoppyfrog;

public class Movement extends Component
{
    GameObject gameObject;
    public Vector2 velocity = new Vector2(0,0);
    public boolean isDodging = false;

    float dodgeTimer = 0.0f;
    float dodgeCooldown = 0.2f;
    float dodgeDirection = 0;

    public Movement(GameObject p_gameObject)
    {
        id = "MOVEMENT";

        gameObject = p_gameObject;
    }

    @Override
    public void update()
    {
        super.update();

        if(isActive)
        {
            gameObject.position.x += velocity.x * Time.getInstance().deltaTime;
            gameObject.position.y -= velocity.y * Time.getInstance().deltaTime;

            if(isDodging)
            {
                velocity.x += 100000 * Time.getInstance().deltaTime * dodgeDirection;
            }
        }

        if(isDodging)
        {
            DodgeCooldown();
        }
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
}
