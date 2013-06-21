package com.zimmerverwaltung.ui.dispatcher;

/**
 * Parameter für den Dispatcher
 * wird im Rahmen des Observer - Interfaces übergeben
 */
public class DispatcherObject {
    private EventTargets target;
    private Object content;

    /**
     * Konstruktor, gibt das Ziel für den Dispatcher an und den Parameter
     * @param target Ziel des Dispatchvorgangs
     * @see EventTargets
     * @param content Parameter
     */
    public DispatcherObject(EventTargets target, Object content) {
        this.target = target;
        this.content = content;
    }

    public EventTargets getTarget() {
        return target;
    }

    public Object getContent() {
        return content;
    }
}
