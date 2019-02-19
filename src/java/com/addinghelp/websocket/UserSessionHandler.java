/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addinghelp.websocket;

import com.addinghelp.db.ActiveUserTree;
import com.addinghelp.db.DBConnect;
import com.addinghelp.db.DBQueries;
import com.addinghelp.mailer.Mailer;
import java.io.IOException;
import java.io.StringReader;
import javax.json.JsonObject;
import javax.websocket.Session;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;

/**
 * @author zamir
 **/

@ApplicationScoped
public class UserSessionHandler extends Thread {

    private final JsonObject message;
    private final DBConnect db;
    private final Session session;
    private final static ActiveUserTree users = new ActiveUserTree();
    private final static Cache trendingProjects = new Cache();
    private final static Cache newPosts = new Cache();

    UserSessionHandler(JsonObject jsonMessage, Session session) {
        this.message = jsonMessage;
        this.db = new DBConnect();
        this.session = session;
    }

    @Override
    public void run() {
        String action = this.message.getString("action");
        System.out.println(action);
        if (null != action)
            switch (action) {
            case "register":
                this.register();
                break;
            case "activate_account":
                this.activate_account();
                break;
            case "login":
                this.login();
                break;
            case "update":
                this.update();
                break;
            case "addHelpCoin":
                this.addHelpCoin();
                break;
            case "follow":
                this.follow();
                break;
            case "search_user":
                this.search(0);
                break;
            case "search_foundation":
                this.search(1);
                break;
            case "donate":
                this.donate();
                break;
            // Works up until here
            case "link":
                this.linkDonation();
                break;
            case "projectInfo":
                this.getProjectsInfo();
                break;
            case "forgot_password":
                this.setForgotPassword();
                break;
            case "forgot_username":
                this.forgotUsername();
                break;
            case "change_password":
                this.change_password();
                break;
            case "info":
                this.getInfo();
                break;
            case "create_project":
                this.createProject();
            break;
            default:
                break;
            }
    }

    private void forgotUsername() {
        String email = this.message.getString("email");
        String username = this.db.getUsernameByMail(email);
        if (username == null)
            return;
        Mailer mailer = new Mailer(email);
        mailer.connectAndSend(username);

    }

    private void setForgotPassword() {
        String email = this.message.getString("email");
        String code = this.generateCode(1);
        String query = DBQueries.createSetForgotPasswordQuery(email, code);
        if (this.db.connectANDsendINSERT(query)) {
            JsonProvider provider = JsonProvider.provider();
            JsonObjectBuilder builder = provider.createObjectBuilder();
            builder.add("action", "activate_password");
            this.sendToSession(builder.build());
            Mailer mailer = new Mailer(email);
            mailer.connectAndSend(code);
        }

    }

    private void getProjectsInfo() {
        String username = this.message.getString("username");
        String projectName = this.message.getString("projectName");

    }

    private void linkDonation() {
        String username = this.message.getString("name");
        int coins = this.message.getInt("coins");
        String query = DBQueries.createDonateQueryAnonymous(username, Integer.toString(coins));
        DBConnect.quickInsert(query);
    }

    private void donate() {
        boolean trending = false;
        String userId = String.valueOf(users.getUserId(this.session.getId()));
        if (!this.message.isNull("trending")) {
            trending = this.message.getBoolean("trending");
        }
        String username = this.message.getString("username");
        String coins = String.valueOf(this.message.getInt("coins"));
        String query = DBQueries.checkProjectCompletion(userId);
        if (db.userCoinCheck(userId, coins) && db.connectAndCheck(query)) {
            query = DBQueries.removeCoin(String.valueOf(userId), String.valueOf(coins));
            // System.out.println(query);
            DBConnect.quickInsert(query);
            query = DBQueries.createDonateQuery(String.valueOf(userId), username, String.valueOf(coins));
            DBConnect.quickInsert(query);
            query = DBQueries.registerDonation(userId, username, coins);
            DBConnect.quickInsert(query);
            query = DBQueries.checkIfLoggedIn(username);
            String sessionID[] = this.db.executeQueryAndGetStringArray(query);
            if (sessionID.length > 0) {
                this.sendDonationNotification(userId, coins, sessionID);
            } else {
                query = DBQueries.createDonationNotification(userId, username, coins);
                DBConnect.quickInsert(query);
            }

        } else {
            // TODO: send not enough coins alert.
        }
        if (trending != true) {
            trendingProjects.refresh();
        }

    }

