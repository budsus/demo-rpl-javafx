package id.ac.ukdw.tutorialrpl;

import id.ac.ukdw.tutorialrpl.db.MahasiswaDAO;
import id.ac.ukdw.tutorialrpl.model.Mahasiswa;
import id.ac.ukdw.tutorialrpl.tools.Dialog;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author budsus
 */
public class UtamaController implements Initializable {

    @FXML
    private TableView<Mahasiswa> tabelMahasiswa;

    @FXML
    private TableColumn<Mahasiswa, String> colNIM;

    @FXML
    private TableColumn<Mahasiswa, String> colNama;

    @FXML
    private TableColumn<Mahasiswa, String> colEmail;

    @FXML
    private MenuItem menuClose;
    
    @FXML
    private MenuItem menuAbout;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNIM.setCellValueFactory(new PropertyValueFactory("nim"));
        colNama.setCellValueFactory(new PropertyValueFactory("nama"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));

        ObservableList<Mahasiswa> data;
        try {
            data = MahasiswaDAO.searchMahasiswas();
            tabelMahasiswa.setItems(data);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UtamaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //resize TableColumn width
        colNama.prefWidthProperty().bind(tabelMahasiswa.widthProperty().divide(3));
        colEmail.prefWidthProperty().bind(tabelMahasiswa.widthProperty().divide(3));

        menuClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        
        menuAbout.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Dialog.showAbout();
            }
        });
    }

}
