package com.example.hoppyfrog;

import android.content.Context;

public class GameOverUI extends GameObject
{
    public GameOverUI(Context context)
    {
        position.y = 200;

        //--------------------COMPONENT_SETUP--------------------//

        //-----ANIMATOR_SETUP-----//
        Animation[] animations = new Animation[1];
        Animation idle = new Animation(context, R.drawable.anim_ui_gameover, 4,
                90, 160, 200, 0.15f, false);

        animations[0] = idle;

        Animator animator = new Animator(context, this, new Vector2(1080, 1920), animations);
        components.add(animator);
    }

}

