package com.example.hoppyfrog;

import android.content.Context;

public class Lava extends GameObject
{
    Animator animator;
    Movement movement;
    BoxCollider collider;

    public Lava(Context context)
    {
        tag = "Lava";
        position = new Vector2(-4000, 10000);

        //--------------------COMPONENT_SETUP--------------------//

        //-----ANIMATOR_SETUP-----//
        Animation[] animations = new Animation[1];
        Animation idle = new Animation(context, R.drawable.lava, 1,
                90, 160, 200, 0.5f, false);
        animations[0] = idle;
        animator = new Animator(context, this, new Vector2(10000, 10000), animations);
        components.add(animator);

        //-----MOVEMENT_SETUP-----//
        movement = new Movement(this);
        components.add(movement);
        movement.velocity = new Vector2(0, 200);

        //-----BOXCOLLIDER_SETUP-----//
        collider = new BoxCollider(this, 10000, 10000, false);
        components.add(collider);
    }

    @Override
    public void update()
    {
        super.update();
    }

    @Override
    public void Reset()
    {
        super.Reset();
        position = new Vector2(-4000, 3000);
        collider.UpdateBounds();
    }
}
