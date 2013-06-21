import com.zimmerverwaltung.storage.io.CsvIO;
import com.zimmerverwaltung.ui.LoginFrame;

import javax.swing.*;

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
