/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nowscreeningtv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author krish
 */

public class NowScreeningTV {

    public static Connection con = null;
    public static int cred_id = -1;
    public static int user_id = -1;
    public static Scanner sc = new Scanner(System.in);



    public NowScreeningTV(int user_id, int cred_id) {
        NowScreeningTV.user_id = user_id;
        NowScreeningTV.cred_id = cred_id;
    }

    public static int getUser_id() {
        return user_id;
    }

    public static void setUser_id(int user_id) {
        NowScreeningTV.user_id = user_id;
    }
    
    public static int getCred_id() {
        return cred_id;
    }

    public static void setCred_id(int cred_id) {
        NowScreeningTV.cred_id = cred_id;
    }

    public NowScreeningTV(Connection con) {
        NowScreeningTV.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        NowScreeningTV.con = con;
    }
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
   
    try
    {
    con = DBConnection.createConnection();
    String check_login = "false";
    
    while("false".equals(check_login)){
        System.out.println("Please enter your Username");
        String set_username = sc.next();
        System.out.println("Please enter your Password");
        String set_password = sc.next();
    
    check_login = NowScreeningTV.checkLogin(set_username, set_password);
    }
    
    System.out.println("Logged in Successfully. Welcome " + check_login + " " + getUser_id());
        
    switch (check_login) {
        case "admin":
            loginAsAdmin();
            break;
        case "employee":
            loginAsEmployee();
            break;
        case "user":
            loginAsUser();
            break;
        default:
            System.out.println("Logged out Successfully.");
            break;
    }
        
    }
    catch(ClassNotFoundException | SQLException e)
    {
    }
    finally
    {
        try
        {
            if(con!=null) DBConnection.closeConnection();
            System.out.println("Connection closed Successfully.");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }    
        
    }
    // End of main
    
    

public static String checkLogin(String username, String password) throws SQLException {
        PreparedStatement preparedst;
        int count_exists = 0;
        String user_type = "";
        int cred_exists = 0;
        String verify_query = "select count(*) AS user_exists, type, id from credentials"
                                             + " where username = ? and password = ?";
        preparedst = NowScreeningTV.con.prepareStatement(verify_query);
        preparedst.setString(1, username);
        preparedst.setString(2, password);
        ResultSet rs= preparedst.executeQuery();
        while(rs.next()){
            count_exists = rs.getInt("user_exists");
            user_type = rs.getString("type");
            cred_exists = rs.getInt("id");
        }
        if(count_exists>0 && !"".equals(user_type) && getUserIdDetails(cred_exists).equals("true")){
          NowScreeningTV.setCred_id(cred_exists);
          return user_type;
        } 
          return "false";
    }
    
public static String getUserIdDetails(int credential_id) throws SQLException {
        PreparedStatement preparedst;
        int count_exists = 0;
        int userID = 0;
        String user_query = "select count(*) AS user_exists, credid from userdetails"
                                             + " where credid = ?";
        preparedst = NowScreeningTV.con.prepareStatement(user_query);
        preparedst.setInt(1, credential_id);
        ResultSet rs= preparedst.executeQuery();
        while(rs.next()){
            count_exists = rs.getInt("user_exists");
            userID = rs.getInt("credid");
        }
        if(count_exists>0){
          NowScreeningTV.setUser_id(userID);
           return "true";
        } 
          return "false";
    }

    public static void loginAsAdmin() throws SQLException {
        System.out.println("Please select the following operations");
        System.out.println("1. Add a new ADMIN");
        System.out.println("2. Remove an ADMIN");
        System.out.println("3. Add a new EMPLOYEE");
        System.out.println("4. Remove an EMPLOYEE");
        System.out.println("5. Remove a USER");
        System.out.println("6. Log Out");
        int operation = NowScreeningTV.sc.nextInt();
        
        switch (operation) {
        case 1:
            addNewAdmin();
        case 2:
            removeAdmin();
        case 3:
            addNewEmployee();
        case 4:
            removeEmployee();
        case 5:
            removeUserAdmin();
        case 6:
            System.out.println("Logged out Successfully.");
            break;
        default:
            loginAsAdmin();
    }
        
   }
    
    public static void loginAsEmployee() throws SQLException {
        System.out.println("Please select the following operations");
        System.out.println("1. Add a TV Show");
        System.out.println("2. Add a TV Season");
        System.out.println("3. Add a TV Episode");
        System.out.println("4. Pause Notifications for a User");
        System.out.println("5. Un-Pause Notifications for a User");
        System.out.println("6. Remove a USER");
        System.out.println("7. Log Out");
        int operation = NowScreeningTV.sc.nextInt();
        
        switch (operation) {
        case 1:
            addTvShow();
        case 2:
            addTvSeason();
        case 3:
            addTvEpisode();
        case 4:
            pauseNotifications();
        case 5:
            unPauseNotifications();
        case 6:
            removeUserEmployee();
        case 7:
            System.out.println("Logged out Successfully.");
            break;
        default:
            loginAsEmployee();
    }
    }
    
    public static void loginAsUser() throws SQLException {
        System.out.println("Please select the following operations");
        System.out.println("1. Check Notifications");
        System.out.println("2. Explore TV Shows");
        System.out.println("3. Subscribe to a TV Show");
        System.out.println("4. Mark an Episode as watched");
        System.out.println("5. Pay the Bill");
        System.out.println("6. See all watched");
        System.out.println("7. Log Out");
        int operation = NowScreeningTV.sc.nextInt();
        
        switch (operation) {
        case 1:
            checkNotifications();
        case 2:
            exploreTvShows();
        case 3:
            subscribeTvShow();
        case 4:
            markEpisodeWatched();
        case 5:
            payBills();
        case 6:
            retrieveWatched();
        case 7:
            System.out.println("Logged out Successfully.");
            break;
        default:
            loginAsAdmin();
    }
    }

