/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addinghelp.websocket;

import com.addinghelp.db.DBConnectAdmin;
import com.addinghelp.db.DBQueriesAdmin;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author zamir
 */
@ApplicationScoped
@ServerEndpoint("/admin")
public class AdminWebSocketServer {

    private JsonObject message;
    private String uname;
    private String password;
        @OnOpen
        public void open(Session session) {
            System.out.println("Admin connected");
            //this.sessionHandler.addSession(session);
    }

    @OnClose
        public void close(Session session) {
            //sessionHandler.removeSession(session);
    }

    @OnError
        public void onError(Throwable error) {
            Logger.getLogger(UserWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
        public void handleMessage(String message, Session session) {
            System.out.println("Messahe found");
            try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            this.uname = jsonMessage.getString("username");
            this.password = jsonMessage.getString("password");
            this.message = jsonMessage;
            if("addFoundation".equals(jsonMessage.getString("action"))){
                this.addFoundation();
            }
        }
    }

    private void addFoundation() {
        String name,info,logo,website;
        name = this.message.getString("name");
        info = this.message.getString("info");
        logo = this.message.getString("logo");
        website = this.message.getString("website");
        String query = DBQueriesAdmin.createInsertFoundationQuery(name,info,logo,website);
        DBConnectAdmin conn = new DBConnectAdmin(this.uname,this.password);
        conn.quickInsert(query);
    }
}
