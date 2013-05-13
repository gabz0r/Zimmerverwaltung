package com.zimmerverwaltung.ui.dispatcher;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 13.05.13
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
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
