/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addinghelp.db;

import javax.websocket.Session;

/**
 *
 * @author zamir
 */
public class ActiveUser {
    private final Session session;
    private final int userid;
    private ActiveUser L,R;


    ActiveUser(int userID, Session session) {
         this.session = session;
        this.userid = userID;
    }
    public void addUser(ActiveUser newUser){
        int check = ActiveUser.stringComp(this.session.getId(),newUser.getSessionID());
        switch (check) {
            case 0:
                this.addToLeft(newUser);
                break;
            case 2:
                this.addToRight(newUser);
                break;
            default:
                System.out.println("Session already exists");
                break;
        }
    }

    public int getID() {
        return userid;
    }

    public Session getSession(int userID){
        Session temp;
        if(userID<this.userid){
            return this.L.getSession(userID);
        }else if(userID>this.userid){
            return this.R.getSession(userID);
        }
        return this.session;
    }
    
    public ActiveUser removeUser(int userID,String sesID){
        int check = stringComp(this.session.getId(),sesID);
        if(userID == this.userid && check == 1){
            System.out.println("Deleting closed session ".concat(this.session.getId()));
            return this.cleanUp();
        }else if(check == 0)
        {
            if(this.L != null)
            this.L = this.L.removeUser(userID,sesID);
        }else
        {
            if(this.R != null)
            this.R = this.R.removeUser(userID,sesID);
        }
        return this;
    }
    
    private ActiveUser cleanUp(){
        if(this.L != null)
            this.L.addUser(this.R);
        else
            return this.R;
        return this.L;
    }

    

    public void printUserList() {
        if(this.L != null){
            this.L.printUserList();
        }
        System.out.println(this.session.getId() +" : "+ Integer.toString(this.userid));
        if(this.R != null)
            this.R.printUserList();
    }
    
    private static int stringComp(String s1, String s2) {
		char[] a = new char[36];
		char[] b = new char[36];
		s1.getChars(0, s1.length(), a, 0);
		System.out.println(a);
		s2.getChars(0, s2.length(), b, 0);
		System.out.println(b);
		for(int i=0;i<a.length;i++) {
			//System.out.println("Measuring: ("+a[i] + ","+b[i]+")");
			if(a[i] < b[i]) {
				return 0;
			}else if(a[i] > b[i])
				return 2;
		}
		return 1;
	}
    

    private void addToLeft(ActiveUser newUser){
        if(this.L == null)
            this.L = newUser;
        else
            this.L.addUser(newUser);
    
    }

    private void addToRight(ActiveUser newUser){
        if(this.R == null)
            this.R = newUser;
        else
            this.R.addUser(newUser);
    
    }
    
    public String getSessionID(){
        return this.session.getId();
    }
    
    public int getUserId(String sessionID){
        int check = ActiveUser.stringComp(this.session.getId(),sessionID);
        switch (check) {
            case 0:
                return this.L.getUserId(sessionID);
            case 2:
                return this.R.getUserId(sessionID);
            default:
                return this.userid;
        }
    }
    
}
