/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addinghelp.db;

import java.math.BigDecimal;
import java.sql.*;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zamir
 */
public class DBConnect {

    private static final String host = "jdbc:mysql://localhost:3306/addinghelp";
    private static final String uName = "root";
    private static final String uPass = "";
    private static final String jdbc = "com.mysql.jdbc.Driver";
    private ResultSet result;
    private String query;
    private JsonProvider provider;

    public DBConnect() {
        provider = JsonProvider.provider();
    } //Initialize

    public static int authenticate(String query) {
        ResultSet resultset;
        try {
            Class.forName(jdbc);
            // here sonoo is database name, root is username and password
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                // here sonoo is database name, root is username and password
                Statement stmt = con.createStatement();
                resultset = stmt.executeQuery(query);
                resultset.next();
                return resultset.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public boolean connectANDsendINSERT(String query) {
        try {
            Class.forName(jdbc);
            // here sonoo is database name, root is username and password
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                // here sonoo is database name, root is username and password
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public static void quickInsert(String query) {
        try {
            Class.forName(jdbc);
            // here sonoo is database name, root is username and password
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                // here sonoo is database name, root is username and password
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public ResultSet connectANDsendSelect(String query) {
        result = null;
        try {
            Class.forName(jdbc);
            // here sonoo is database name, root is username and password
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                // here sonoo is database name, root is username and password
                Statement stmt = con.createStatement();
                result = stmt.executeQuery(query);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    public boolean connectAndCheck(String query) {
        result = null;
        Object r = null;
        try {
            Class.forName(jdbc);
            // here sonoo is database name, root is username and password
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                // here sonoo is database name, root is username and password
                Statement stmt = con.createStatement();
                result = stmt.executeQuery(query);
                result.next();
                r = result.getObject(1);
                // System.out.println("Value: " +Integer.toString(r));
                // result.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return (result != null);
    }

    public JsonObject getUserData(String userId) {
        this.query = DBQueries.createGetUserDataQuery(userId);
        System.out.println(this.query);
        this.result = null;
        JsonObjectBuilder builder = provider.createObjectBuilder();
        try {
            Class.forName(jdbc);
            // here sonoo is database name, root is username and password
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                // here sonoo is database name, root is username and password
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                if (this.result.next()) {
                    builder.add("action", "update");
                    builder.add("type", "personal");
                    builder.add("username", result.getString("username"));
                    builder.add("birthdate", result.getDate("birthdate").toString());
                    builder.add("email", result.getString("email"));
                    builder.add("gender", result.getString("gender"));
                    builder.add("helpcoins", result.getInt("helpcoins"));
                    builder.add("helpcoins", result.getInt("helpcoins"));
                    builder.add("accumulated", result.getInt("accumulated"));
                    builder.add("followers", result.getInt("followers"));
                    builder.add("following", result.getInt("following"));
                    builder.add("completed_projects", result.getInt("completed_projects"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return builder.build();
    }

    public JsonArray connectAndSearchUser(String arg) {
        this.query = DBQueries.createSearchUsersQuery(arg);
        result = null;
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        int flag = 0;
        try {
            Class.forName(jdbc);
            // here sonoo is database name, root is username and password
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                // here sonoo is database name, root is username and password
                Statement stmt = con.createStatement();
                result = stmt.executeQuery(query);
                while (result.next()) {
                    JsonObjectBuilder builder = provider.createObjectBuilder();
                    builder.add("username", result.getString("username"));
                    builder.add("picture", result.getString("picture"));
                    arrBuilder.add(builder);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        arrBuilder.add("action: 'search_results'");
        arrBuilder.add("type:'USR'");
        return arrBuilder.build();
    }

    public JsonArray connectAndSearchFoundation(String arg) {
        this.query = DBQueries.createSearchFoundationQuery(arg);
        this.result = null;
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        int flag = 0;
        try {
            Class.forName(jdbc);
            // here sonoo is database name, root is username and password
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                // here sonoo is database name, root is username and password
                Statement stmt = con.createStatement();
                result = stmt.executeQuery(query);
                while (result.next()) {
                    JsonObjectBuilder builder = provider.createObjectBuilder();
                    builder.add("name", result.getString("name"));
                    builder.add("picture", result.getString("logo"));
                    arrBuilder.add(builder);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        arrBuilder.add("action: 'search_results'");
        arrBuilder.add("type:'FND'");
        return arrBuilder.build();
    }

    public boolean userCoinCheck(String userId, String coins) {
        query = "SELECT * FROM Users WHERE `id` = " + userId + " AND `helpcoins` >= " + coins;
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                result = stmt.executeQuery(query);
                if (result.next()) {
                    return true;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public JsonObject getProjectsInfo(String userId) {
        this.result = null;
        this.query = DBQueries.createGetProjectsQueryFromUser(userId);
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        try {
            Class.forName(jdbc);
            // here sonoo is database name, root is username and password
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                // here sonoo is database name, root is username and password
                Statement stmt = con.createStatement();
                result = stmt.executeQuery(query);
                while (result.next()) {
                    JsonObjectBuilder builder = provider.createObjectBuilder();
                    builder.add("foundationProject", result.getString("projectname"));
                    builder.add("goal", result.getString("goal"));
                    builder.add("helpcoins", result.getString("helpcoins"));
                    builder.add("creationdate", result.getString("creationdate"));
                    if (result.getString("completiondate") != null) {
                        builder.add("completiondate", result.getString("completiondate"));
                    }
                    arrBuilder.add(builder);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        JsonObjectBuilder builder = provider.createObjectBuilder();
        builder.add("messages", arrBuilder);
        builder.add("action", "update");
        builder.add("type", "projects_user");
        return builder.build();
    }

    /* Change if met foto */
    public JsonObject getNotifications(String userID) {
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        result = null;
        this.query = DBQueries.getNotifications(userID);
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                result = stmt.executeQuery(this.query);
                while (result.next()) {
                    JsonObjectBuilder builder = provider.createObjectBuilder();
                    builder.add("info", result.getString("info"));
                    builder.add("date", result.getString("date"));
                    arrBuilder.add(builder);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        JsonObjectBuilder builder = provider.createObjectBuilder();
        builder.add("messages", arrBuilder);
        builder.add("action", "update");
        builder.add("type", "notifications");
        return builder.build();
    }

    public JsonObject getHelpingORHelping(String userId, boolean helpers) {
        if (helpers) {
            this.query = DBQueries.createGetHelpersQuery(userId);
        } else {
            this.query = DBQueries.creategetHelpingQuery(userId);
        }
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                result = stmt.executeQuery(this.query);
                while (result.next()) {
                    JsonObjectBuilder builder = provider.createObjectBuilder();
                    builder.add("username", result.getString("username"));
                    arrBuilder.add(builder);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        JsonObjectBuilder builder = provider.createObjectBuilder();
        builder.add("action", "update");
        if (helpers) {
            builder.add("type", "helpers");
        } else {
            builder.add("type", "helpees");
        }
        builder.add("messages", arrBuilder);
        return builder.build();
    }

    public JsonObject getProjects(String id, int type) {
        if (type == 0) {
            this.query = DBQueries.getOpenProjects(id);
        } else {
            this.query = DBQueries.trendingProjects();
        }
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                while (this.result.next()) {
                    JsonObjectBuilder builder = provider.createObjectBuilder();
                    builder.add("username", this.result.getString("username"));
                    builder.add("helpcoins", this.result.getString("helpcoins"));
                    builder.add("goal", this.result.getString("goal"));
                    builder.add("creationdate", this.result.getString("creationdate"));
                    builder.add("projectname", this.result.getString("projectname"));
                    builder.add("foundationname", this.result.getString("foundationname"));
                    arrBuilder.add(builder);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        JsonObjectBuilder builder = provider.createObjectBuilder();
        if (type == 0) {
            builder.add("type", "openprojects");
        } else {
            builder.add("type", "trending_projects");
        }
        builder.add("messages", arrBuilder);
        builder.add("action", "update");
        return builder.build();
    }

    public String getUsernameByMail(String email) {
        this.query = DBQueries.createGetUsernameByMailQuery(email);
        this.result = null;
        String username;
        username = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                while (this.result.next()) {
                    username = this.result.getString("username");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return username;
    }

    public JsonObject getPeopleYouMightKnow(String id) {
        this.query = DBQueries.createPeopleYouMightKnowQuery(id);
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                result = stmt.executeQuery(this.query);
                while (result.next()) {
                    JsonObjectBuilder builder = provider.createObjectBuilder();
                    builder.add("username", result.getString("username"));
                    arrBuilder.add(builder);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        JsonObjectBuilder builder = provider.createObjectBuilder();
        builder.add("action", "update");
        builder.add("messages", arrBuilder);
        builder.add("type", "people_you_might_know");
        return builder.build();
    }

    public void deleteNotifications(String userID) {
        this.query = DBQueries.deleteNotifications(userID);
        this.connectANDsendINSERT(query);
    }

    public JsonObject getNewPosts() {
        this.query = DBQueries.getNewPosts();
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                while (this.result.next()) {
                    JsonObjectBuilder builder = provider.createObjectBuilder();
                    builder.add("short", this.result.getString("short"));
                    builder.add("content", this.result.getString("content"));
                    builder.add("title", this.result.getString("title"));
                    builder.add("hashtags", this.result.getString("hashtags"));
                    builder.add("foundationname", this.result.getString("foundationname"));
                    arrBuilder.add(builder);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        JsonObjectBuilder builder = provider.createObjectBuilder();
        builder.add("type", "new_posts");
        builder.add("messages", arrBuilder);
        builder.add("action", "update");
        return builder.build();
    }

    public JsonObject getExplore() {
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        JsonObjectBuilder builder = provider.createObjectBuilder();
        arrBuilder.add(this.getNewPosts());
        arrBuilder.add(this.getProjects("empty", 1));
        builder.add("type", "explore");
        builder.add("messages", arrBuilder);
        builder.add("action", "update");
        return builder.build();
    }

    public String[] executeQueryAndGetStringArray(String query) {
        String[] result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                int iterator = 0;
                while (this.result.next()) {
                    result[iterator] = this.result.getString(1);
                    iterator++;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String executeQueryAndGetString(String query) {
        String result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                if (this.result.next()) {
                    result = this.result.getString(1);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public JsonObject getFoundationProjects(String foundationName) {
        this.query = DBQueries.getNewPosts();
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                while (this.result.next()) {
                    JsonObjectBuilder builder = provider.createObjectBuilder();
                    builder.add("short", this.result.getString("short"));
                    builder.add("content", this.result.getString("content"));
                    builder.add("title", this.result.getString("title"));
                    builder.add("hashtags", this.result.getString("hashtags"));
                    builder.add("pictures", this.getFoundationPictures(
                            foundationName,
                            this.getFoundationPictureHash(
                                    foundationName, "Project", this.result.getString("title")
                            )
                    )
                    );
                    arrBuilder.add(builder);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        JsonObjectBuilder builder = provider.createObjectBuilder();
        builder.add("type", "foundation_projects");
        builder.add("messages", arrBuilder);
        builder.add("action", "update");
        builder.add("foundationname", foundationName);
        return builder.build();
    }

    public JsonObject getPost(String foundationName, String postTitle) {

        JsonObjectBuilder builder = provider.createObjectBuilder();
        this.query = DBQueries.getPost(foundationName, postTitle);
        String enc = this.getFoundationPictureHash(foundationName, "Post", postTitle);
        JsonArray arr = getFoundationPictures(foundationName, enc);
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                if (this.result.next()) {
                    builder.add("short", this.result.getString("short"));
                    builder.add("content", this.result.getString("content"));
                    builder.add("title", postTitle);
                    builder.add("hashtags", this.result.getString("hashtags"));
                    builder.add("date", this.result.getString("date"));
                    builder.add("foundationname", foundationName);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        builder.add("type", "post_full");
        builder.add("pictures", arr);
        builder.add("action", "info");
        return builder.build();
    }

    private JsonArray getFoundationPictures(String foundationName, String code) {
        if (code.isEmpty()) {
            this.query = DBQueries.getFoundationPictures(foundationName);
        } else {
            this.query = DBQueries.getFoundationPictures(foundationName, code);
        }
        JsonArrayBuilder builder = provider.createArrayBuilder();
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                while (this.result.next()) {
                    builder.add(this.result.getString("code"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return builder.build();
    }

    public JsonObject getUserProject(String projectID) {
        this.query = DBQueries.getUserProject(projectID);
        JsonObjectBuilder builder = provider.createObjectBuilder();
        this.result = null;
        JsonArray arr = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                if (this.result.next()) {
                    builder.add("helpcoins", this.result.getString("helpcoins"));
                    builder.add("goal", this.result.getString("goal"));
                    builder.add("title", "user_project");
                    builder.add("username", this.result.getString("username"));
                    builder.add("creationdate", this.result.getString("creationdate"));
                    builder.add("completiondate", this.result.getString("completiondate"));
                    builder.add("foundationname", this.result.getString("foundationname"));
                    builder.add("projectname", this.result.getString("projectname"));
                    builder.add("info", this.result.getString("info"));
                    String enc = this.getFoundationPictureHash(this.result.getString("foundationname"), "Project",
                            this.result.getString("projectname"));
                    arr = getFoundationPictures(this.result.getString("foundationname"), enc);
                    builder.add("pictures", arr);
                    builder.add("hellpers", this.getPeopleWhoDonated(projectID));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        builder.add("type", "user_project");
        builder.add("action", "info");
        return builder.build();
    }

    public JsonObject getFoundationPosts(String foundationName) {
        this.query = DBQueries.getFoundationPosts(foundationName);
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        JsonArray arr;
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                while (this.result.next()) {
                    JsonObjectBuilder builder = provider.createObjectBuilder();
                    builder.add("short", this.result.getString("short"));
                    builder.add("content", this.result.getString("content"));
                    builder.add("title", this.result.getString("title"));
                    builder.add("hashtags", this.result.getString("hashtags"));
                    builder.add("date", this.result.getString("date"));
                    builder.add("foundationname", foundationName);
                    String enc = this.getFoundationPictureHash(this.result.getString("foundationname"), "Post",
                            this.result.getString("title"));
                    arr = this.getFoundationPictures(this.result.getString("foundationname"), enc);
                    builder.add("pictures", arr);
                    arrBuilder.add(builder);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        JsonObjectBuilder builder = provider.createObjectBuilder();
        builder.add("type", "foundation_posts");
        builder.add("messages", arrBuilder);
        builder.add("action", "info");
        return builder.build();
    }

    private String getFoundationPictureHash(String foundationName, String type, String title) {
        String s = foundationName;
        String encryptedString;
        encryptedString = "";
        s = s.concat("-" + type);
        s = s.concat("-" + title);
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(s.getBytes());
            encryptedString = new String(messageDigest.digest());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encryptedString;
    }

    public JsonObject getSurvey() {
        this.query = DBQueries.getSurvey();
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                while (this.result.next()) {
                    JsonObjectBuilder builder = provider.createObjectBuilder();
                    builder.add("url", this.result.getString("url"));
                    builder.add("coins", this.result.getString("coins"));
                    builder.add("picture", this.result.getString("picture"));
                    arrBuilder.add(builder);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        JsonObjectBuilder builder = provider.createObjectBuilder();
        builder.add("type", "surveys");
        builder.add("messages", arrBuilder);
        builder.add("action", "info");
        return builder.build();
    }

    public JsonObject getSpecials() {
        this.query = DBQueries.getSpecials();
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                while (this.result.next()) {
                    JsonObjectBuilder builder = provider.createObjectBuilder();
                    builder.add("url", this.result.getString("url"));
                    builder.add("coins", this.result.getString("coins"));
                    builder.add("picture", this.result.getString("picture"));
                    arrBuilder.add(builder);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        JsonObjectBuilder builder = provider.createObjectBuilder();
        builder.add("type", "specials");
        builder.add("messages", arrBuilder);
        builder.add("action", "info");
        return builder.build();
    }

    public JsonObject getStats() {
        this.query = DBQueries.getStats();
        JsonObjectBuilder builder = provider.createObjectBuilder();
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                if (this.result.next()) {
                    builder.add("raised", this.result.getDouble("raised"));
                    builder.add("raised_total", this.result.getString("raised_total"));
                    builder.add("users", this.result.getString("users"));
                    builder.add("completed_projects", this.result.getString("completed_projects"));
                    builder.add("helpcoins_generated", this.result.getString("helpcoins_generated"));
                    builder.add("helpcoins_donated", this.result.getString("helpcoins_donated"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        builder.add("type", "statistics");
        builder.add("action", "info");
        return builder.build();
    }

    public JsonObject getFoundationInfo(String foundation) {
        this.query = DBQueries.getFoundationInfo(foundation);
        JsonObjectBuilder builder = provider.createObjectBuilder();
        JsonArray arr;
        arr = this.getFoundationPictures(foundation, "");
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                if (this.result.next()) {
                    builder.add("foundationname", foundation);
                    builder.add("info", this.result.getString("info"));
                    builder.add("logo", this.result.getString("logo"));
                    builder.add("type", this.result.getString("type"));
                    builder.add("website", this.result.getString("website"));
                    builder.add("helpcoins_recieved", this.result.getString("helpcoins_recieved"));
                    builder.add("projects_completed", this.result.getString("projects_completed"));
                    builder.add("pictures", arr);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        builder.add("type", "foundation");
        builder.add("action", "info");
        return builder.build();
    }

    public JsonObject getPeopleWhoDonated(String projectID) {
        this.query = DBQueries.getPeopleWhoDonated(projectID);
        JsonObjectBuilder builder = provider.createObjectBuilder();
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                while (this.result.next()) {
                    arrBuilder.add(this.result.getString("username"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        builder.add("type", "people_who_donated");
        builder.add("action", "info");
        builder.add("people", arrBuilder);
        return builder.build();
    }

    public JsonObject getAllFOundationProjectsWithGPS() {
        this.query = DBQueries.getAllFoundationProjectsWithGPS();
        JsonArrayBuilder arrBuilder = provider.createArrayBuilder();
        this.result = null;
        try {
            Class.forName(jdbc);
            try (Connection con = DriverManager.getConnection(DBConnect.host, DBConnect.uName, DBConnect.uPass)) {
                Statement stmt = con.createStatement();
                this.result = stmt.executeQuery(query);
                while (this.result.next()) {
                    JsonObjectBuilder builder = provider.createObjectBuilder();
                    builder.add("short", this.result.getString("short"));
                    builder.add("title", this.result.getString("title"));
                    builder.add("hashtags", this.result.getString("hashtags"));
                    arrBuilder.add(builder);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        JsonObjectBuilder builder = provider.createObjectBuilder();
        builder.add("type", "specials");
        builder.add("messages", arrBuilder);
        builder.add("action", "info");
        return builder.build();
    }
}