    private void update() {
        String userId = String.valueOf(users.getUserId(this.session.getId()));
        String type = this.message.getString("type");
        switch (type) {
        case "personal":
            // Getting User Data
            this.getUserData(userId);
            break;
        case "notifications":
            // Getting All notifications
            this.getNotifications(userId);
            break;
        case "helpers":
            System.out.println("getting user info");
            // Get usersnames of people who are beign followed by the user
            this.getFollowing(userId);
            break;
        case "helpees":
            // Get usernames of people who are following you
            this.getFollowers(userId);
            break;
        case "projects_user":
            // Getting Project Info
            this.getProjectsInfoFromUser(userId);
            break;
        case "openproject":
            // Get Projects of the people you follow
            this.getOpenProjects(userId);
            break;
        case "people_you_might_know":
            this.getPeopleYouMightKnow(userId);
            break;
        case "trending_projects":
            this.getTrendingProjects();
            break;
        case "new_posts":
            this.getNewPosts();
            break;
        case "explore":
            this.getExplore();
            break;
        default:
            break;

        }
    }

    private void getExplore() {
        this.getNewPosts();
        this.getTrendingProjects();
    }

    private void getNewPosts() {
        if (newPosts.needRefresh()) {
            newPosts.setCache(this.db.getNewPosts());
            System.out.println(newPosts.getCache());
            newPosts.updateTime();
        } else {
            System.out.println("SERVER: Stored data is used");
        }
        this.sendToSession(newPosts.getCache());
    }

    private void getTrendingProjects() {
        /*
         * if(trendingProjects.needRefresh()) {
         * trendingProjects.setCache(this.db.getProjects("0", MIN_PRIORITY));
         * System.out.println(trendingProjects.getCache());
         * trendingProjects.updateTime(); }else{
         * System.out.println("SERVER: Stored data is used"); }
         */
        System.out.println(this.db.getProjects("0", MIN_PRIORITY));
        this.sendToSession(this.db.getProjects("0", MIN_PRIORITY));
    }

    private void getOpenProjects(String id) {
        System.out.println(this.db.getProjects(id, 0).toString());
        this.sendToSession(this.db.getProjects(id, 0));
    }

    private void getPeopleYouMightKnow(String id) {
        System.out.println(this.db.getPeopleYouMightKnow(id).toString());
        this.sendToSession(this.db.getPeopleYouMightKnow(id));
    }

    private void getFollowers(String userId) {
        System.out.println(this.db.getHelpingORHelping(userId, false).toString());
        this.sendToSession(this.db.getHelpingORHelping(userId, false));
    }

    private void getFollowing(String userId) {
        System.out.println(this.db.getHelpingORHelping(userId, true).toString());
        this.sendToSession(this.db.getHelpingORHelping(userId, true));
    }

    private void getProjectsInfoFromUser(String userID) {
        System.out.println(this.db.getProjectsInfo(userID).toString());
        this.sendToSession(this.db.getProjectsInfo(userID));
    }

    private void getUserData(String userID) {
        System.out.println(this.db.getUserData(userID).toString());
        this.sendToSession(this.db.getUserData(userID));
    }

    private void getNotifications(String userID) {
        System.out.println(this.db.getNotifications(userID).toString());
        this.sendToSession(this.db.getNotifications(userID));
        this.db.deleteNotifications(userID);
    }

    private void search(int cmd) {
        String searchArg;
        searchArg = this.message.getString("search");
        switch (cmd) {
        case 0:
            this.searchUsers(searchArg);
            break;
        case 1:
            this.searchFoundations(searchArg);
            break;
        }
    }

    private void searchUsers(String arg) {
        System.out.println(this.db.connectAndSearchUser(arg));
        this.sendToSession(this.db.connectAndSearchUser(arg));
    }

    private void searchFoundations(String arg) {
        System.out.println(this.db.connectAndSearchFoundation(arg));
        this.sendToSession(this.db.connectAndSearchFoundation(arg));
    }

    private void follow() {
        String userID;
        String user, query;
        userID = String.valueOf(users.getUserId(this.session.getId()));
        user = this.message.getString("username");
        query = DBQueries.checkIfAlreadyFollowed(userID, user);
        if (!this.db.connectAndCheck(query)) {
            query = DBQueries.createFollowingQuery(String.valueOf(userID), user);
            System.out.println(query);
            DBConnect.quickInsert(query);
            query = DBQueries.checkIfAlreadyFollowed(String.valueOf(userID), user);
            boolean result = this.db.connectAndCheck(query);
            query = DBQueries.createFollowingNotification(result, user, userID);
            DBConnect.quickInsert(query);
        } else {
            System.out.println("User already follows the other person");
        }
    }

