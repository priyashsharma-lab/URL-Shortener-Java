package com.shorturl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shorturl.util.DBUtil;

public class DatabaseManager 
{
    public int saveOriginalUrlGetId(String originalUrl)
    {
        try
        {
            Connection con=DBUtil.getConnection();
            PreparedStatement pst=con.prepareStatement("INSERT INTO urlcodes(originalUrl) VALUES (?)",PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, originalUrl);
            pst.executeUpdate();
            ResultSet rs=pst.getGeneratedKeys();
            int dbId=0;
            if (rs.next())
            {
                dbId=rs.getInt(1);
            }
            pst.close();
            con.close();
            System.out.println("saveoriginalurland get id success--> "+dbId);
            return dbId;
        }
        catch(SQLException e)
        {
            System.out.println("DataBase Error from saveOriginalUrlGetId --> "+e.getMessage());
            return -1;
        }
    }

    public void saveEncodedUrl(String encodedUrl,int dbId)
    {
        try
        {
            Connection con=DBUtil.getConnection();
            PreparedStatement pst=con.prepareStatement("UPDATE urlcodes SET encodedUrl=? where id=?");
            pst.setString(1, encodedUrl);
            pst.setInt(2, dbId);
            pst.execute();
            pst.close();
            con.close();
            System.out.println("Save encoded url success");
        }
        catch(SQLException e)
        {
            System.out.println("DataBase Error"+e.getMessage());
        }
    }

    public String getOriginalUrl(int dbId)
    {
        String originalUrl="";
        try
        {
            Connection con=DBUtil.getConnection();
            PreparedStatement pst=con.prepareStatement("SELECT originalUrl,visitedCount from urlcodes where id=?");
            pst.setInt(1, dbId);
            ResultSet rs=pst.executeQuery();
            if (rs.next())
            {
                originalUrl=rs.getString("originalUrl");
                int visitedCount=rs.getInt("visitedCount")+1;
                PreparedStatement pst2=con.prepareStatement("UPDATE urlcodes SET visitedCount=? where id=?");
                pst2.setInt(1, visitedCount);
                pst2.setInt(2, dbId);
                pst2.execute();
                System.out.println("visitedCount increased --> "+visitedCount);
                pst2.close();
            }
            else
            {
                originalUrl="URL not Found";
            }
            con.close();
        }
        catch(SQLException e)
        {
            System.out.println("DataBase Error from getOriginalUrl "+e.getMessage());
        }
        return originalUrl;
    }
    public boolean deleteUrlFromDatabase(String originalUrl)
    {
        try
        {
            Connection con=DBUtil.getConnection();
            PreparedStatement pst=con.prepareStatement("DELETE FROM urlcodes where originalUrl=?");
            pst.setString(1,originalUrl); 
            pst.execute(); 
            System.out.println("Deleted successfully");   
            pst.close();
            con.close();  
            return true;
        }
        catch(SQLException e)
        {
            System.out.println("Database Error form deleteUrlFromDatabase--> "+e.getMessage());
            return false;
        }
    }
}
