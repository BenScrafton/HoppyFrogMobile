package com.example.hoppyfrog;

import android.content.Context;

public class HUD extends GameObject
{
    public HUD(Context context)
    {
        //-----ANIMATOR_SETUP-----//
        Animation[] animations = new Animation[2];
        Animation playing = new Animation(context, R.drawable.your_score, 1,
                90, 160, 200, 0.15f, true);

        animations[0] = playing;

        Animation gameOver = new Animation(context, R.drawable.gameover_scores, 1,
                90, 160, 200, 0.15f, true);

        animations[1] = gameOver;

        Animator animator = new Animator(context, this, new Vector2(1080, 1920), animations);
        components.add(animator);
    }
}
