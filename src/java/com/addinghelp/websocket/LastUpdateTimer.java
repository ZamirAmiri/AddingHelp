/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addinghelp.websocket;
import java.util.Calendar;
import javax.json.JsonObject;

/**
 *
 * @author zamir
 */
class LastUpdateTimer {
    private int time;
    private Calendar cal = null;
    private JsonObject trendingProjects;
    
    public void updateTime(){
        if(this.cal == null){
            this.cal = Calendar.getInstance();
        }
        this.time = (int) Math.ceil(this.cal.getTimeInMillis()/1000);
        System.out.println("New last update time has been set to :"+ String.valueOf(this.time));
        
    }
    public void setTrendingProjects(JsonObject obj){
        this.trendingProjects = obj;
    }
    
    public JsonObject getTrendingProjects(){
        return this.trendingProjects;
    }

    boolean needRefresh() {
        int newTime = (int) Math.ceil(this.cal.getTimeInMillis()/1000);
        return newTime - 3600 >= this.time;
    }
    
}
