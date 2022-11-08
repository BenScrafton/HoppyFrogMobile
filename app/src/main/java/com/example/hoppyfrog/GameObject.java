package com.example.hoppyfrog;

import com.example.hoppyfrog.observers.Observer;
import com.example.hoppyfrog.observers.events.Event;
import com.example.hoppyfrog.observers.events.EventType;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GameObject implements Observer
{
    Vector2 position = new Vector2(0,0);
    Vector2 scale = new Vector2(200, 200);
    ArrayList<Component> components  = new ArrayList<>();

    public void update()
    {
        for(Component c : components)
        {
            c.update();
        }
    }

    public <T> T getComponentOfType(String id)
    {
        for(Component c : components)
        {
            if(c.id == id)
            {
                return (T) c;
            }
        }

        return null;
    }

    @Override
    public void onNotify(GameObject object, Event event)
    {
        if(event.eventType == EventType.ON_GROUNDED)
        {

        }
    }

    public void OnCollision(Collision collision)
    {

    }

    public void OnCollisionExit(CollisionSide side)
    {

    }

}
