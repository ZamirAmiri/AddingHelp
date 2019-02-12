/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addinghelp.websocket;

import com.addinghelp.db.DBConnect;
import com.addinghelp.db.DBQueries;
import javax.json.JsonObject;

/**
 *
 * @author zamir
 */
class Login {

    static Boolean isUnverified(JsonObject message) {
        String username;
        String password;
        String query;
        int result = 0;
        username = message.getString("name");
        password = message.getString("pass");
        query = DBQueries.createUnverifiedQuery(username,password);
        System.out.println(query);
        result = DBConnect.authenticate(query);
        System.out.print("RESULT" + String.valueOf(result));
        return result>0;
    }
    public Login(){   
    }
    
    public static void login(int id,String session) {
        String query = DBQueries.createInsertActiveUserQuery(String.valueOf(id),session);
        System.out.println("QUERY: "+query);
        DBConnect.quickInsert(query);
    }

    public static int authenticate(JsonObject message) {
        String username;
        String password;
        String query;
        int result = 0;
        username = message.getString("name");
        password = message.getString("pass");
        query = DBQueries.createLogInQuery(username,password);
        System.out.println(query);
        result = DBConnect.authenticate(query);
        return result;
    }    
}
