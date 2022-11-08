package com.example.hoppyfrog;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BoxCollider extends Component
{
    public RectF box;
    float scaleX;
    float scaleY;
    static List<BoxCollider> boxColliders = new ArrayList<BoxCollider>();
    public RectF lastBox;

    boolean isStatic = false;

    BoxCollider(GameObject p_gameObject, float p_scaleX, float p_scaleY, boolean p_isStatic)
    {
        id = "BOXCOLLIDER";
        isStatic = p_isStatic;
        gameObject = p_gameObject;
        scaleX = p_scaleX;
        scaleY = p_scaleY;
        box = new RectF(gameObject.position.x, gameObject.position.y, gameObject.position.x + scaleX, gameObject.position.y + scaleY);
        boxColliders.add(this);
        lastBox = new RectF(0, 0, 0, 0);
    }

    @Override
    public void update()
    {
        super.update();

        UpdateBounds();
        CheckCollisions();
        lastBox.set((float) box.left, (float) box.top, (float) box.right, (float) box.bottom);
    }

    void UpdateBounds()
    {
        box.set(gameObject.position.x, gameObject.position.y, gameObject.position.x + scaleX, gameObject.position.y + scaleY);
    }

    void AccountForCameraUpdate(Vector2 deltaPos)
    {
        box.set(gameObject.position.x, gameObject.position.y, gameObject.position.x + scaleX, gameObject.position.y + scaleY);

        lastBox.set(lastBox.left - deltaPos.x, lastBox.top - deltaPos.y,
         lastBox.right - deltaPos.x, lastBox.bottom - deltaPos.y);
    }

    void CheckCollisionsOLD()
    {
        for(BoxCollider collider : boxColliders)
        {
            if(collider != this)
            {
                if((this.box.right >= collider.box.left) && (this.box.left <= collider.box.right) &&
                        (this.box.top <= collider.box.bottom) && (this.box.bottom >= collider.box.top))
                {
                    if(gameObject.<Gravity>getComponentOfType("GRAVITY") != null)
                    {
                        //if(gameObject.<Gravity>getComponentOfType("GRAVITY").grounded = true)
                        //{
                            //gameObject.<Gravity>getComponentOfType("GRAVITY").grounded = true;
                        //}

                        CollisionSide side;
                        float overlapDist;

                        //Log.e("COLLISION CHECK: ", Float.toString(lastBox.bottom) + " : " + Float.toString(collider.box.top) + " : " + Float.toString(box.bottom - collider.box.top));

                        //Log.e("COLLISION CHECK: ", Float.toString(lastBox.bottom) + " : " + Float.toString(box.bottom));

                        if((lastBox.bottom < box.bottom) && (box.bottom > collider.box.top) && (lastBox.bottom < collider.box.top)) // Moved Down and Overlapped on Top of collider
                        {
                            side = CollisionSide.BOTTOM;
                            overlapDist = box.bottom - collider.box.top;
                        }
                        else if((lastBox.top > box.top) && (box.top < collider.box.bottom) && (lastBox.top > collider.box.bottom)) // Moved Up and Overlapped on Bottom of collider
                        {
                            side = CollisionSide.TOP;
                            overlapDist = Math.abs(box.top - collider.box.bottom);
                        }
                        else if((lastBox.left > box.left) && (box.left < collider.box.right)) // Moved Left and Overlapped Right of collider
                        {
                            side = CollisionSide.LEFT;
                            overlapDist = Math.abs(box.left - collider.box.right);
                        }
                        else // MUST HAVE :  Moved Right and Overlapped Left of collider
                        {
                            side = CollisionSide.RIGHT;
                            overlapDist =  Math.abs(box.right - collider.box.left);
                        }

                        Collision collision = new Collision(side, overlapDist);
                        gameObject.OnCollision(collision);
                    }
                }
            }
        }
    }

    void CheckCollisions()
    {
        if(isStatic)
        {
            return;
        }

        boolean collided = false;

        for(BoxCollider collider : boxColliders)
        {
            collided = false;
            if (collider != this)
            {
                if ((this.box.right >= collider.box.left) && (this.box.left <= collider.box.right) &&
                        (this.box.top <= collider.box.bottom) && (this.box.bottom >= collider.box.top))
                {
                    collided = true;
                }
            }

            if(collided)
            {
                CollisionSide side;
                float overlapDist;


                if(lastBox.bottom < collider.box.top)
                {
                    side = CollisionSide.BOTTOM;
                    overlapDist = collider.box.top - box.bottom;

                    Log.e("CHECKER", "LAST BOX: " + Float.toString(lastBox.bottom) + " : " + Float.toString(collider.box.top)  + "," + Float.toString(box.bottom) + "," + Float.toString(overlapDist));

                }
                else if(lastBox.top > collider.box.bottom)
                {
                    side = CollisionSide.TOP;
                    overlapDist = collider.box.bottom - box.top;
                }
                else if(lastBox.left > collider.box.right)
                {
                    side = CollisionSide.LEFT;
                    overlapDist = collider.box.right - box.left;
                }
                else
                {
                    side = CollisionSide.RIGHT;
                    overlapDist = collider.box.left - box.right;
                }
                //side = CollisionSide.BOTTOM;
                //overlapDist = collider.box.top - box.bottom;


                Collision collision = new Collision(side, overlapDist);
                gameObject.OnCollision(collision);

                lastBox.set(gameObject.position.x, gameObject.position.y, gameObject.position.x + scaleX, gameObject.position.y + scaleY);

            }
        }
    }
}
