package com.example.hoppyfrog;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BoxCollider extends Component
{
    private RectF box;
    private float scaleX;
    private float scaleY;
    private static List<BoxCollider> boxColliders = new ArrayList<BoxCollider>();
    private RectF lastBox;
    private boolean isStatic = false;
    private List<GameObject> curColliders = new ArrayList<GameObject>();

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
        if(isActive)
        {
            UpdateBounds();
            CheckCollisions();
            lastBox.set((float) box.left, (float) box.top, (float) box.right, (float) box.bottom);
        }
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

    void CheckCollisions()
    {
        if(isStatic)
        {
            return;
        }

        boolean collidedAtAll = false;
        boolean collided = false;
        List<GameObject> tempCurColliders = new ArrayList<>();

        for(BoxCollider collider : boxColliders) //On Collision Enter
        {
            collided = false;

            if (collider != this)
            {
                if ((this.box.right >= collider.box.left) && (this.box.left <= collider.box.right) &&
                        (this.box.top <= collider.box.bottom) && (this.box.bottom >= collider.box.top))
                {
                    collided = true;
                    collidedAtAll = true;
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

                Collision collision = new Collision(side, overlapDist, collider.gameObject);
                gameObject.OnCollision(collision);

                if(!curColliders.contains(collider.gameObject))
                {
                    curColliders.add(collider.gameObject);
                }

                tempCurColliders.add(collider.gameObject);
            }
        }

        for(GameObject g : curColliders) //On Collision Exit
        {
            if(!tempCurColliders.contains(g))
            {
                gameObject.OnCollisionExit(g);
            }
        }

        if(collidedAtAll)
        {
            lastBox.set(gameObject.position.x, gameObject.position.y, gameObject.position.x + scaleX, gameObject.position.y + scaleY);
        }
    }
}
