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
public class DBQueries {

    public static String createUploadPictureQuery(String userID, String encodedPicture) {
        return "UPDATE Users SET picture='" + encodedPicture + "' WHERE id = " + userID;
    }

    public static String createInsertActiveUserQuery(String id, String session) {
        return "INSERT INTO ActiveUsers(userId, Session) VALUES (" + id + ",'" + session + "')";
    }

    public static String createLogInQuery(String username, String password) {
        return ("Select id from Users WHERE username = '" + username + "' AND password = '" + password + "'");
    }

    public static String createUnverifiedQuery(String username, String password) {
        return ("SELECT COUNT(name) FROM UnverifiedUsers WHERE name = '" + username + "' AND pass = '" + password
                + "'");
    }

    public static String createActivationQuery(String code) {
        return "INSERT INTO Users (username, password, email, gender, birthdate)"
                + "SELECT username, password, email, gender, birthdate FROM UnverifiedUsers " + "WHERE activationcode='"
                + code + "'";
        // "DELETE FROM UnverifiedUsers WHERE code ='"+ code+"'";
    }

    public static String createDeleteUnverifiedQuery(String code) {
        return "DELETE FROM UnverifiedUsers WHERE activationcode ='" + code + "'";
    }

    public static String createSubscribeQuery(String userID, String helpingID) {
        return "INSERT INTO Subscriptions(user, helping) VALUES (" + userID + "," + helpingID + ")";
    }

    public static String createPrivateProject(String projectName, String userID, String goal, String priv) {
        return "INSERT INTO project(" + "name, user, goal, private, impactpoStrings, helpcoins)" + " VALUES (" + "'"
                + projectName + "'," + userID + "," + goal + "," + priv + "," + Integer.toString(0) + ","
                + Integer.toString(0) + ")";
    }

    public static String createPublicProject(String projectName, String userID, String goal, String priv,
            String rewardDescription, String rewardType, String rewardURL) {
        return "INSERT INTO project("
                + "name, user, goal, private, impactpoStrings,rewarddescription, rewardtype, rewardurl, helpcoins)"
                + " VALUES (" + "'" + projectName + "'," + userID + "," + goal + "," + priv + "," + Integer.toString(0)
                + ",'" + rewardDescription + "','" + rewardType + "','" + rewardURL + "'," + Integer.toString(0) + ")";
    }

    public static String createProjectParticipationQuery(String projectName, String userID) {
        String project = DBQueries.selectProject(projectName);
        return "INSERT INTO projectparticipation(project, user) VALUES (" + project + "," + userID + ")";
    }

    public static String createprojectPropositions(String projectName, String propositionName) {
        String query_one = DBQueries.selectProject(projectName);
        String query_two = DBQueries.selectProposition(propositionName);

        return "INSERT " + "INTO projectpropositions(project, proposition) " + "VALUES (" + query_one + "," + query_two
                + ")";
    }

    private static String selectProject(String projectName) {
        return "SELECT id FROM project WHERE name = '" + projectName + "'";
    }

    private static String selectProposition(String propositionName) {
        return "SELECT id FROM proposition WHERE name = '" + propositionName + "'";
    }

    public static String createAddingHelpingCoinQuery(String userId, String coins) {
        return "UPDATE Users SET helpcoins = helpcoins + " + coins + ", accumulated=accumulated + " + coins
                + " WHERE id = '" + userId + "'";
    }

    public static String createFindUserIdWithUserName(String userName) {
        return "SELECT id FROM Users WHERE username = '" + userName + "'";
    }

    public static String createFollowingQuery(String userId, String otherUserName) {
        return "INSERT INTO Subscriptions(helper, helpee)\n" + "SELECT A.id AS CustomerName1, B.id AS CustomerName2\n"
                + "FROM Users A, Users B\n" + "WHERE A.id = " + userId + "\n" + "AND B.username = '" + otherUserName
                + "'";
    }

    public static String createSearchUsersQuery(String argument) {
        return "SELECT username,picture FROM Users WHERE username LIKE '%" + argument + "%'";
    }

    public static String createGetUserDataQuery(String userId) {
        return "SELECT username, birthdate, email, gender, helpcoins, accumulated,"
                + "(SELECT COUNT(Projects.id) AS value FROM Projects WHERE Projects.user = " + userId
                + " AND Projects.completiondate IS NOT NULL) AS completed_projects,"
                + "(SELECT COUNT(helper) FROM Subscriptions WHERE helpee = " + userId + " ) AS followers ,"
                + "(SELECT COUNT(helpee) FROM Subscriptions WHERE helper = " + userId + " ) AS following"
                + " FROM Users WHERE id='" + userId + "'";
    }

