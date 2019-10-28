package id.ac.ukdw.tutorialrpl;

import id.ac.ukdw.tutorialrpl.model.Mahasiswa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author budsus
 */
public class FormMahasiswaController implements Initializable {
    private Mahasiswa mahasiswa;
    
    @FXML
    private TextField txtNIM;
    
    @FXML
    private TextField txtNama;
    
    @FXML
    private TextField txtEmail;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void setMahasiswa(Mahasiswa mhsOld) {
        this.mahasiswa = mhsOld;
        txtNIM.setText(mahasiswa.getNim());
        txtNama.setText(mahasiswa.getNama());
        txtEmail.setText(mahasiswa.getEmail());
    }
    
    public Mahasiswa getMahasiswa() {
        this.mahasiswa.setNim(txtNIM.getText());
        this.mahasiswa.setNama(txtNama.getText());
        this.mahasiswa.setEmail(txtEmail.getText());
        return this.mahasiswa;
    }
}
