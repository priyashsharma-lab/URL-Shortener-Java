package com.shorturl.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

import com.shorturl.util.DBUtil;
public class AnalyticsManager 
{
    public static int getTotalUrls()
    {
        try
        {
            int totalUrls=-1;
            Connection con=DBUtil.getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("Select COUNT(*) from urlcodes;");
            if (rs.next())
            {
                totalUrls=rs.getInt(1);
            }
            st.close();
            con.close();
            return totalUrls;
        }
        catch(SQLException e)
        {
            System.out.println("Database Error from getTotalUrls--> "+e.getMessage());
            return -1;
        }
    }   
    public static int getActiveUrls() 
    {
        try
        {
            int activeUrls=0;
            Connection con=DBUtil.getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("Select * from urlcodes;");
            while (rs.next())
            {
                int dbId=rs.getInt("id");
                if (!DBUtil.isUrlExpired(dbId))
                {
                    activeUrls+=1;
                }
            }
            st.close();
            con.close();
            return activeUrls;
        }
        catch(SQLException e)
        {
            System.out.println("Database Error from getActiveUrls--> "+e.getMessage());
            return -1;
        }
        
    }
    public static int getExpiredUrls() 
    {
        try
        {
            int expiredUrls=0;
            Connection con=DBUtil.getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("Select * from urlcodes;");
            while (rs.next())
            {
                int dbId=rs.getInt("id");
                if (DBUtil.isUrlExpired(dbId))
                {
                    expiredUrls+=1;
                }
            }
            st.close();
            con.close();
            return expiredUrls;
        }
        catch(SQLException e)
        {
            System.out.println("Database Error from getExpiredUrls--> "+e.getMessage());
            return -1;
        }
        
    }
    public static int getTotalVisits() 
    {
        try
        {
            int totalVisits=0;
            Connection con=DBUtil.getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("Select SUM(visitedCount) from urlcodes;");
            if (rs.next())
            {
                totalVisits=rs.getInt(1);
            }
            st.close();
            con.close();
            return totalVisits;
        }
        catch(SQLException e)
        {
            System.out.println("Database Error from getTotalVisits--> "+e.getMessage());
            return -1;
        }
        
    }
    public static double getAverageVisits() 
    {
        try
        {
            double avgVisits=0;
            Connection con=DBUtil.getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("Select AVG(visitedCount) from urlcodes;");
            if (rs.next())
            {
                avgVisits=rs.getDouble(1);
            }
            st.close();
            con.close();
            return avgVisits;
        }
        catch(SQLException e)
        {
            System.out.println("Database Error from getAverageVisits--> "+e.getMessage());
            return -1;
        }
        
    }
    public static String[] getMostViewedUrl() 
    {
        try
        {
            String mostViewedUrl[]=new String[2];
            Connection con=DBUtil.getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("Select encodedUrl,visitedCount from urlcodes ORDER BY visitedCount DESC LIMIT 1; ");
            if (rs.next())
            {
                mostViewedUrl[0]=rs.getString(1);
                mostViewedUrl[1]=String.valueOf(rs.getInt(2));
            }
            st.close();
            con.close();
            return mostViewedUrl;
        }
        catch(SQLException e)
        {
            System.out.println("Database Error from getAverageVisits--> "+e.getMessage());
            return null;
        }
        
    }
    public static LinkedHashMap<String,Integer> getTop5Urls()
    {
        try
        {
            LinkedHashMap<String,Integer> top5Urls=new LinkedHashMap<>();
            Connection con=DBUtil.getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("Select encodedUrl,visitedCount from urlcodes ORDER BY visitedCount DESC LIMIT 5; ");
            while (rs.next())
            {
                top5Urls.put(rs.getString(1),rs.getInt(2));
            }
            st.close();
            con.close();
            return top5Urls;
        }
        catch(SQLException e)
        {
            System.out.println("Database Error from getAverageVisits--> "+e.getMessage());
            return null;
        }

    }
}
