package com.example.elahefinal.DB;
import java.io.IOException;
import java.sql.*;
public class DB {
    public String url = "jdbc:mysql://localhost:3306/paintio";
    public String username = "root";
    public String password = "1234";

    public ResultSet requestSelect(String query){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public boolean requestInsert(String query){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
