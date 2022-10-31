package com.example.hoppyfrog.observers;

import com.example.hoppyfrog.GameObject;
import com.example.hoppyfrog.observers.events.Event;

public interface Observer {
    void onNotify(GameObject object, Event event);
}
