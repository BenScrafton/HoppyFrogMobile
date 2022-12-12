package com.example.hoppyfrog;

import com.example.hoppyfrog.observers.Observer;
import com.example.hoppyfrog.observers.events.Event;
import com.example.hoppyfrog.observers.events.EventType;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GameObject implements Observer
{
    String tag = "";
    Vector2 position = new Vector2(0,0);
    ArrayList<Component> components  = new ArrayList<>();
    boolean isActive = true;

    public void update()
    {
        if(!isActive)
        {
            return;
        }

        for(Component c : components)
        {
            c.update();
        }
    }

    public <T> T getComponentOfType(ComponentType type)
    {
        for(Component c : components)
        {
            //if(c.id == id)
            //{
                //return (T) c;
            //}

            if(c.componentType == type)
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

    public void OnCollision(Collision collision) {}

    public void OnCollisionExit(GameObject otherCollider) {}

    public void SetIsActive(boolean state)
    {
        isActive = state;
    }

    public void Reset() {}
}
