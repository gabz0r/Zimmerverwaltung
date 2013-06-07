package com.zimmerverwaltung.ui.util;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 04.06.13
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */
public abstract class CustomFrame extends JFrame {
    public CustomFrame() {
        super();
    }

    public abstract void close();
}
