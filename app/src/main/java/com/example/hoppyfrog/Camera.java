package com.example.hoppyfrog;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;

import java.util.List;

public class Camera extends Component
{
    int ignoreTranslation[];

    public Camera(GameObject p_gameObject)
    {
        id = "CAMERA";
        gameObject = p_gameObject;
    }

    public void render(Canvas canvas)
    {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        for(Layer layer : GameView.layers)
        {
            Vector2 offset = new Vector2(-1 * gameObject.position.x, -1 * gameObject.position.y);
            if(layer.ignoreCameraTranslation)
            {
                offset = new Vector2(0,0);
            }

            for(GameObject g : layer.gameObjects)
            {
                if(!g.isActive)
                {
                    continue;
                }

                if(g.<Animator>getComponentOfType("ANIMATOR") != null)
                {
                    g.<Animator>getComponentOfType("ANIMATOR").draw(canvas, offset);
                }

                if(g.<UItext>getComponentOfType("UI_TEXT") != null)
                {
                    g.<UItext>getComponentOfType("UI_TEXT").render(canvas);
                }
            }
        }
    }
}
