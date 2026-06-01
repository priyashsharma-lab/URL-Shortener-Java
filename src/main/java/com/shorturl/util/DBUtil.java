package com.shorturl.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil
{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shorturl";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "password";
    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public static boolean isOriginalUrlExists(String originalUrl)
    {
        try
        {
            Connection con=DBUtil.getConnection();
            PreparedStatement pst=con.prepareStatement("Select * from urlcodes where originalUrl=?");
            pst.setString(1, originalUrl);
            ResultSet rs=pst.executeQuery();
            boolean b=rs.next();
            pst.close();
            con.close();
            return b;
        }
        catch(SQLException e)
        {
            System.out.println("Database Error from isOriginalUrlExixsts in DBUtil--> "+e.getMessage());
            return false;
        }
    }

    public static String getEncodedUrl(String originalUrl)
    {
        String encodedUrl="";
        try
        {
            Connection con=DBUtil.getConnection();
            PreparedStatement pst=con.prepareStatement("Select encodedUrl from urlcodes where originalUrl=?");
            pst.setString(1, originalUrl);
            ResultSet rs=pst.executeQuery();
            if (rs.next())
            {
                encodedUrl=rs.getString("encodedUrl");
            }
            return  encodedUrl;
        }
        catch(SQLException e)
        {
            System.out.println("Database Error from getEncodedUrl in DBUtil--> "+e.getMessage());
            return "Error";
        }
    }
}