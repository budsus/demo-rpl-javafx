package id.ac.ukdw.tutorialrpl.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author budsus
 */
public class Mahasiswa {
    
    
    public Mahasiswa(){
    }
    
    public Mahasiswa(String nim, String nama, String email) {
        this.nim = new SimpleStringProperty(nim);
        this.nama = new SimpleStringProperty(nama);
        this.email = new SimpleStringProperty(email);
    }

    /**
     * @return the nim
     */
    public String getNim() {
        return nim.get();
    }

    /**
     * @param nim the nim to set
     */
    public void setNim(String nim) {
        this.nim = new SimpleStringProperty(nim);
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return nama.get();
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = new SimpleStringProperty(nama);
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email.get();
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }
    
    @Override
    public String toString() {
        return getNim() + " " + getNama() + " " + getEmail();
    }
    
    private SimpleStringProperty nim;
    private SimpleStringProperty nama;
    private SimpleStringProperty email;
    
}
