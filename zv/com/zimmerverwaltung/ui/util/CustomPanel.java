package com.zimmerverwaltung.ui.util;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 04.06.13
 * Time: 12:30
 * To change this template use File | Settings | File Templates.
 */

/**
 * Erweitert JPanel um die Methode setFrameContainer
 */
public class CustomPanel extends JPanel {
    protected JFrame frameContainer;

    public CustomPanel() {
        super();
    }

    public CustomPanel(LayoutManager lmgr) {
        super(lmgr);
    }

    /**
     * Macht das Frame bekannt, welches das Panel enthält
     * @deprecated Selbe Funktionalität über SwingUtilities.getRoot(this)
     * @param container Das RootFrame
     */
    public void setFrameContainer(JFrame container) {
        this.frameContainer = container;
    }

}
