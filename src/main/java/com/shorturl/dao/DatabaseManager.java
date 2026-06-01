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
            System.out.println("DataBase Error"+e.getMessage());
            return 0;
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
            PreparedStatement pst=con.prepareStatement("SELECT originalUrl from urlcodes where id=?");
            pst.setInt(1, dbId);
            ResultSet rs=pst.executeQuery();
            if (rs.next())
            {
                originalUrl=rs.getString("originalUrl");
            }
            else
            {
                originalUrl="URL not Found";
            }
        }
        catch(SQLException e)
        {
            System.out.println("DataBase Error"+e.getMessage());
        }
        return originalUrl;
    }
}
