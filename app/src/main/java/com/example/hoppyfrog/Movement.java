package com.example.hoppyfrog;

public class Movement extends Component
{
    GameObject gameObject;
    public Vector2 velocity = new Vector2(0,0);

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
        }
    }
}
