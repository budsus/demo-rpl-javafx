package id.ac.ukdw.tutorialrpl;

import id.ac.ukdw.tutorialrpl.db.MahasiswaDAO;
import id.ac.ukdw.tutorialrpl.model.Mahasiswa;
import id.ac.ukdw.tutorialrpl.tools.Dialog;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

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

    @FXML
    private Button btnTambah;

    private Alert formMahasiswaRoot;

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

        menuAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dialog.showAbout();
            }
        });

        // Definisikan context menu untuk setiap baris pada TableView
        final ContextMenu cxMenu = new ContextMenu();
        MenuItem cxMenuItemEdit = new MenuItem("Ubah Data");
        cxMenu.getItems().add(cxMenuItemEdit);
        MenuItem cxMenuItemDelete = new MenuItem("Hapus Data");
        cxMenu.getItems().add(cxMenuItemDelete);

        // tambahkan event handler ketika klik kanan terjadi pada TableView
        tabelMahasiswa.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getButton() == MouseButton.SECONDARY) {
                    cxMenu.show(tabelMahasiswa, t.getScreenX(), t.getScreenY());
                }
                if (t.getButton() == MouseButton.PRIMARY) {
                    if (cxMenu.isShowing()) {
                        cxMenu.hide();
                    }
                }
            }
        });

        tabelMahasiswa.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                if (evt.getCode().equals(KeyCode.ESCAPE)) {
                    cxMenu.hide();
                }
            }
        });

        btnTambah.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openFormMahasiswa(true, new Mahasiswa("", "", ""));
            }
        });

        cxMenuItemEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Mahasiswa mhs = tabelMahasiswa.getSelectionModel().getSelectedItem();
                openFormMahasiswa(false, mhs);
            }
        });
        
        cxMenuItemDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Mahasiswa mhs = tabelMahasiswa.getSelectionModel().getSelectedItem();
                int index = tabelMahasiswa.getSelectionModel().getSelectedIndex();
                try {
                    MahasiswaDAO.deleteMahasiswaWithId(mhs.getNim());
                    tabelMahasiswa.getItems().remove(index);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(UtamaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    private void openFormMahasiswa(boolean isAdd, final Mahasiswa mhs) {
        int index = 0;
        if (!isAdd) {
            index = tabelMahasiswa.getSelectionModel().getSelectedIndex();
        }
        if (formMahasiswaRoot == null) {
            formMahasiswaRoot = new Alert(INFORMATION);
            formMahasiswaRoot.setTitle("Mahasiswa");
            formMahasiswaRoot.setHeaderText("Form Mahasiswa");
            formMahasiswaRoot.initModality(Modality.WINDOW_MODAL);
            ButtonType btnSimpan = new ButtonType("Simpan");
            ButtonType btnBatal = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
            formMahasiswaRoot.getButtonTypes().setAll(btnSimpan, btnBatal);

            FormMahasiswaController mhsController = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormMahasiswa.fxml"));
            try {
                Node content = loader.load();
                DialogPane pane = formMahasiswaRoot.getDialogPane();
                pane.setContent(content);
                mhsController = loader.getController();
                mhsController.setMahasiswa(mhs);
            } catch (IOException ex) {
                Logger.getLogger(UtamaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Optional<ButtonType> result = formMahasiswaRoot.showAndWait();
            if (result.get() == btnSimpan) {
                if (mhsController != null){
                    if (isAdd) {
                        // tambahkan data ke tabel
                        Mahasiswa mhsUpdated = mhsController.getMahasiswa();
                        try {
                            MahasiswaDAO.addMahasiswa(mhsUpdated);
                            tabelMahasiswa.getItems().add(mhsUpdated);
                        } catch (SQLException | ClassNotFoundException ex) {
                            Logger.getLogger(UtamaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        String nimOld = mhs.getNim();
                        Mahasiswa mhsUpdated = mhsController.getMahasiswa();
                        try {
                            ObservableList<Mahasiswa> listMahasiswa = tabelMahasiswa.getItems();
                            MahasiswaDAO.updateMahasiswa(nimOld, mhsUpdated);
                            listMahasiswa.set(index, mhsUpdated);
                        } catch (SQLException | ClassNotFoundException ex) {
                            Logger.getLogger(UtamaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                formMahasiswaRoot = null;
            } else if (result.get() == btnBatal) {
                formMahasiswaRoot = null;
            }
        }
    }

}
