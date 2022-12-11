package com.example.hoppyfrog;

import java.util.List;

public class Layer
{
    public List<GameObject> gameObjects;
    public boolean ignoreCameraTranslation;

    public Layer(List<GameObject> p_gameObjects, boolean p_ignoreCameraTranslation)
    {
        gameObjects = p_gameObjects;
        ignoreCameraTranslation = p_ignoreCameraTranslation;
    }
}
