package com.example.hoppyfrog;

public class Time
{
    float deltaTime = 0.0f;

    private static Time instance;

    private Time() {}

    public static Time getInstance()
    {
        if(instance == null)
        {
            instance = new Time();
        }
        return instance;
    }

    void update()
    {

    }

    void setDeltaTime(float dt)
    {
        deltaTime = dt;
    }
}
