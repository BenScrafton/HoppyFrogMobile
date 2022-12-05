package com.example.hoppyfrog;

import android.content.Context;

public class Meteor extends GameObject
{
    AudioSource audioSource;

    public Meteor(Context context, Vector2 p_position)
    {
        tag = "Meteor";
        position = p_position;

        //--------------------COMPONENT_SETUP--------------------//

        //-----ANIMATOR_SETUP-----//
        Animation[] animations = new Animation[1];
        animations[0] = new Animation(context, R.drawable.meteor, 5, 16, 32, 200, 0.05f, false);
        Animator animator = new Animator(context, this, new Vector2(200,400), animations);

        components.add(animator);

        //-----MOVEMENT_SETUP-----//
        Movement movement = new Movement(this);
        movement.velocity = new Vector2(0,-1000);
        components.add(movement);

        //-----GRAVITY_SETUP-----//
        Vector2 g = new Vector2(0, -500.0f);
        Gravity gravity = new Gravity(this, g);
        components.add(gravity);

        //-----BOXCOLLIDER_SETUP-----//
        BoxCollider collider = new BoxCollider(this, 200, 400, true);
        components.add(collider);

        //-----AUDIO_SOURCE_SETUP-----//
        audioSource = new AudioSource(this);
        audioSource.LoadSoundPoolFile(R.raw.whistle);
    }

}
