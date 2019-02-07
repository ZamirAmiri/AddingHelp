/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addinghelp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author zamir
 */
public class DBConnectAdmin {

    private static final String host = "jdbc:mysql://localhost:3306/addinghelp";
    private final String uName;
    private final String uPass;
    private ResultSet rs;
    private int error;
    public DBConnectAdmin(String uname, String password) {
        this.uName = uname;
        this.uPass = password;
        
    }

    public void quickInsert(String query) {
        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            //here sonoo is database name, root is username and password
            try (Connection con = DriverManager.getConnection(host,uName,uPass)) {
                //here sonoo is database name, root is username and password
                Statement stmt=con.createStatement();
                stmt.executeUpdate(query);
            }
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e);
        }
    }
    
}
