package com.imralav.gmtools.battletracker.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class UnitClickedEvent extends Event {
    public UnitClickedEvent(EventType<UnitClickedEvent> eventType) {
        super(eventType);
    }

    public UnitClickedEvent(Object source, EventTarget target, EventType<UnitClickedEvent> eventType) {
        super(source, target, eventType);
    }
}
