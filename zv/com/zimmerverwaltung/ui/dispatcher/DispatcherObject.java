package com.zimmerverwaltung.ui.dispatcher;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 13.05.13
 * Time: 11:26
 * To change this template use File | Settings | File Templates.
 */

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
