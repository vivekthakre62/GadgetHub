/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vivek
 */
public class DBUtil {
private static Connection conn;
public static void openConnection(String url,String name,String password){
   if(conn==null){
    try{
        
                Class.forName("oracle.jdbc.driver.OracleDriver");
        conn=DriverManager.getConnection(url,name,password);
        System.out.println("gadgethub connection opened!");
    }
    
    catch(SQLException sq){
        System.out.println("gadgethub Error in Opening connection");
        sq.printStackTrace();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
   }
}
public static Connection provideConnection(){
    return conn;
}
public static void closeConnection(){
    if(conn!=null){
    try {
        conn.close();
    } catch (SQLException ex) {
       System.out.println("Error in closing connection");
      ex.printStackTrace();
    }
    }
}
public static void closeStatement(Statement st){
    if(st!=null){
         try {
        st.close();
         }
    catch (SQLException ex) {
       System.out.println("Error in closing statement");
      ex.printStackTrace();
    }
    }
    }
public static void closeResultSet(ResultSet rs){
    if(rs!=null){
        try {
        rs.close();
         }
    catch (SQLException ex) {
       System.out.println("Error in closing statement");
      ex.printStackTrace();
    }
    }
}

}
