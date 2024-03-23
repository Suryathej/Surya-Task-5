import java.sql.ResultSet;
import java.sql.SQLException;

public class StockLedgerEntry {
    private String query;

    public StockLedgerEntry() {
        query = null;
    }

    public ResultSet getInfo(String fld, String tbl) throws SQLException {
        query = "SELECT DISTINCT(" + fld + ") FROM " + tbl;
        return executeQuery(query);
    }

    public ResultSet getInfo(String fld1, String fld2, String tbl) throws SQLException {
        query = "SELECT " + fld1 + ", " + fld2 + " FROM " + tbl + " ORDER BY " + fld2;
        return executeQuery(query);
    }

    public ResultSet getCentralStocInfo(String viewName) throws SQLException {
        query = "SELECT * FROM " + viewName;
        return executeQuery(query);
    }

    public ResultSet getUserProfile(String userPk) throws SQLException {
        query = "SELECT full_name, employee_code, designation_name, dept_name, sex " +
                "FROM employee, employee_personal_info, employee_status, designation, department " +
                "WHERE employee_pk = employee_personal_pk_fk " +
                "AND employee_pk = employee_status_pk_fk " +
                "AND designation_pk = designation_fk " +
                "AND department_fk = dept_pk " +
                "AND employee_pk = " + userPk;
        return executeQuery(query);
    }

    public ResultSet getPrescriptionDate(String stdPk) throws SQLException {
        query = "SELECT std_prescription_pk, prescription_dt FROM std_prescription_info " +
                "WHERE student_pk_fk = " + stdPk + " ORDER BY prescription_dt DESC";
        return executeQuery(query);
    }

    public ResultSet getDiagnosisDetails(String presPk) throws SQLException {
        query = "SELECT prescription_dt, diagnosis_detail, reconsult_dt, general_advice, full_name " +
                "FROM std_prescription_info, employee_personal_info " +
                "WHERE std_prescription_pk = " + presPk +
                "AND emplaoyee_pk_fk = employee_personal_pk_fk";
        return executeQuery(query);
    }

    public ResultSet getPatientMedicine(String presPk) throws SQLException {
        query = "SELECT med_type, med_com_name, med_weight, no_of_doses, day_duration, med_qty, medication_inst_text " +
                "FROM patient_med_info, medicine_gen_info, medicine_com_info, medication_inst_detail " +
                "WHERE std_prescription_fk = " + presPk +
                "AND med_com_name_fk = med_com_name_pk " +
                "AND med_gen_name_fk = med_gen_name_pk " +
                "AND medication_inst_fk = medication_inst_pk " +
                "ORDER BY med_type ASC";
        return executeQuery(query);
    }

    private ResultSet executeQuery(String query) throws SQLException {
        ResultSet resultSet = null;
        try {
            database db = new database();
            resultSet = db.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
