import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private String dbName = "central_db";
    private String userName = "root";
    private String password = "admin";
    private String host = "localhost";
    private String url = "jdbc:mysql://" + host + "/" + dbName;

    public Database() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        return resultSet;
    }

    public void executeProcedure(String procName, int... params) throws SQLException {
        StringBuilder paramPlaceholder = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            if (i != 0) paramPlaceholder.append(",");
            paramPlaceholder.append("?");
        }

        String query = "{ CALL " + procName + "(" + paramPlaceholder.toString() + ") } ";
        CallableStatement cs = connection.prepareCall(query);
        
        for (int i = 0; i < params.length; i++) {
            cs.setInt(i + 1, params[i]);
        }

        cs.execute();
    }

    // Other methods (select, insert, update, delete, etc.) go here...

    public void close() throws SQLException {
        if (resultSet != null) resultSet.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
    }
}
