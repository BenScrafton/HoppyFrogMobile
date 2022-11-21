package com.example.hoppyfrog;

import java.util.List;

public class Layer
{
    List<GameObject> gameObjects;
    float paralaxFactor;

    public Layer(List<GameObject> p_gameObjects, float p_paralaxFactor)
    {
        gameObjects = p_gameObjects;
        paralaxFactor = p_paralaxFactor;
    }
}
