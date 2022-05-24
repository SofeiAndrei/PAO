package Jdbc;

import java.sql.*;

public class JdbcConnection {
    private Connection connection;
    public JdbcConnection(){
        String url = "jdbc:mysql://localhost:1604/pao";
        String username = "root";
        String password = "sofeiandrei";
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet ExecuteSelectQuery(String querry) throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet rs = statement.executeQuery(querry);
        return rs;
    }
    public void ExecuteCreateUpdateDeleteQuery(String querry) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.executeUpdate(querry);
    }
}
