package com.example.hoppyfrog;

import android.content.Context;

public class LavaSplash extends GameObject
{
    public LavaSplash(Context context)
    {
        //--------------------COMPONENT_SETUP--------------------//

        //-----ANIMATOR_SETUP-----//
        Animation[] animations = new Animation[1];
        animations[0] = new Animation(context, R.drawable.anim_lava_splash, 16, 32, 16, 200, 0.0f, true);
        Animator animator = new Animator(context, this, new Vector2(400,200), animations);
        components.add(animator);
    }
}
