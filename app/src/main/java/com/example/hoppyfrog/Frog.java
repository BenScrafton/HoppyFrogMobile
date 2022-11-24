package com.example.hoppyfrog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Debug;
import android.util.Log;

public class Frog extends GameObject
{
    int numJumps = 2;
    int numJumpsLeft = 2;
    boolean isAlive = true;
    LavaSplash lavaSplash;

    public Frog(Context context)
    {
        lavaSplash = new LavaSplash(context);
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

        Animator animator = new Animator(context, this, new Vector2(200, 200), animations);
        components.add(animator);

        //-----MOVEMENT_SETUP-----//
        Movement movement = new Movement(this);
        components.add(movement);

        //-----GRAVITY_SETUP-----//
        Vector2 g = new Vector2(0, -500.0f);
        Gravity gravity = new Gravity(this, g);
        components.add(gravity);

        //-----BOXCOLLIDER_SETUP-----//
        BoxCollider collider = new BoxCollider(this, 200, 200, false);
        components.add(collider);
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

        if(collision.collider.tag == "LillyPad" && isAlive)
        {
            switch (collision.collisionSide)
            {
                case TOP:
                    //position.y += collision.overlapDistance + 2;
                    break;
                case RIGHT:
                    //position.x += collision.overlapDistance + 2;
                    break;
                case LEFT:
                    //position.x += collision.overlapDistance + 2;
                    break;
                case BOTTOM:
                    position.y += collision.overlapDistance - 0.1f;
                    this.<Movement>getComponentOfType("MOVEMENT").velocity = new Vector2(this.<Movement>getComponentOfType("MOVEMENT").velocity.x,0);
                    this.<Gravity>getComponentOfType("GRAVITY").velocity = new Vector2(0,0);
                    this.<Gravity>getComponentOfType("GRAVITY").grounded = true;
                    this.<Animator>getComponentOfType("ANIMATOR").setAnimationIndex(0);

                    numJumpsLeft = numJumps;
                    break;
            }
        }
        else if(collision.collider.tag == "Lava")
        {
            if(isAlive)
            {
                //GameView.gameManager.SetGameState(GameState.BEGIN_PLAY);

                isAlive = false;
                Log.e("On Collision", "change anim");
                this.<Animator>getComponentOfType("ANIMATOR").changeAnimation(3);
                this.<Movement>getComponentOfType("MOVEMENT").velocity = new Vector2(0, -20);
                this.<Movement>getComponentOfType("MOVEMENT").isActive = false;
                this.<Gravity>getComponentOfType("GRAVITY").isActive = false;
            }
        }
        else if(collision.collider.tag == "Meteor")
        {
            if(isAlive)
            {
                isAlive = false;
                Log.e("On Collision", "change anim");
                this.<Animator>getComponentOfType("ANIMATOR").changeAnimation(4);
                //this.<Movement>getComponentOfType("MOVEMENT").velocity = new Vector2(0, -20);
                this.<Movement>getComponentOfType("MOVEMENT").isActive = false;
                this.<Gravity>getComponentOfType("GRAVITY").isActive = false;
            }
        }

    }

    @Override
    public void OnCollisionExit(GameObject otherCollider)
    {
        super.OnCollisionExit(otherCollider);

        if(otherCollider.tag == "LillyPad")
        {
            this.<Gravity>getComponentOfType("GRAVITY").grounded = false;
        }
    }

    public void Jump()
    {
        if(isAlive)
        {
            if(numJumpsLeft > 0)
            {
                this.<Gravity>getComponentOfType("GRAVITY").grounded = false;
                this.<Movement>getComponentOfType("MOVEMENT").velocity = new Vector2(0, 700.0f);
                this.<Animator>getComponentOfType("ANIMATOR").changeAnimation(1);

                numJumpsLeft--;
            }
        }
    }
}
