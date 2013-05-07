import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.storage.io.CsvIO;
import com.zimmerverwaltung.ui.LoginFrame;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 25.04.13
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        CsvIO.loadAllCsvData();
        LoginFrame.getLoginFrame();
    }
}
