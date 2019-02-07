/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addinghelp.db;

/**
 *
 * @author zamir
 */
public class DBQueriesAdmin {
    public static String createInsertFoundationQuery(String name,String info,String logo,String website){
        return "INSERT INTO `foundation`(`id`, `name`, `info`, `logo`, `website`) "
                + "VALUES (0,'"+name+"','"+info+"','"+logo+"','"+website+"')";
    }
    public static String createInsertPropositionQuery(String description, String icon, String name, String foundation, String category, int impactPoints, int value, String unit, int requiredCoins){
        return "INSERT INTO `proposition`(`name`, `description`, `icon`, `impactpoints`, `requiredcoins`, `unit`, `value`, `category`, `foundation`)"
                + " VALUES ('"
                + name +"','" +description+"','"+icon+"',"+Integer.toString(impactPoints)+","
                        + Integer.toString(requiredCoins)+",'"+unit+"',"+Integer.toString(value)+",'"
                        +category+"','"+ foundation + "')";
    }
    private static String selectProposition(String propositionName){
        return "SELECT `id` FROM `proposition` WHERE name='"+propositionName+"'";
    }
    private static String selectFoundation(String name){
        return "SELECT `id` FROM `foundation` WHERE name='"+name+"'";
    }
    private static String selectCountry(String country){
        return "Select `id` from countries WHERE country = '"+country+"'";
    }
    public static String insertPropositionLocation(String country, String propositionName){
        return "INSERT INTO `propositionlocation`(`proposition`, `country`) VALUES ("
                + DBQueriesAdmin.selectProposition(propositionName) + "',"
                + DBQueriesAdmin.selectCountry(country) + "')";
    }
    private static String insertPropositionPictures(String propositionName,String picture){
        return "INSERT INTO `propositionpictures`(`url`, `proposition`) VALUES ('"
                + picture
                + "',"
                + DBQueriesAdmin.selectProposition(propositionName)
                + ")";
    }
    
}