    private void addHelpCoin() {
        System.out.println("Adding Coin");
        String userID = String.valueOf(users.getUserId(this.session.getId()));
        int coins = this.message.getInt("coins");
        String query = DBQueries.createAddingHelpingCoinQuery(String.valueOf(userID), String.valueOf(coins));
        DBConnect.quickInsert(query);
    }

    private void register() {
        /* In register send error message back */
        String name, email, pass, birthdate, gender, code, query;
        name = this.message.getString("username");
        pass = this.message.getString("password");
        email = this.message.getString("email");
        birthdate = this.message.getString("birthdate");
        gender = this.message.getString("gender");
        query = DBQueries.checkNewUserName(name);
        Boolean result = this.db.connectAndCheck(query);
        JsonObject msg = null;
        if (result) {
            query = DBQueries.checkNewUserEmail(email);
            result = this.db.connectAndCheck(query);
            if (result) {
                code = generateCode(0);
                query = this.createRegisterQuery(name, pass, email, birthdate, gender, code);
                System.out.println(query);
                result = this.db.connectANDsendINSERT(query);
                if (result) {
                    Mailer mailer = new Mailer(email);
                    mailer.connectAndSend(code);
                    msg = this.createActivateAccountMessage();
                }
            } else {
                String err = "This email is used by another account.";
                msg = this.createErrorMessage(err);
            }
        } else {
            String err = "This username is already used by someone else.";
            System.out.println(err);
            msg = this.createErrorMessage(err);
        }
        this.sendToSession(msg);
    }

    private String createRegisterQuery(String name, String pass, String email, String birthdate, String gender,
            String code) {
        String q;
        q = "INSERT INTO `UnverifiedUsers`(`username`, `password`, `email`, `gender`, `birthdate`,`activationcode`) VALUES ('"
                + name + "', '" + pass + "', '" + email + "', '" + gender + "', '" + birthdate + "', " + code + ")";
        return q;
    }

