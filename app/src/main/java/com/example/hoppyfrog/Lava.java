package com.example.hoppyfrog;

import android.content.Context;

public class Lava extends GameObject
{
    public Lava(Context context)
    {
        tag = "Lava";
        position = new Vector2(-4000, 2050);

        //--------------------COMPONENT_SETUP--------------------//

        //-----ANIMATOR_SETUP-----//
        Animation[] animations = new Animation[1];
        Animation idle = new Animation(context, R.drawable.lava, 1,
                90, 160, 200, 0.5f, false);
        animations[0] = idle;
        Animator animator = new Animator(context, this, new Vector2(10000, 10000), animations);
        components.add(animator);

        //-----MOVEMENT_SETUP-----//
        Movement movement = new Movement(this);
        components.add(movement);
        movement.velocity = new Vector2(0, 30);

        //-----BOXCOLLIDER_SETUP-----//
        BoxCollider collider = new BoxCollider(this, 10000, 10000, false);
        components.add(collider);
    }

    @Override
    public void update()
    {
        super.update();
    }
}
