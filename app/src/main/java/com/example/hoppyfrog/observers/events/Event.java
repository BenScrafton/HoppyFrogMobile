package com.example.hoppyfrog.observers.events;

public class Event {
    public EventType eventType;

    public Event(EventType type)
    {
        this.eventType = type;
    }

    public Event()
    {
        this.eventType = EventType.USER_EVENT;
    }
}