    public static String createSearchFoundationQuery(String searchArg) {
        return "SELECT foundation name,logo FROM Foundations  WHERE name LIKE %" + searchArg + "%";
    }

    public static String createLogOutQuery(String id) {
        return "DELETE FROM ActiveUsers WHERE Session = '" + id + "'";
    }

    public static String createDonateQuery(String userId, String username, String nrCoins) {
        return "UPDATE Projects SET Projects.helpcoins= Projects.helpcoins + " + nrCoins
                + " WHERE Projects.user = (SELECT id FROM Users WHERE username = '" + username
                + "') AND Projects.completiondate IS NULL;";
    }

    public static String removeCoin(String userId, String nrCoins) {
        return "UPDATE Users SET helpcoins= Users.helpcoins - " + nrCoins + " WHERE Users.id = " + userId;
    }

    public static String createGetProjectsQueryFromUser(String userId) {
        return "SELECT FoundationProject.projectname,Projects.goal,Projects.helpcoins,Projects.creationdate, Projects.completiondate "
                + "FROM Projects  INNER JOIN FoundationProject ON FoundationProject.id = Projects.foundationProject "
                + "WHERE Projects.user = " + userId;
    }

    public static String creategetHelpingQuery(String userId) {
        return "SELECT Users.username FROM Users INNER JOIN Subscriptions ON Users.id = Subscriptions.helpee AND Subscriptions.helper = "
                + userId;
    }

    public static String createGetHelpersQuery(String userId) {
        return "SELECT Users.username FROM Users INNER JOIN Subscriptions ON Users.id = Subscriptions.helper AND Subscriptions.helpee = "
                + userId;
    }

    public static String createDonateQueryAnonymous(String username, String coins) {
        return "UPDATE Projects SET helpcoins = helpcoins + " + coins + " WHERE "
                + "user = (SELECT Users.id FROM Users WHERE Users.username = '" + username + "') "
                + "AND completiondate IS NULL";
    }

    public static String checkNewUserEmail(String email) {
        return "SELECT COUNT(tem.username) FROM (" + "SELECT `username` FROM Users WHERE email = '" + email + "'\n"
                + "UNION\n" + "SELECT `username` FROM UnverifiedUsers WHERE email = '" + email + "'" + ") as tem;";
    }

    public static String checkNewUserName(String name) {
        return "SELECT COUNT(tem.username) FROM (" + "SELECT `username` FROM Users WHERE username = '" + name + "'\n"
                + "UNION\n" + "SELECT `username` FROM UnverifiedUsers WHERE username = '" + name + "'" + ") as tem;";
    }

    static String getNotifications(String userID) {
        return "SELECT `info`,`date` FROM Notifications WHERE id = " + userID + " ORDER BY date DESC";
    }

    static String getOpenProjects(String id) {
        return "SELECT Users.username,Projects.helpcoins,Projects.goal, Projects.creationdate,FoundationProject.projectname , Foundations.foundationname \n"
                + "FROM (((Users\n" + "INNER JOIN Projects ON Projects.user = Users.id)\n"
                + "INNER JOIN FoundationProject ON FoundationProject.id = Projects.foundationProject)\n"
                + "INNER JOIN Foundations ON Foundations.id = FoundationProject.foundation)\n"
                + "WHERE (Users.id IN (SELECT Subscriptions.helpee FROM Subscriptions WHERE Subscriptions.helper = "
                + id + ")OR Users.id = " + id + " ) AND Projects.completiondate is NULL;";
    }

    public static String createSetForgotPasswordQuery(String email, String code) {
        return "INSERT INTO `SpecialRequests`(`id`, `code`) VALUES ((SELECT Users.id FROM Users WHERE email = '" + email
                + "')," + code + ")";
    }

    static String createGetUsernameByMailQuery(String email) {
        return "SELECT username FROM Users WHERE email = '" + email + "'";
    }

    public static String createChangePasswordQuery(String code, String password) {
        return "UPDATE Users SET Users.password = '" + password
                + "' WHERE Users.id = (SELECT id FROM SpecialRequests WHERE code = " + code + ")";
    }

