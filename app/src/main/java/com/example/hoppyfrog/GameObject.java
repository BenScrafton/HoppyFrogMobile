package com.example.hoppyfrog;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GameObject
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
}
