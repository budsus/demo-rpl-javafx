package id.ac.ukdw.tutorialrpl.db;

import id.ac.ukdw.tutorialrpl.model.Mahasiswa;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author budsus
 */
public class MahasiswaDAO {

    public static Mahasiswa searchMahasiswa(String nim) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM mahasiswa WHERE nim='" + nim + "'";
        try {
            ResultSet rsMhs = DBUtil.getInstance().dbExecuteQuery(selectStmt);
            Mahasiswa mahasiswa = getMahasiswaFromResultSet(rsMhs);
            return mahasiswa;
        } catch (SQLException e) {
            System.out.println("Sedang mencari mahasiswa dengan nim " + nim + ", error terjadi: " + e);
            throw e;
        }
    }

    private static Mahasiswa getMahasiswaFromResultSet(ResultSet rs) throws SQLException {
        Mahasiswa mhs = null;
        if (rs.next()) {
            mhs = new Mahasiswa();
            mhs.setNim(rs.getString("NIM"));
            mhs.setNama(rs.getString("NAMA"));
            mhs.setEmail(rs.getString("EMAIL"));
        }
        return mhs;
    }

    public static ObservableList<Mahasiswa> searchMahasiswas() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM mahasiswa";
        try {
            ResultSet rsMhs = DBUtil.getInstance().dbExecuteQuery(selectStmt);
            ObservableList<Mahasiswa> mhsList = getMahasiswaList(rsMhs);
            return mhsList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e); //Return exception
            throw e;
        }
    }

    private static ObservableList<Mahasiswa> getMahasiswaList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Mahasiswa> mhsList = FXCollections.observableArrayList();
        while (rs.next()) {
            Mahasiswa mhs = new Mahasiswa();
            mhs.setNim(rs.getString("NIM"));
            mhs.setNama(rs.getString("NAMA"));
            mhs.setEmail(rs.getString("EMAIL"));
            mhsList.add(mhs);
        }
        return mhsList;
    }

    public static void updateMahasiswa(String nim, Mahasiswa mhs) throws SQLException, ClassNotFoundException {
        String updateStmt = "UPDATE mahasiswa SET nim='" + mhs.getNim() + "',"
                + "nama = '" + mhs.getNama() + "',"
                + "email = '" + mhs.getEmail() + "' WHERE nim='" + nim + "'";
        try {
            DBUtil.getInstance().dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            throw e;
        }
    }

    public static void addMahasiswa(Mahasiswa mhs) throws SQLException, ClassNotFoundException {
        String updateStmt = "INSERT INTO mahasiswa (nim, nama, email) VALUES ('" + mhs.getNim() + "', "
                + "'" + mhs.getNama() + "', "
                + "'" + mhs.getEmail() + "')";
        try {
            DBUtil.getInstance().dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            throw e;
        }
    }

    public static void deleteMahasiswaWithId(String nim) throws SQLException, ClassNotFoundException {
        String updateStmt = "DELETE FROM mahasiswa WHERE nim='" + nim + "'";
        try {
            DBUtil.getInstance().dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            throw e;
        }
    }
}
