package com.example.hoppyfrog;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class Frog extends GameObject
{
    //Components
    Animator animator;
    Movement movement;
    Gravity gravity;
    BoxCollider collider;
    AudioSource audioSource;

    private int numJumps = 2;
    private int numJumpsLeft = 2;

    public boolean isAlive = true;

    public Frog(Context context)
    {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        position = new Vector2((width / 2) - 100, height / 2);

        //--------------------COMPONENT_SETUP--------------------//

        //-----ANIMATOR_SETUP-----//
        Animation[] animations = new Animation[5];

        Animation idle = new Animation(context, R.drawable.anim_frog_idle, 2,
                16, 16, 200, 0.5f, false);
        Animation acending = new Animation(context, R.drawable.anim_frog_acending, 3,
                16, 16, 200, 0.5f, true);
        Animation landing = new Animation(context, R.drawable.anim_frog_crouch, 3,
                16, 16, 200, 0.5f, true);
        Animation death = new Animation(context, R.drawable.anim_frog_death, 9,
                16, 16, 200, 0.07f, true);
        Animation squashed = new Animation(context, R.drawable.anim_frog_squashed, 18,
                16, 16, 200, 0.025f, true);

        animations[0] = idle;
        animations[1] =  acending;
        animations[2] = landing;
        animations[3] = death;
        animations[4] = squashed;

        animator = new Animator(context, this, new Vector2(200, 200), animations);
        components.add(animator);

        //-----MOVEMENT_SETUP-----//
        movement = new Movement(this);
        components.add(movement);

        //-----GRAVITY_SETUP-----//
        Vector2 g = new Vector2(0, -500.0f);
        gravity = new Gravity(this, g);
        components.add(gravity);

        //-----BOXCOLLIDER_SETUP-----//
        collider = new BoxCollider(this, 200, 200, false);
        components.add(collider);

        //-----AUDIO_SOURCE_SETUP-----//
        audioSource = new AudioSource(this);
        audioSource.LoadSoundPoolFile(R.raw.boing); //index: 0
        audioSource.LoadSoundPoolFile(R.raw.burn); //        1
        audioSource.LoadSoundPoolFile(R.raw.swipe);//       2
    }

    @Override
    public void update()
    {
        super.update();
    }

    @Override
    public void OnCollision(Collision collision)
    {
        super.OnCollision(collision);

        if (!isAlive)
        {
            return;
        }

        switch (collision.collider.tag)
        {
            case "LillyPad":
                if (collision.collisionSide == CollisionSide.BOTTOM)
                {
                    position.y += collision.overlapDistance - 0.1f;
                    movement.velocity = new Vector2(movement.velocity.x,0);
                    gravity.velocity = new Vector2(0,0);
                    gravity.grounded = true;
                    animator.setAnimationIndex(0);

                    numJumpsLeft = numJumps;
                }
                break;
            case "Lava":
                animator.changeAnimation(3);
                Die();

                break;
            case "Meteor":
                animator.changeAnimation(4);
                Die();

                break;
        }
    }

    @Override
    public void OnCollisionExit(GameObject otherCollider)
    {
        super.OnCollisionExit(otherCollider);

        if(otherCollider.tag == "LillyPad")
        {
            gravity.grounded = false;
        }
    }

    void Die()
    {
        isAlive = false;
        GameView.gameManager.SetGameState(GameState.GAMEOVER);
        movement.velocity = new Vector2(0, 0);
        movement.isActive = false;
        gravity.isActive = false;
        audioSource.PlaySound(1);
    }

    public void Jump()
    {
        if(isAlive)
        {
            if(numJumpsLeft > 0)
            {
                gravity.grounded = false;
                movement.velocity = new Vector2(0, 700.0f);
                animator.changeAnimation(1);

                numJumpsLeft--;
                audioSource.PlaySound(0);
            }
        }
    }

    public void Dodge(Vector2 swipe)
    {
        movement.Dodge(swipe);
        audioSource.PlaySound(2);
    }

    @Override
    public void Reset()
    {
        super.Reset();

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

        position = new Vector2((width / 2) - 100, (height / 2) - 1);
        collider.UpdateBounds();
        movement.velocity = new Vector2(0,0);
        animator.changeAnimation(0);
        isAlive = true;

        audioSource.StopSound();
        movement.isActive = true;
        gravity.isActive = true;
    }
}
