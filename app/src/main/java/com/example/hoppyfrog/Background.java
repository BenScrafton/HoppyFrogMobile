package com.example.hoppyfrog;

import android.content.Context;

public class Background extends GameObject
{
    float timer = 0.0f;
    float animChangeTime = 0.07f * 28.0f;
    boolean animChanged = false;

    public Background(Context context)
    {
        position.y = 50;
        Animation[] animations = new Animation[2];
        Animation start = new Animation(context, R.drawable.anim_volcano, 28,
                90, 160, 200, 0.07f, true);

        Animation end = new Animation(context, R.drawable.anim_volcano2, 8,
                90, 160, 200, 0.07f, false);

        animations[0] = start;
        animations[1] = end;
        Animator animator = new Animator(context, this, new Vector2(1080, 1920), animations);
        components.add(animator);
    }

    @Override
    public void update() {
        super.update();

        if(!animChanged)
        {
            timer += Time.getInstance().deltaTime;
        }

        if(timer >= animChangeTime)
        {
            this.<Animator>getComponentOfType("ANIMATOR").changeAnimation(1);
            animChanged = true;
            timer = 0.0f;
        }
    }
}
