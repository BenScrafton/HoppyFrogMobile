package com.example.hoppyfrog;

import android.content.Context;

public class LillyPad extends GameObject
{
    public LillyPad(Context context, Vector2 pos)
    {
        tag = "LillyPad";
        position = pos;

        //--------------------COMPONENT_SETUP--------------------//

        //-----ANIMATOR_SETUP-----//
        Animation[] animations = new Animation[1];
        animations[0] = new Animation(context, R.drawable.lilly1, 1, 16, 16, 200, 0.0f, true);

        Animator animator = new Animator(context, this, new Vector2(200,200), animations);

        components.add(animator);

        //-----BOXCOLLIDER_SETUP-----//
        BoxCollider collider = new BoxCollider(this, 200, 25, true);
        components.add(collider);
    }
}
