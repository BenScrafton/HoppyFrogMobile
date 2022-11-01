package com.example.hoppyfrog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class Frog extends GameObject
{
    public Frog(Context context)
    {

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        position = new Vector2((width / 2) - 100, height / 2);

        //--------------------COMPONENT_SETUP--------------------//

        //-----ANIMATOR_SETUP-----//
        Animation[] animations = new Animation[3];

        Animation idle = new Animation(context, R.drawable.anim_frog_idle, 2,
                16, 16, 200, 0.5f, false);
        Animation acending = new Animation(context, R.drawable.anim_frog_acending, 3,
                16, 16, 200, 0.5f, true);
        Animation landing = new Animation(context, R.drawable.anim_frog_crouch, 3,
                16, 16, 200, 0.5f, true);

        animations[0] = idle;
        animations[1] =  acending;
        animations[2] = landing;

        Animator animator = new Animator(context, this, R.drawable.anim_frog_idle,
                2, 16, 16, 200, 0.5f, animations);

        components.add(animator);

        //-----MOVEMENT_SETUP-----//
        Movement movement = new Movement(this);
        components.add(movement);

        //-----GRAVITY_SETUP-----//
        Vector2 g = new Vector2(0, -500.0f);
        Gravity gravity = new Gravity(this, g);
        components.add(gravity);

        //-----BOXCOLLIDER_SETUP-----//
        BoxCollider collider = new BoxCollider(this, 200, 200);
        components.add(collider);
    }

    @Override
    public void update()
    {
        super.update();
        if(this.<Gravity>getComponentOfType("GRAVITY").grounded)
        {
            this.<Animator>getComponentOfType("ANIMATOR").setAnimationIndex(0);
        }
    }
}