    public static String createClearSpecialRequestQuerie(String code) {
        return "DELETE FROM `SpecialRequests` WHERE code = " + code;
    }

    public static String createDonationNotification(String userId, String username, String coins) {
        return "INSERT INTO `Notifications` (`id`, `info`, `date`) VALUES ((SELECT `id` FROM Users WHERE `username` = '"
                + username + "'), CONCAT((SELECT `username` from Users WHERE `id`= " + userId + "), \' has donated "
                + coins + " coStrings to your project.\'), CURRENT_TIMESTAMP)";
    }

    static String createPeopleYouMightKnowQuery(String id) {
        return "SELECT COUNT(Subscriptions.helper) AS common,Users.username \n" + "FROM Subscriptions \n"
                + "INNER JOIN Users ON Subscriptions.helpee=Users.id\n"
                + "WHERE Subscriptions.helper IN (SELECT `helper` FROM Subscriptions WHERE helpee = " + id
                + ") AND helpee != " + id + " GROUP BY Users.username ORDER BY common DESC LIMIT 10";
    }

    public static String registerDonation(String userId, String username, String coins) {
        return "INSERT INTO `Donations`(`user`, `helpcoins`, `project`) VALUES (" + userId + "," + coins
                + ",(SELECT Projects.id FROM Projects WHERE Projects.user = (SELECT Users.id FROM Users WHERE Users.username = '"
                + username + "') AND Projects.completiondate IS NULL))";
    }

    public static String trendingProjects() {
        return "SELECT SUM(Donations.helpcoins) AS totalHelpcoins,FoundationProject.projectname,Projects.helpcoins,Projects.goal,Projects.creationdate,Users.username,Foundations.foundationname\n"
                + "FROM Donations \n" + "INNER JOIN Projects ON Donations.project = Projects.id\n"
                + "INNER JOIN FoundationProject ON Projects.foundationProject = FoundationProject.id\n"
                + "INNER JOIN Users ON Projects.user = Users.id\n"
                + "INNER JOIN Foundations ON FoundationProject.foundation = Foundations.id\n"
                + "WHERE Donations.project IN (SELECT Projects.id FROM Projects WHERE Projects.creationdate >= ADDDATE(CURRENT_DATE,INTERVAL -7 DAY))\n"
                + "GROUP BY FoundationProject.projectname\n" + "ORDER BY totalHelpcoins DESC LIMIT 10";
    }

    static String deleteNotifications(String id) {
        return "DELETE FROM `Notifications` WHERE id = " + id;
    }

    static String getNewPosts() {
        return "SELECT Posts.short,Posts.content,Posts.hashtags,Posts.title,Foundations.foundationname FROM Posts\n"
                + "INNER JOIN Foundations ON Posts.foundation = Foundations.id\n"
                + "ORDER BY Posts.date DESC LIMIT 100;";
    }

    public static String checkIfAlreadyFollowed(String userId, String user) {
        return "SELECT Users.username FROM Users INNER JOIN Subscriptions ON Users.id = Subscriptions.helpee "
                + "AND Subscriptions.helpee IN (SELECT id FROM Users WHERE username = '" + user + "') "
                + "AND Subscriptions.helper = " + userId;
    }

    public static String createFollowingNotification(boolean result, String username, String userId) {
        if (result) {
            return "INSERT INTO `Notifications` (`id`, `info`, `date`) VALUES ((SELECT `id` FROM Users WHERE `username` = '"
                    + username + "'), CONCAT((SELECT `username` from Users WHERE `id`= " + userId
                    + "), \' has started helping you back.\'), CURRENT_TIMESTAMP)";
        } else {
            return "INSERT INTO `Notifications` (`id`, `info`, `date`) VALUES ((SELECT `id` FROM Users WHERE `username` = '"
                    + username + "'), CONCAT((SELECT `username` from Users WHERE `id`= " + userId
                    + "), \' has started helping you.\'), CURRENT_TIMESTAMP)";
        }
    }

    public static String checkProjectCompletion(String userId) {
        return "SELECT `id` FROM Projects WHERE user = " + userId + " AND completiondate IS NULL";
    }

    public static String checkIfLoggedIn(String username) {
        return "Select session From ActiveUsers Where userId in (Select username from ActiveUsers WHERE username = '"
                + username + "')";
    }

    public static String getUsernameById(String userId) {
        return "SELECT username FROM users WHERE id = " + userId;
    }

