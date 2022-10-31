package com.example.hoppyfrog;

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

    BoxCollider(GameObject p_gameObject, float p_scaleX, float p_scaleY)
    {
        gameObject = p_gameObject;
        scaleX = p_scaleX;
        scaleY = p_scaleY;
        box = new RectF(gameObject.position.x, gameObject.position.y, gameObject.position.x + scaleX, gameObject.position.y + scaleY);
        boxColliders.add(this);
    }

    @Override
    public void update()
    {
        super.update();

        UpdateBounds();
        CheckCollisions();
    }

    void UpdateBounds()
    {
        box.set(gameObject.position.x, gameObject.position.y, gameObject.position.x + scaleX, gameObject.position.y + scaleY);
    }

    void CheckCollisions()
    {
        for(BoxCollider collider : boxColliders)
        {
            if(collider != this)
            {
                if((this.box.right > collider.box.left) && (this.box.left < collider.box.right) &&
                        (this.box.top < collider.box.bottom) && (this.box.bottom > collider.box.top))
                {
                    if(gameObject.<Gravity>getComponentOfType("GRAVITY") != null)
                    {
                        if(gameObject.<Gravity>getComponentOfType("GRAVITY").grounded = true)
                        {
                            gameObject.<Gravity>getComponentOfType("GRAVITY").grounded = true;
                        }
                    }
                }
            }
        }
    }
}
