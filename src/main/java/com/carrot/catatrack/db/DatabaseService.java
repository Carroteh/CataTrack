package com.carrot.catatrack.db;

import com.carrot.catatrack.model.Eye;
import com.carrot.catatrack.model.Patient;

import java.net.URL;
import java.sql.*;

/**
 * @author Philip Mathee
 * @version 1.0
 * Class that is used to interact with the database
 */
public class DatabaseService {

    /**
     * No args contructor for the Database service
     */
    public DatabaseService() {
    }

    /**
     * Method that attempts to connect to the database
     * @return Connection object
     */
    private Connection connect() {
        //URL to database
        URL url = getClass().getResource("/com/carrot/catatrack/db/catatrack.db");
        System.out.println(url);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + url);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return conn;
    }

    /**
     * Function that inserts a new patient into the database
     * @param patient the patient to insert
     * @return the patients primary key (row ID) or -1 for failure
     */
    public int insertPatient(Patient patient) {
        int PK = 0;

        //SQL statement
        String sql = "INSERT INTO Patient(id_num,surname,initials,dob,status,contact,alt_contact) VALUES (?,?,?,?,?,?,?)";

        //Prepare and execute statement
        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, patient.getId_num());
            pstmt.setString(2, patient.getSurname());
            pstmt.setString(3, patient.getInitials());
            pstmt.setDate(4, patient.getDob());
            pstmt.setString(5, patient.getStatus());
            pstmt.setString(6, patient.getContact());
            pstmt.setString(7, patient.getAlt_contact());

            PK = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            PK = -1;
        }

        return PK;
    }

    /**
     * Function to insert eye information for a patient
     * @param eye the eye top insert
     * @return the Primary Key of the new record
     */
    public int insertEye(Eye eye) {
        int PK = 0;

        //SQL statement
        String sql = """
                    INSERT INTO Eye(patient_id, side, lens, va_init, va_postop, va_2weeks, va_6weeks, va_final, surg_place, surg_date, surg_type, surg_notes)
                    VALUES(?,?,?,?,?,?,?,?,?,?,?,?)
                    """;

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eye.getPatient_id());
            pstmt.setString(2, String.valueOf(eye.getSide()));
            pstmt.setString(3, eye.getLens());
            pstmt.setString(4, eye.getVa_init());
            pstmt.setString(5, eye.getVa_postop());
            pstmt.setString(6, eye.getVa_2weeks());
            pstmt.setString(7, eye.getVa_6weeks());
            pstmt.setString(8, eye.getVa_final());
            pstmt.setString(9, eye.getSurg_place());
            pstmt.setDate(10, eye.getSurg_date());
            pstmt.setString(11, eye.getSurg_type());
            pstmt.setString(12, eye.getSurg_notes());

            PK = pstmt.executeUpdate();

        } catch (SQLException ex) {
            PK = -1;
            System.err.println(ex.getMessage());
        }

        return PK;
    }

    /**
     * Function to update a patients information
     * @param patient the new patient data
     * @param rightEye the new right eye data
     * @param leftEye the new left eye data
     * @return true for success, false for failure
     */
    public boolean editPatient(Patient patient, Eye rightEye, Eye leftEye) {
        String patientSQL = """
                    UPDATE Patient SET
                    id_num = ? ,
                    surname = ? ,
                    initials = ? ,
                    dob = ? ,
                    status = ? ,
                    contact = ? ,
                    alt_contact = ?
                    WHERE patient_id = ?
                    """;

        String rEyeSQL = """
                    UPDATE Eye SET
                    lens = ? ,
                    va_init = ? ,
                    va_postop = ?,
                    va_2weeks = ?,
                    va_6weeks = ?,
                    va_final = ?,
                    surg_date = ?,
                    surg_type = ?,
                    surg_notes = ?,
                    surg_place = ?,
                    WHERE patient_id = ? AND side = ?
                         """;

        String lEyeSQL = """
                    UPDATE Eye SET
                    lens = ? ,
                    va_init = ? ,
                    va_postop = ?,
                    va_2weeks = ?,
                    va_6weeks = ?,
                    va_final = ?,
                    surg_date = ?,
                    surg_type = ?,
                    surg_notes = ?,
                    surg_place = ?,
                    WHERE patient_id = ? AND side = ?
                         """;

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(patientSQL); ) {

            pstmt.setString(1, patient.getId_num());
            pstmt.setString(2, patient.getSurname());
            pstmt.setString(3, patient.getInitials());
            pstmt.setDate(4,  patient.getDob());
            pstmt.setString(5, patient.getStatus());
            pstmt.setString(6, patient.getContact());
            pstmt.setString(7, patient.getAlt_contact());
            pstmt.setInt(8, patient.getPatient_id());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(rEyeSQL); ) {

            pstmt.setString(1, rightEye.getLens());
            pstmt.setString(2, rightEye.getVa_init());
            pstmt.setString(3, rightEye.getVa_postop());
            pstmt.setString(4, rightEye.getVa_2weeks());
            pstmt.setString(5, rightEye.getVa_6weeks());
            pstmt.setString(6, rightEye.getVa_final());
            pstmt.setDate(7,rightEye.getSurg_date());
            pstmt.setString(8, rightEye.getSurg_type());
            pstmt.setString(9, rightEye.getSurg_notes());
            pstmt.setString(10, rightEye.getSurg_place());
            pstmt.setInt(11, patient.getPatient_id());
            pstmt.setString(12, String.valueOf('R'));

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(lEyeSQL); ) {

            pstmt.setString(1, leftEye.getLens());
            pstmt.setString(2, leftEye.getVa_init());
            pstmt.setString(3, leftEye.getVa_postop());
            pstmt.setString(4, leftEye.getVa_2weeks());
            pstmt.setString(5, leftEye.getVa_6weeks());
            pstmt.setString(6, leftEye.getVa_final());
            pstmt.setDate(7, leftEye.getSurg_date());
            pstmt.setString(8, leftEye.getSurg_type());
            pstmt.setString(9, leftEye.getSurg_notes());
            pstmt.setString(10, leftEye.getSurg_place());
            pstmt.setInt(11, patient.getPatient_id());
            pstmt.setString(12, String.valueOf('L'));

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }

        return true;
    }
}
