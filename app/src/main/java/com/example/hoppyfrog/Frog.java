package com.example.hoppyfrog;

import android.content.Context;
import android.util.Log;

public class Frog extends GameObject
{
    public Frog(Context context)
    {
        //--------------------COMPONENT_SETUP--------------------//

        //-----ANIMATOR_SETUP-----//
        Animator animator = new Animator(context, this, R.drawable.anim_frog_idle,
                2, 16, 16, 200, 0.5f);
        components.add(animator);

        //-----GRAVITY_SETUP-----//
        Vector2 g = new Vector2(0, -500.0f);
        Gravity gravity = new Gravity(this, g);
        components.add(gravity);

        Log.e("yolo", "hi friend");
    }
}
