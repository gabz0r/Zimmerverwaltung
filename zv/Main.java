import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.storage.io.CsvIO;
import com.zimmerverwaltung.ui.LoginFrame;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 25.04.13
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException,
                                                  InstantiationException,
                                                  IllegalAccessException,
                                                  UnsupportedLookAndFeelException {
        CsvIO.loadAllCsvData();
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

        LoginFrame.getLoginFrame();
    }
}
