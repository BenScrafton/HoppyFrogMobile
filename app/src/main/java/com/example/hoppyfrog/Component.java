package com.example.hoppyfrog;

enum ComponentType
{
    ANIMATOR,
    AUDIO_SOURCE,
    BOX_COLLIDER,
    CAMERA,
    FOLLOW_OBJECT,
    GRAVITY,
    MOVEMENT,
    UI_TEXT,
    VERTICAL_DISTANCE_RECORDER
}

public class Component
{
    public GameObject gameObject;
    public boolean isActive = true;
    public String id = "NONE";
    public ComponentType componentType;

    public void update()
    {

    }
}
