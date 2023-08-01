package com.example.elahefinal.User;

import com.example.elahefinal.DB.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    public static User loginUser;
    private String fullName,username,password;
    private DB db;
    public User(String fullName, String username, String password) {
        this.db = new DB();
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.db = new DB();
        this.username = username;
        this.password = password;
    }

    public User() {
        this.db = new DB();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean login(User user) throws SQLException {
        String query = "SELECT * FROM users where username = '"+user.getUsername()+"';";
        ResultSet resultSet = this.db.requestSelect(query);
        if(resultSet != null){
            resultSet.next();
            String username = resultSet.getString(1);
            String password = resultSet.getString(2);
            String fullName = resultSet.getString(3);
            boolean result = user.getPassword().equals(password);
            if(result){
                User.loginUser = new User(fullName,username,password);
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    public boolean signup(User user){
        String query = "INSERT INTO users VALUE('"+user.getUsername()+"','"+user.getPassword()+"','"+user.getFullName()+"');";
        boolean result = this.db.requestInsert(query);
        return result;
    }
    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