    static String getFoundationPictures(String foundationName, String hash) {
        return "SELECT FoundationPictures.code from FoundationPictures"
                + "Inner JOIN Foundations AT FoundationPictures.foundation = Foundation.id"
                + "WHERE Foundations.name = '" + foundationName + "' " + "AND Foundation.hash = '" + hash + "'";
    }

    static String getFoundationPictures(String foundationName) {
        return "SELECT `code` from FoundationPictures WHERE foundation = (SELECT id FROM Foundations WHERE foundationname = '"
                + foundationName + "') LIMIT 3";
    }

    static String getPost(String foundationname, String postTitle) {
        return "SELECT Posts.short,Posts.content,Posts.date,Posts.hashtags "
                + "FROM Posts WHERE Posts.foundation = (SELECT id FROM Foundations " + "WHERE foundationname = '"
                + foundationname + "')" + "AND Posts.title = '" + postTitle + "'";
    }

    static String getUserProject(String projectID) {
        return "SELECT Projects.helpcoins,Projects.goal,Projects.creationdate,"
                + "Projects.completiondate,FoundationProject.projectname,"
                + "Foundations.foundationname,Users.username\n" + "FROM Projects\n"
                + "INNER JOIN FoundationProject ON FoundationProject.id = Projects.foundationProject\n"
                + "INNER JOIN Foundations ON Foundations.id = FoundationProject.foundation\n"
                + "INNER JOIN Users ON Users.id = Projects.user\n" + "WHERE Projects.id = " + projectID;
    }

    static String getFoundationPosts(String foundationName) {
        return "SELECT Posts.short, Posts.content , Posts.title , Posts.date,Posts.hashtags\n" + "FROM Posts\n"
                + "INNER JOIN Foundations ON Foundations.id = Posts.foundation\n"
                + "WHERE Foundations.foundationname = '" + foundationName + "'";
    }

    static String getSurvey() {
        return "SELECT url,coins,picture FROM Survey WHERE expirationdate < CURRENT_DATE AND launchdate <= CURRENT_DATE";
    }

    static String getSpecials() {
        return "SELECT url,coins,picture FROM SpecialRequests WHERE expirationdate < CURRENT_DATE AND launchdate <= CURRENT_DATE";
    }

    static String getStats() {
        return "SELECT (SELECT raised FROM Funding WHERE date > ADDDATE(CURRENT_DATE,INTERVAL -1 MONTH)) AS raised,\n"
                + "(SELECT SUM(raised) FROM Funding) AS raised_total,\n"
                + "(SELECT COUNT(DISTINCT user) FROM Donations) AS users,\n"
                + "(SELECT COUNT(id) FROM `Projects` WHERE completiondate IS NOT NULL) AS completed_projects,\n"
                + "(SELECT COUNT(accumulated) FROM Users) AS helpcoins_generated,\n"
                + "(SELECT SUM(helpcoins) FROM Donations) AS helpcoins_donated";
    }

    static String getFoundationInfo(String foundation) {
        return "SELECT Foundations.foundationname,Foundations.info,Foundations.logo,Foundations.type,Foundations.reward,Foundations.website,SUM(Projects.helpcoins) AS helpcoins_recieved,COUNT(Projects.foundationProject) AS projects_completed FROM Foundations\n"
                + "INNER JOIN FoundationProject ON FoundationProject.foundation = Foundations.id\n"
                + "INNER JOIN Projects ON Projects.foundationProject = FoundationProject.id\n"
                + "WHERE Foundations.foundationname = '" + foundation + "' AND Projects.completiondate IS NOT NULL";
    }

    static String getPeopleWhoDonated(String projectID) {
        return "SELECT username FROM Users WHERE id IN (SELECT DISTINCT Donations.user FROM Donations WHERE Donations.project = "+projectID+")";
    }

    public static String createUserProject(String userID, String projectname, String foundation, String goal) {
        return  "INSERT INTO `Projects`(`foundationProject`, `goal`, `user`) VALUES\n" +
                "((SELECT FoundationProject.id FROM FoundationProject WHERE FoundationProject.foundation = \n" +
                "(SELECT Foundations.id FROM Foundations WHERE Foundations.foundationname = '"+foundation+"')\n" +
                "AND FoundationProject.projectname = '"+projectname+"'),"+goal+","+userID+")";
    }
    public static String getAllFoundationProjectsWithGPS(){
        return "SELECT foundation,projectname,gpsX,gpsY FROM FoundationProject";
    }
}
