package com.example.hoppyfrog;

import android.content.Context;

public class Background extends GameObject
{
   private float timer = 0.0f;
   private float animChangeTime = 0.07f * 28.0f;
   private boolean animChanged = false;
   private Animator animator;
   private boolean erupted = false;
   private AudioSource audioSource;

    public Background(Context context)
    {
        position.y = -520;

        //--------------------COMPONENT_SETUP--------------------//

        //-----ANIMATOR_SETUP-----//
        Animation[] animations = new Animation[2];
        Animation start = new Animation(context, R.drawable.anim_volcano_erupt, 27,
                90, 300, 200, 0.07f, true);
        Animation end = new Animation(context, R.drawable.anim_volcano_after, 6,
                90, 300, 200, 0.07f, false);

        animations[0] = start;
        animations[1] = end;
        animator = new Animator(context, this, new Vector2(1080, 3600), animations);
        components.add(animator);

        //-----AUDIO_SOURCE_SETUP-----//
        audioSource = new AudioSource(this);
        audioSource.LoadSoundPoolFile(R.raw.eruption);// index: 0

        audioSource.LoadMediaPlayerFile(R.raw.lava);
        audioSource.LoadMediaPlayerFile(R.raw.low_rumble);
        audioSource.LoadMediaPlayerFile(R.raw.sound_track);
        audioSource.PlayMediaPlayer(2);
    }

    @Override
    public void update()
    {
        super.update();

        if(GameStateManager.gameState == GameState.PLAYING)
        {
            if(!animChanged)
            {
                timer += Time.getInstance().deltaTime;
            }

            if(!erupted)
            {
                erupted = true;
                audioSource.PlaySound(0);
                audioSource.PlayMediaPlayers();
            }

            if(timer >= 2)
            {
                this.<Animator>getComponentOfType(ComponentType.ANIMATOR).changeAnimation(1);
                animChanged = true;
                timer = 0.0f;
            }
        }
    }

    @Override
    public void Reset() {
        super.Reset();
        animator.changeAnimation(0);
        timer = 0;
        animChanged = false;
        erupted = false;

        audioSource.PauseMediaPlayer(0);
        audioSource.PauseMediaPlayer(1);
        audioSource.StopSound();
    }
}