    public static void addNewAdmin() throws SQLException {
        PreparedStatement pst;
        int count = 0;
        System.out.println("Please enter new Admin Username");
        String set_username = sc.next();
        System.out.println("Please enter new Admin Password");
        String set_password = sc.next();
        String add_admin_query = "insert into credentials(username, password, type) " +
                                 "VALUES (?,?,'admin')";
        pst = con.prepareStatement(add_admin_query);
        pst.setString(1, set_username);
        pst.setString(2, set_password);
        count = pst.executeUpdate();
	System.out.println(count + " rows updated");
        System.out.println();
        pst.close();  
    }

    public static void removeAdmin() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void addNewEmployee() throws SQLException {
        PreparedStatement pst;
        int count = 0;
        System.out.println("Please enter new Employee Username");
        String set_username = sc.next();
        System.out.println("Please enter new Employee Password");
        String set_password = sc.next();
        String add_admin_query = "insert into credentials(username, password, type) " +
                                 "VALUES (?,?,'employee')";
        pst = con.prepareStatement(add_admin_query);
        pst.setString(1, set_username);
        pst.setString(2, set_password);
        count = pst.executeUpdate();
	System.out.println(count + " rows updated");
        System.out.println();
        pst.close();
    }

    public static void removeEmployee() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void removeUserAdmin() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void addTvShow() throws SQLException {
        PreparedStatement pst;
        int count = 0;
        System.out.println("Please enter new TV Show Name");
        String set_name = sc.next();
        System.out.println("Please enter new TV Show Genre");
        String set_genre = sc.next();
        System.out.println("Please enter new TV Show Rating out of 10");
        double set_rating = sc.nextDouble();
        String add_tvs_query = "insert into tvshows(tvsname, tvsgenre, tvrating) " +
                                 "VALUES (?,?,?)";
        pst = con.prepareStatement(add_tvs_query);
        pst.setString(1, set_name);
        pst.setString(2, set_genre);
        pst.setDouble(3, set_rating);
        count = pst.executeUpdate();
	System.out.println(count + " rows updated");
        System.out.println();
        pst.close();
    }

    private static void addTvSeason() throws SQLException {
        PreparedStatement pst;
        int count = 0;
        System.out.println("Please enter existing TV Show Name");
        String set_name = sc.next();
        System.out.println("Please enter new TV Show Season Number");
        int set_number = sc.nextInt();
        String add_tvseason_query = "insert into tvseasons(snumber, tvshowid)" +
                                    "select ?, tvshow.id" +
                                    "from tvshows tvshow" +
                                    "where tvshow.name = ?";
        pst = con.prepareStatement(add_tvseason_query);
        pst.setInt(1, set_number);
        pst.setString(2, set_name);
        count = pst.executeUpdate();
	System.out.println(count + " rows updated");
        System.out.println();
        pst.close();
    }
    
    public static void addTvEpisode() throws SQLException {
      PreparedStatement pst;
        int count = 0;
        System.out.println("Please enter existing TV Show Name");
        String set_showName = sc.next();
        System.out.println("Please enter existing TV Show Season number");
        int set_season = sc.nextInt();
        System.out.println("Please enter new Episode Number");
        int set_epNumber = sc.nextInt();
        System.out.println("Please enter new Episode Name");
        String set_epName = sc.next();
        System.out.println("Please enter new Episode Rating out of 10");
        double set_rating = sc.nextDouble();
        String add_tvepisode_query = "insert into tvepisodes(epnumber, epname, eprating, tvseasonid)" +
                                    "select ?, ?, ?, tvshow.id" +
                                    "from tvshows tvshow, tvseasons season" +
                                    "where season.tvshowid = tvshow.id" +
                                    "and season.snumber = ?" +
                                    "and tvshow.name = ?";
        pst = con.prepareStatement(add_tvepisode_query);
        pst.setInt(1, set_epNumber);
        pst.setString(2, set_epName);
        pst.setDouble(3, set_rating);
        pst.setInt(4, set_season);
        pst.setString(5, set_showName);
        count = pst.executeUpdate();
	System.out.println(count + " rows updated");
        System.out.println();
        pst.close();
    }

    public static void pauseNotifications() throws SQLException {
        PreparedStatement pst;
        int count = 0;
        System.out.println("Please enter the username of the user to pause the user's notifications");
        String set_name = sc.next();
        String pause_notif_query = "insert into tvseasons(snumber, tvshowid)" +
                                    "select ?, tvshow.id" +
                                    "from tvshows tvshow" +
                                    "where tvshow.name = ?";
        pst = con.prepareStatement(pause_notif_query);
        pst.setString(2, set_name);
        count = pst.executeUpdate();
	System.out.println(count + " rows updated");
        System.out.println();
        pst.close();
    }

    public static void unPauseNotifications() {
      //  update notify to false in notifications based on username of the user
    }

    public static void removeUserEmployee() {
      //  remove employee
    }

    public static void checkNotifications() {
      //  select all new tv shows which are not watched
    }

    public static void exploreTvShows() {
      //  select all tv shows
    }

    public static void subscribeTvShow() {
      //  insert into notifications only if it is not already existing
    }

    public static void markEpisodeWatched() {
      //  insert query for marking an episode watched 
    }

    public static void payBills() {
      //  insert query for payment
    }

    public static void retrieveWatched() {
      //  select query for watched : TV SHOW NAME, TV SEASON, TV SHOW EPISODE , EPISODE NAME
    }
    
    public static void insertUserDetails() {
      //  insert query to insert user details
    }
    
}
