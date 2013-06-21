package com.zimmerverwaltung.ui.dispatcher;

import java.util.*;

/**
 * Klasse zum weiterleiten von Events
 */
public class EventDispatcher extends Observable {
    private static EventDispatcher instance;

    private EventDispatcher() {
        super();
    }

    /**
     * Verteilt eine Nachricht an alle Observer
     */
    public void dispatch(EventTargets target, Object message) {
        setChanged();
        notifyObservers(new DispatcherObject(target, message));
    }

    /**
     * Singleton
     * @return Instanz
     */
    public static EventDispatcher getInstance() {
        if(instance == null) {
            instance = new EventDispatcher();
        }
        return instance;
    }

    public void registerObserver(Observer o) {
        addObserver(o);
    }

    public void deleteObserver(Observer o) {
        deleteObserver(o);
    }
}
