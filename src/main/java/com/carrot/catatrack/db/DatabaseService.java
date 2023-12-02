package com.carrot.catatrack.db;

import com.carrot.catatrack.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Philip Mathee
 * @version 1.0
 * Class that is used to interact with the database
 */
public class DatabaseService {

    public static final Logger logger = LogManager.getLogger(DatabaseService.class);

    /**
     * No args contructor for the Database service
     */
    public DatabaseService() {
    }

    /**
     * Method that attempts to connect to the database
     *
     * @return Connection object
     */
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:C:/Database/catatrack.db");
        } catch (SQLException ex) {
            logger.error("Could not connect to DB, {}", ex.getMessage());
            ex.printStackTrace();
        }
        return conn;
    }

    public int getNumPatients() {
        logger.info("Getting number of patients.");

        int numPatients = 0;

        String SQL = "SELECT COUNT(patient_id) as count FROM Patient";
        logger.info("SQL: {}", SQL);

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();

            numPatients = rs.getInt("count");

        } catch(SQLException ex) {
            ex.printStackTrace();
            logger.error("{}", ex.getMessage());
        }
        logger.info("RESULT {}", numPatients);
        return  numPatients;
    }

    public boolean insertPerson(Person person) {
        logger.info("Inserting Person");
        int PK = insertPatient(person.getPatient());
        if (PK != -1) {
            logger.info("Successfully inserted patient.");
            person.getRightEye().setPatient_id(PK);
            person.getLeftEye().setPatient_id(PK);
            int rEye = insertEye(person.getRightEye());
            int lEye = insertEye(person.getLeftEye());
            if (rEye == -1 || lEye == -1) {
                logger.error("Error while inserting eye.");
                return false;
            }
            logger.info("Successfully inserted eyes.");
        } else {
            logger.error("Error while inserting patient.");
            return false;
        }
        return true;
    }

    /**
     * Function that inserts a new patient into the database
     *
     * @param patient the patient to insert
     * @return the patients primary key (row ID) or -1 for failure
     */
    private int insertPatient(Patient patient) {
        logger.info("Inserting patient");

        int PK = 0;

        //SQL statement
        String sql = "INSERT INTO Patient(id_num,surname,initials,dob,status,contact,alt_contact) VALUES (?,?,?,?,?,?,?)";
        String pkSQL = "SELECT last_insert_rowid()";

        //Prepare and execute statement
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pkstmt = conn.prepareStatement(pkSQL)) {

            pstmt.setString(1, patient.getId_num());
            pstmt.setString(2, patient.getSurname());
            pstmt.setString(3, patient.getInitials());
            pstmt.setDate(4, patient.getDob());
            pstmt.setString(5, patient.getStatus());
            pstmt.setString(6, patient.getContact());
            pstmt.setString(7, patient.getAlt_contact());

            logger.info("SQL {}", pstmt.toString());

            pstmt.executeUpdate();
            ResultSet rs = pkstmt.executeQuery();

            while (rs.next()) {
                PK = rs.getInt(1);
                logger.info("Returned PK: {}", PK);
            }

        } catch (SQLException e) {
            logger.error("{}", e.getMessage());
            e.printStackTrace();
            PK = -1;
        }
        return PK;
    }

    /**
     * Function to insert eye information for a patient
     *
     * @param eye the eye top insert
     * @return the Primary Key of the new record
     */
    private int insertEye(Eye eye) {
        logger.info("Inserting eye");

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

            logger.info("SQL: {}", pstmt.toString());

            PK = pstmt.executeUpdate();

        } catch (SQLException ex) {
            logger.error("{}", ex.getMessage());
            PK = -1;
            ex.printStackTrace();
        }

        return PK;
    }

    /**
     * Function to update a patients information
     *
     * @param person the Person to add
     * @return true for success, false for failure
     */
    public boolean editPatient(Person person) {
        logger.info("Updating patient.");

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
                    surg_place = ?
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
                    surg_place = ?
                    WHERE patient_id = ? AND side = ?
                         """;

        //Update patient table
        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(patientSQL); ) {

            pstmt.setString(1, person.getPatient().getId_num());
            pstmt.setString(2, person.getPatient().getSurname());
            pstmt.setString(3, person.getPatient().getInitials());
            pstmt.setDate(4,  person.getPatient().getDob());
            pstmt.setString(5, person.getPatient().getStatus());
            pstmt.setString(6, person.getPatient().getContact());
            pstmt.setString(7, person.getPatient().getAlt_contact());
            pstmt.setInt(8, person.getPatient().getPatient_id());

            logger.info("SQL: {}", pstmt.toString());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            logger.error("{}", ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        logger.info("Successfully updated patient.");

        //Update Right Eye
        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(rEyeSQL); ) {

            logger.info("Updating Right eye.");

            pstmt.setString(1, person.getRightEye().getLens());
            pstmt.setString(2,  person.getRightEye().getVa_init());
            pstmt.setString(3,  person.getRightEye().getVa_postop());
            pstmt.setString(4,  person.getRightEye().getVa_2weeks());
            pstmt.setString(5,  person.getRightEye().getVa_6weeks());
            pstmt.setString(6,  person.getRightEye().getVa_final());
            pstmt.setDate(7, person.getRightEye().getSurg_date());
            pstmt.setString(8,  person.getRightEye().getSurg_type());
            pstmt.setString(9,  person.getRightEye().getSurg_notes());
            pstmt.setString(10,  person.getRightEye().getSurg_place());
            pstmt.setInt(11,  person.getPatient().getPatient_id());
            pstmt.setString(12, String.valueOf('R'));

            logger.info("SQL: {}", pstmt.toString());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            logger.error("{}", ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        //Update Left Eye
        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(lEyeSQL); ) {

            logger.info("Updating left eye.");

            pstmt.setString(1, person.getLeftEye().getLens());
            pstmt.setString(2, person.getLeftEye().getVa_init());
            pstmt.setString(3, person.getLeftEye().getVa_postop());
            pstmt.setString(4, person.getLeftEye().getVa_2weeks());
            pstmt.setString(5, person.getLeftEye().getVa_6weeks());
            pstmt.setString(6, person.getLeftEye().getVa_final());
            pstmt.setDate(7, person.getLeftEye().getSurg_date());
            pstmt.setString(8, person.getLeftEye().getSurg_type());
            pstmt.setString(9, person.getLeftEye().getSurg_notes());
            pstmt.setString(10, person.getLeftEye().getSurg_place());
            pstmt.setInt(11, person.getPatient().getPatient_id());
            pstmt.setString(12, String.valueOf('L'));

            logger.info("SQL: {}", pstmt.toString());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            logger.error("{}", ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Returns patients retrieved from a patient search
     *
     * @param surname  the patients surname
     * @param initials the patients initials
     * @param dob      the patients date of birth
     * @param id       the patients ID
     * @return an ArrayList of Persons
     */
    public ArrayList<Person> patientSearch(String surname, String initials, Date dob, String id, String status, String contact, String alt_contact) {
        ArrayList<Person> people = new ArrayList<>();

        //Query
        String patientSQL = "SELECT * FROM Patient WHERE ";

        if(surname.equals("") && initials.equals("") && DateUtils.isDefault(dob) && status.equals("N/A") && contact.equals("") && alt_contact.equals("") && id.equals("")) {
            return null;
        }
        if (!surname.equals("")) {
            patientSQL += "surname = ? AND ";
        }
        if(!initials.equals("")) {
            patientSQL += "initials = ? AND ";
        }
        if(!DateUtils.isDefault(dob)) {
            patientSQL += "dob = ? AND ";
        }
        if(!status.equals("N/A")) {
            patientSQL += "status = ? AND ";
        }
        if(!contact.equals(""))  {
            patientSQL += "contact = ? AND ";
        }
        if(!alt_contact.equals("")) {
            patientSQL+= "alt_contact = ? AND ";
        }
        if(!id.equals("")) {
            if(id.length() == 6) {
                patientSQL += "substr(id_num,1,6) = ? AND ";
            }
            else {
                patientSQL += "id_num = ? AND ";
            }
        }


        patientSQL = patientSQL.substring(0,patientSQL.length()-5);

        try(Connection conn = this.connect();
            PreparedStatement patientStatement = conn.prepareStatement(patientSQL)) {

            int param = 1;

            //Create Prepared Statement
            if (!surname.equals("")) {
                patientStatement.setString(param, surname);
                param++;
            }
            if(!initials.equals("")) {
                patientStatement.setString(param, initials);
                param++;
            }
            if(!DateUtils.isDefault(dob)) {
                patientStatement.setDate(param, dob);
                param++;
            }
            if(!status.equals("N/A")) {
                patientStatement.setString(param, status);
                param++;
            }
            if(!contact.equals("")) {
                patientStatement.setString(param, contact);
                param++;
            }
            if(!alt_contact.equals("")) {
                patientStatement.setString(param, alt_contact);
            }
            if(!id.equals("")) {
                patientStatement.setString(param, id);
            }

            logger.info("SQL: {}", patientStatement.toString());

            //Get patients that match query
            ResultSet patientResult = patientStatement.executeQuery();

            //Loop through patients
            while(patientResult.next()) {

                logger.info("Patient found: {} {}",  patientResult.getInt("patient_id"),  patientResult.getString("surname"));

                Patient patient = new Patient(
                        patientResult.getInt("patient_id"),
                        patientResult.getString("id_num"),
                        patientResult.getString("surname"),
                        patientResult.getString("initials"),
                        patientResult.getDate("dob"),
                        patientResult.getString("status"),
                        patientResult.getString("contact"),
                        patientResult.getString("alt_contact"));

                //Fetch eyes of each patient
                //RIGHT
                Eye rightEye = getEyeForPatient(conn, patient.getPatient_id(), 'R');

                //LEFT
                Eye leftEye = getEyeForPatient(conn, patient.getPatient_id(), 'L');

                //create new person and add them to the array list
                Person person = new Person(patient, rightEye, leftEye);
                people.add(person);
            }

        }catch(SQLException ex) {
            logger.error("{}", ex.getMessage());
            ex.printStackTrace();
        }
        return people;
    }

    /**
     * Function to do a general search based on surgery and VA information
     *
     * @param surg_date  the date of the surgery
     * @param month      boolean which indicates if the date represents a whole month (true) or a specific day (false)
     * @param lens       the eye lens
     * @param status     the patient status
     * @param va_final1  the final VA of an eye
     * @param va_final2  the final VA of another eye
     * @param surg_type  the surgery type
     * @param surg_place the surgery place
     * @return an ArrayList of People who matched the search results
     */
    public ArrayList<Person> generalSearch(Date surg_date, boolean month, String lens, String status, String va_final1, String va_final2, String surg_type, String surg_place) {

        ArrayList<Person> people = new ArrayList<>();
        ArrayList<Integer> uniquePatients = new ArrayList<>();

        boolean doubleVASearch = false;
        boolean va1Search = false;
        boolean va2Search = false;
        boolean pseudophakicSearch = false;

        if(DateUtils.isDefault(surg_date) && lens.equals("N/A") && status.equals("N/A") && va_final1.equals("N/A") && va_final2.equals("N/A") && surg_type.equals("N/A") && surg_place.equals("N/A")) {
            logger.info("EMPTY SEARCH.");
            return null;
        }

        if(!DateUtils.isDefault(surg_date)) {
            logger.info("PSEUDOPHAKIC SEARCH.");
            pseudophakicSearch = true;
        }

        if(!va_final1.equals("N/A") && !va_final2.equals("N/A")) {
            logger.info("DOUBLE VA SEARCH.");
            doubleVASearch = true;
        } else if (!va_final1.equals("N/A")) {
            logger.info("VA1 SEARCH.");
            va1Search = true;
        } else if (!va_final2.equals("N/A")) {
            logger.info("VA2 SEARCH.");
            va2Search = true;
        }

        //General SQL
        String generalSQL;
        //Search without status
        if(status.equals("N/A")) {
            //Search with two VAs
            if(doubleVASearch) {
                generalSQL = "SELECT Eye.* FROM (SELECT * FROM Eye WHERE Eye.va_final = ?)VA1, Eye ";
                generalSQL += "WHERE Eye.va_final = ? AND Eye.patient_id = VA1.patient_id AND ";
            }
            else {
                generalSQL = "SELECT * FROM Eye WHERE ";
            }
        }
        //Search with status
        else {
            //Search with 2 VAs
            if(doubleVASearch) {
                generalSQL = "SELECT Eye.* FROM (SELECT * FROM Eye WHERE Eye.va_final = ?)VA1, Eye ";
                generalSQL += "Eye INNER JOIN Patient ON Eye.patient_id = Patient.patient_id ";
                generalSQL += "WHERE Eye.va_final = ? AND Eye.patient_id = VA1.patient_id AND status = ? AND ";
            }else {
                generalSQL = "SELECT Eye.* FROM Eye INNER JOIN Patient ON Eye.patient_id = Patient.patient_id WHERE ";
                generalSQL += "status = ? AND ";
            }
        }

        if(va1Search || va2Search) {
            generalSQL += "Eye.va_final = ? AND ";
        }
        if(!DateUtils.isDefault(surg_date)) {
            if(month) {
                if(doubleVASearch) {
                    generalSQL += "(strftime('%Y-%m', Eye.surg_date / 1000, 'unixepoch') = ? OR strftime('%Y-%m', VA1.surg_date / 1000, 'unixepoch') = ?) AND ";
                }
                else {
                    generalSQL += "strftime('%Y-%m', Eye.surg_date / 1000, 'unixepoch') = ? AND ";
                }
            }
            else {
                if(doubleVASearch) {
                    generalSQL += "(Eye.surg_date = ? OR VA1.surg_date = ?) AND ";
                }
                else {
                    generalSQL += "Eye.surg_date = ? AND ";
                }
            }
        }
        if(!lens.equals("N/A")) {
            if(doubleVASearch) {
                generalSQL += "(Eye.lens = ? OR VA1.lens = ?) AND ";
            }
            else {
                generalSQL += "Eye.lens = ? AND ";
            }
        }
        if(!surg_type.equals("N/A")) {
            if(doubleVASearch) {
                generalSQL += "(Eye.surg_type = ? OR VA1.surg_type = ?) AND ";
            }
            else {
                generalSQL += "Eye.surg_type = ? AND ";
            }
        }
        if(!surg_place.equals("N/A")) {
            if(doubleVASearch) {
                generalSQL += "(Eye.surg_place = ? OR VA1.surg_place = ?) AND ";
            }
            else {
                generalSQL += "Eye.surg_place = ? AND ";
            }
        }

        generalSQL = generalSQL.substring(0,generalSQL.length()-5);

        logger.debug("SQLLITE, {}", generalSQL);

        try(Connection conn = this.connect();
            PreparedStatement generalStatement = conn.prepareStatement(generalSQL)) {

            int param = 1;

            //WITHOUT STATUS
            if(status.equals("N/A")) {
                //WITH DOUBLE VA
                if(doubleVASearch) {
                    generalStatement.setString(param, va_final1);
                    param++;
                    generalStatement.setString(param, va_final2);
                    param++;
                }
            }
            //WITH STATUS
            else {
                //WITH DOUBLE VA
                if(doubleVASearch) {
                    generalStatement.setString(param, va_final1);
                    param++;
                    generalStatement.setString(param, va_final2);
                    param++;
                    generalStatement.setString(param, status);
                }
                else {
                    generalStatement.setString(param, status);
                }
                param++;
            }

            if(va1Search) {
                generalStatement.setString(param, va_final1);
                param++;
            }
            else if (va2Search){
                generalStatement.setString(param, va_final2);
                param++;
            }
            //Set general parameters dynamically
            if(!DateUtils.isDefault(surg_date)) {
                String strDate = surg_date.toString();
                if(month) {
                    generalStatement.setString(param, strDate.substring(0,7));
                    if(doubleVASearch) {
                        param++;
                        generalStatement.setString(param, strDate.substring(0,7));
                    }
                }
                else {
                    generalStatement.setDate(param, surg_date);
                    if(doubleVASearch) {
                        param++;
                        generalStatement.setDate(param, surg_date);
                    }
                }
                param++;
            }
            if(!lens.equals("N/A")) {
                generalStatement.setString(param, lens);
                param++;
                if(doubleVASearch) {
                    generalStatement.setString(param, lens);
                    param++;
                }
            }
            if(!surg_type.equals("N/A")) {
                generalStatement.setString(param, surg_type);
                param++;
                if(doubleVASearch) {
                    generalStatement.setString(param, surg_type);
                    param++;
                }
            }
            if(!surg_place.equals("N/A")) {
                generalStatement.setString(param, surg_place);
                if(doubleVASearch) {
                    param++;
                    generalStatement.setString(param, surg_place);
                }
            }

            logger.info("SQL: {}", generalStatement.toString());

            //Execute general query
            ResultSet generalResult = generalStatement.executeQuery();

            boolean duplicate;
            //Loop through results
            while(generalResult.next()) {
                duplicate = false;

                //Check for duplicates
                for(Integer i : uniquePatients) {
                    if(i.equals(generalResult.getInt("patient_id"))) {
                        duplicate = true;
                    }
                }
                if(duplicate) {continue;}

                //Create eye
                Eye eye = new Eye(
                        generalResult.getInt("patient_id"),
                        generalResult.getString("side").charAt(0),
                        generalResult.getString("lens"),
                        generalResult.getString("va_init"),
                        generalResult.getString("va_postop"),
                        generalResult.getString("va_2weeks"),
                        generalResult.getString("va_6weeks"),
                        generalResult.getString("surg_place"),
                        generalResult.getDate("surg_date"),
                        generalResult.getString("surg_type"),
                        generalResult.getString("surg_notes")
                );

                logger.info("Eye found: {}", generalResult.getInt("patient_id"));

                //Add unique patient ID to list
                uniquePatients.add(generalResult.getInt("patient_id"));

                Patient patient = getPatientByID(conn, eye.getPatient_id());

                //Skip bilateral pseudophakic
                if((!pseudophakicSearch) && patient.getStatus().equals("Bilateral pseudophakic")) {
                    continue;
                }

                //Find Left or Right eye depending on the search
                if(eye.getSide() == 'R') {
                    Eye leftEye = getEyeForPatient(conn, eye.getPatient_id(), 'L');
                    Person person = new Person(patient, eye, leftEye);
                    people.add(person);
                }
                else {
                    Eye rightEye = getEyeForPatient(conn, eye.getPatient_id(), 'R');
                    Person person = new Person(patient, rightEye, eye);
                    people.add(person);
                }
            }
        }
        catch(SQLException ex) {
            logger.error("{}", ex.getMessage());
            ex.printStackTrace();
        }
        return people;
    }

    /**
     * Function that retrieves a specific patient
     *
     * @param conn       the database connection
     * @param patient_id the patient id
     * @return the Patient
     * @throws SQLException
     */
    private Patient getPatientByID(Connection conn, int patient_id) throws SQLException {
        logger.info("Getting Patient by patient_id.");

        String patSQL = "SELECT * FROM Patient WHERE patient_id = ?";

        PreparedStatement patStatement = conn.prepareStatement(patSQL);

        patStatement.setInt(1, patient_id);

        logger.info("SQL: {}", patStatement.toString());

        ResultSet rs = patStatement.executeQuery();

        return new Patient(
            rs.getInt("patient_id"),
            rs.getString("id_num"),
            rs.getString("surname"),
            rs.getString("initials"),
            rs.getDate("dob"),
            rs.getString("status"),
            rs.getString("contact"),
            rs.getString("alt_contact")
        );
    }

    /**
     * Function that retrieves a specific eye from a patient id
     *
     * @param conn       the database connection
     * @param patient_id the patient ID
     * @param side       the eye side
     * @return the Eye
     * @throws SQLException
     */
    private Eye getEyeForPatient(Connection conn, int patient_id, char side) throws SQLException {
        logger.info("Getting Eye for Patient.");

        String eyeSQL = "SELECT * FROM Eye WHERE patient_id = ? AND side = ?";

        PreparedStatement eyeStatement = conn.prepareStatement(eyeSQL);

        eyeStatement.setInt(1, patient_id);
        eyeStatement.setString(2, String.valueOf(side));

        logger.info("SQL: {}", eyeStatement.toString());

        ResultSet rs = eyeStatement.executeQuery();

        return new Eye(
                rs.getInt("patient_id"),
                rs.getString("side").charAt(0),
                rs.getString("lens"),
                rs.getString("va_init"),
                rs.getString("va_postop"),
                rs.getString("va_2weeks"),
                rs.getString("va_6weeks"),
                rs.getString("surg_place"),
                rs.getDate("surg_date"),
                rs.getString("surg_type"),
                rs.getString("surg_notes")
        );
    }
}