    private String generateCode(int type) {
        String code = "";
        int r;
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            r = rand.nextInt(9);
            code = code.concat(Integer.toString(r));
        }
        if (!this.checkCodeUniqueness(code, type)) {
            this.generateCode(type);
        }
        return code;
    }

    private void activate_account() {
        String code, query;
        code = this.message.getString("code");
        query = DBQueries.createActivationQuery(code);
        System.out.println("QUERY :" + query);
        boolean b = db.connectANDsendINSERT(query);
        if (b) {
            query = DBQueries.createDeleteUnverifiedQuery(code);
            System.out.println("QUERY :" + query);
            db.connectANDsendINSERT(query);
            JsonObject msg = this.createAccountVerifiedMessage();
            this.sendToSession(msg);
        }
    }

    private boolean checkCodeUniqueness(String code, int type) {
        String query;
        if (type == 0) {
            query = "SELECT COUNT(username)\n" + "FROM UnverifiedUsers\n" + "WHERE activationcode = " + code;
        } else {
            query = "SELECT COUNT(id)\n" + "FROM SpecialRequests\n" + "WHERE code = " + code;
        }
        return this.db.connectAndCheck(query);
    }

    private void login() {
        int userID = Login.authenticate(this.message);
        final int userID1 = userID;
        if (userID1 > 0) {
            Login.login(userID1, this.session.getId());
            JsonObject obj = createLoginMessage();
            users.addUser(userID1, session);
            this.sendToSession(obj);
        } else {
            Boolean result = Login.isUnverified(this.message);
            if (result) {
                JsonObject msg = this.createActivateAccountMessage();
                this.sendToSession(msg);
            }
        }
    }

    private JsonObject createActivateAccountMessage() {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder().add("action", "unverified_user").build();
        return addMessage;
    }

    private JsonObject createSearchMessage(String v) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder().add("action", "searchResult").add("data", v).build();
        return addMessage;
    }

    private JsonObject createLoginMessage() {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder().add("action", "login_succes").build();
        return addMessage;
    }

    private void sendToSession(JsonObject message) {
        try {
            this.session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            Logger.getLogger(UserSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendToSession(JsonArray message) {
        try {
            this.session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            Logger.getLogger(UserSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JsonObject createAccountVerifiedMessage() {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder().add("action", "user_verified").build();
        return addMessage;
    }

    private JsonObject createErrorMessage(String err) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder().add("error", err).build();
        return addMessage;
    }

    private void change_password() {
        String code = this.message.getString("code");
        String password = this.message.getString("password");
        String query = DBQueries.createChangePasswordQuery(code, password);
        System.out.print(query);
        if (!this.db.connectANDsendINSERT(query)) {
            // send error
        } else {
            query = DBQueries.createClearSpecialRequestQuerie(code);
            DBConnect.quickInsert(query);
        }
    }

    static void removeUser(String sessionId) {
        int userId = users.getUserId(sessionId);
        if (userId != 0)
            users.removeUser(userId, sessionId);
    }

    private void sendDonationNotification(String userId, String coins, String[] sessions) {
        String username;
        Session s;
        JsonProvider provider = JsonProvider.provider();
        JsonObjectBuilder objbuilder = provider.createObjectBuilder();
        String query = DBQueries.getUsernameById(userId);
        username = this.db.executeQueryAndGetString(query);
        String msg = "<".concat(username + "> has donated " + coins + " helpcoins to your project");
        objbuilder.add("action", "notification");
        objbuilder.add("type", "donation");
        objbuilder.add("message", msg);
        JsonObject obj = objbuilder.build();
        for (String session1 : sessions) {
            s = users.getSession(session1);
            try {
                s.getBasicRemote().sendText(obj.toString());
            } catch (IOException ex) {
                Logger.getLogger(UserSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
                users.removeUser(Integer.valueOf(userId), session1);
            }
        }
    }

    private void getInfo() {
        String type = this.message.getString("type");
        switch (type) {
        case "foundation_projects":
            this.getFoundationProjects();
            break;
        case "foundation_posts":
            this.getFoundationPosts();
            break;
        case "user_project":
            this.getUserProject();
            break;
        case "post_full":
            this.getPost();
            break;
        case "surveys":
            this.getSurvey();
            break;
        case "specials":
            this.getSpecials();
            break;
        case "statistics":
            this.getStats();
            break;
        case "foundation":
            this.getFoundationInfo();
            break;
        case "people_who_donated":
            this.getPeopleWhoDonated();
            break;
        }
    }

    private void getFoundationProjects() {
        String foundationName = this.message.getString("foundation");
        System.out.println(this.db.getFoundationProjects(foundationName));
        this.sendToSession(this.db.getFoundationProjects(foundationName));
    }

    private void getPost() {
        String foundationName = this.message.getString("foundation");
        String postTitle = this.message.getString("post_title");
        System.out.println(this.db.getPost(foundationName, postTitle).toString());
        this.sendToSession(this.db.getPost(foundationName, postTitle));
    }

    private void getUserProject() {
        String projectID = String.valueOf(this.message.getInt("projectID"));
        System.out.println(this.db.getUserProject(projectID).toString());
        this.sendToSession(this.db.getUserProject(projectID));
    }

    private void getFoundationPosts() {
        String foundationName = this.message.getString("foundation");
        System.out.println(this.db.getFoundationPosts(foundationName).toString());
        this.sendToSession(this.db.getFoundationPosts(foundationName));
    }

    private void getSurvey() {
        System.out.println(this.db.getSurvey().toString());
        this.sendToSession(this.db.getSurvey());
    }

    private void getSpecials() {
        System.out.println(this.db.getSpecials().toString());
        this.sendToSession(this.db.getSpecials());
    }

    private void getStats() {
        System.out.println(this.db.getStats().toString());
        this.sendToSession(this.db.getStats());
    }

    private void getFoundationInfo() {
        String foundation = this.message.getString("foundationname");
        System.out.println(this.db.getFoundationInfo(foundation).toString());
        this.sendToSession(this.db.getFoundationInfo(foundation));
    }

    private void getPeopleWhoDonated() {
        String projectID = String.valueOf(this.message.getInt("projectID"));
        System.out.println(this.db.getPeopleWhoDonated(projectID).toString());
        this.sendToSession(this.db.getPeopleWhoDonated(projectID));
    }

    private void createProject() {
        String userID = String.valueOf(users.getUserId(this.session.getId()));
        String projectname = this.message.getString("projectname");
        String foundation = this.message.getString("foundation");
        String goal = this.message.getString("goal");
        String query = DBQueries.createUserProject(userID,projectname,foundation,goal);
        String s;
        JsonReader reader;
        boolean result;
                result = this.db.connectANDsendINSERT(query);
        if(result){
            s = "{\"action\":\"succes\",\"message\":\"project succesfully created\"}";
        }else{
            s = "{\"action\":\"failure\",\"message\":\"something went wrong please try again later.\"}";
        }
        reader = Json.createReader(new StringReader(s));
        JsonObject jsonMessage = reader.readObject();
        this.sendToSession(jsonMessage);
    }
    
    private void getAllFoundationProjectsWithGPS(){
        System.out.println(this.db.getAllFOundationProjectsWithGPS().toString());
        this.sendToSession(this.db.getAllFOundationProjectsWithGPS());
    }
}
