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
public class CustomPanel extends JPanel {
    protected JFrame frameContainer;

    public CustomPanel() {
        super();
    }

    public CustomPanel(LayoutManager lmgr) {
        super(lmgr);
    }

    public void setFrameContainer(JFrame container) {
        this.frameContainer = container;
    }

}
