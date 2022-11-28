package com.example.hoppyfrog;

import android.content.Context;

public class SplashScreen extends GameObject
{
    public SplashScreen(Context context)
    {
        position.y = 50;

        //--------------------COMPONENT_SETUP--------------------//

        //-----ANIMATOR_SETUP-----//
        Animation[] animations = new Animation[1];
        Animation idle = new Animation(context, R.drawable.anim_ui_spalsh_screen, 4,
                            90, 160, 200, 0.15f, false);

        animations[0] = idle;

        Animator animator = new Animator(context, this, new Vector2(1080, 1920), animations);
        components.add(animator);
    }
}
