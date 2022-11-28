package com.example.hoppyfrog;

import android.content.Context;

import java.security.ProviderException;
import java.util.ArrayList;
import java.util.List;

public class MeteorManager
{
    Meteor[] meteors = new Meteor[10];
    List<Meteor> active = new ArrayList<>();
    List<Meteor> inActive = new ArrayList<>();

    float meteorTimer = 0.0f;
    float meteorCooldown = 3.0f;
    float maxDisplacement = 500;
    float minDisplacement = -500;

    public MeteorManager(Context context, List<GameObject> p_gameObjects)
    {
        for (int i = 0; i < 10; i++)
        {
            Meteor m = new Meteor(context, new Vector2(-1000, -1000));
            m.SetIsActive(false);
            p_gameObjects.add(m);
            inActive.add(m);
        }
    }

    public void update()
    {
        SendInActiveMeteors();
        ProcessActiveMeteors();
    }

    public void Reset()
    {
        for(int i = active.size()-1; i > -1; i--)
        {
            Meteor m = active.get(i);

            inActive.add(m);
            active.remove(m);
            m.position = new Vector2(10000000, 1000000);
            m.<BoxCollider>getComponentOfType("BOXCOLLIDER").UpdateBounds();
            m.isActive = false;
        }
    }

    void SendInActiveMeteors()
    {
        meteorTimer += Time.getInstance().deltaTime;
        if(meteorTimer >= meteorCooldown)
        {
            meteorTimer = 0.0f;
            SendMeteor();
        }
    }

    void ProcessActiveMeteors()
    {
        if(!active.isEmpty())
        {
            for(int i = active.size()-1; i > -1; i--)
            {
                Meteor m = active.get(i);

                if(m.position.y > 5000)
                {
                    m.SetIsActive(false);
                    active.remove(m);
                    inActive.add(m);
                    m.position = new Vector2(-1000, -1000);
                    m.<Movement>getComponentOfType("MOVEMENT").velocity = new Vector2(0,0);
                }
            }
        }
    }

    void SendMeteor()
    {
        if(!inActive.isEmpty())
        {
            Meteor m = inActive.get(0);
            m.SetIsActive(true);
            m.position = new Vector2( 500 + (float) Math.floor(Math.random()*(maxDisplacement-minDisplacement+1)+minDisplacement),
                                    GameView.mainCamera.position.y - 500.0f);
            inActive.remove(m);
            active.add(m);
        }
    }
}
