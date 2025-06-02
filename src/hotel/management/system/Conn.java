package hotel.management.system;

import java.sql.*;
//
//public class Conn {
//    Connection c;
//    Statement s;
//    
//    public Conn() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/hmsv", "root", "");
//            s = c.createStatement();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//package hotel.management.system;
//
//import java.sql.*;

public class Conn {
    public Connection con; 
    public Statement s;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con =DriverManager.getConnection("jdbc:mysql:///hmsv","root",""); 
            s = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}