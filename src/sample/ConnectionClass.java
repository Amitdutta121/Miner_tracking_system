package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
    public Connection connection;
    public Connection getConnection(){
        String dbName = "data";
        String userName = "amit";
        String pass = "amit";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection  = DriverManager.getConnection("jdbc:mysql://localhost:3306/data?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",userName,pass);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return connection;

    }
}
