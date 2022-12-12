package com.example.hoppyfrog;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GameObject
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
            if(c.componentType == type)
            {
                return (T) c;
            }
        }
        return null;
    }

    public void OnCollision(Collision collision) {}

    public void OnCollisionExit(GameObject otherCollider) {}

    public void SetIsActive(boolean state)
    {
        isActive = state;
    }

    public void Reset() {}
}
