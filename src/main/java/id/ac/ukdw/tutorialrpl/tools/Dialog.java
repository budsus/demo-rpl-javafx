package id.ac.ukdw.tutorialrpl.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author budsus
 */
public class Dialog {

    public static void showAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Info Aplikasi");
        alert.setHeaderText("Aplikasi Tutorial RPL");
        alert.setContentText("Aplikasi ini hanya menunjukkan contoh sederhana tentang bagaimana membuat aplikasi yang mengakses database SQLite dengan UI JavaFX");

        alert.showAndWait();
    }
}
