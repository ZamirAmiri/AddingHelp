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
class Cache {
    private int time = 0;
    private Calendar cal = null;
    private JsonObject data;
    private boolean refresh = true;
    
    public void updateTime(){
        this.makeCalendar();
        this.time = (int) Math.ceil(this.cal.getTimeInMillis()/1000);
        System.out.println("New last update time has been set to :"+ String.valueOf(this.time));
    }
    public void setCache(JsonObject obj){
        this.data = obj;
    }
    
    public JsonObject getCache(){
        return this.data;
    }

    boolean needRefresh() {
        this.makeCalendar();
        int newTime = (int) Math.ceil(this.cal.getTimeInMillis()/1000);
        return newTime - 3600 >= this.time || this.refresh;
    }
    
    private void makeCalendar(){
        if(this.isEmpty()){
            this.cal = Calendar.getInstance();
        }
    }
    
    private boolean isEmpty(){
        return this.cal == null;
    }
    
    public void refresh(){
        this.refresh = true;
    }
    
}
