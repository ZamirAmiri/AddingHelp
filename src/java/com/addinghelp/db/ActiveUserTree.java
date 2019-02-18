/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addinghelp.db;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

/**
 *
 * @author zamir
 */
@ApplicationScoped
public class ActiveUserTree {

    private ActiveUser root=null;
    
    public void addUser(int userID, Session session) {
        System.out.println("1");
        if (root == null){
            this.root = this.createUser(userID,session);
        }else{
            root.addUser(this.createUser(userID, session));
        }
    }

    private ActiveUser createUser(int userID, Session session) {
        return new ActiveUser(userID,session);
    }
    
    public void removeUser(int userID,String sesID){
        if(this.root != null)
        this.root = this.root.removeUser(userID,sesID);
    }

    public Session getSession(int userID) {
        if(root != null)
           return this.root.getSession(userID);
        return null;
    }
    
    public Session getSession(String sessionId) {
        if(root != null)
           return this.root.getSession(sessionId);
        return null;
    }
    
    public int getUserId(String sessionID){
        if(root != null)
            return this.root.getUserId(sessionID);
        return 0;
    }

    public void printUserList() {
        this.root.printUserList();
    }

}
