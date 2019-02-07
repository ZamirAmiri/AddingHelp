/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addinghelp.websocket;

import com.addinghelp.db.DBConnect;
import com.addinghelp.db.DBQueries;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
 * @author zamir amiri
 */
@ApplicationScoped
@ServerEndpoint("/actions")
public class UserWebSocketServer {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:ms");
    @OnOpen
        public void open(Session session) {
            System.out.println("New connection: "+ session.getId());
            //this.sessionHandler.addSession(session);
    }

    @OnClose
        public void close(Session session) {
            String query = DBQueries.createLogOutQuery(session.getId());
            DBConnect.quickInsert(query);
    }

    @OnError
        public void onError(Throwable error) {
            Logger.getLogger(UserWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
        public void handleMessage(String message, Session session) {
            System.out.println("Message found");
            try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            Thread t = new UserSessionHandler(jsonMessage,session);
            t.start();
        }
    }
}
