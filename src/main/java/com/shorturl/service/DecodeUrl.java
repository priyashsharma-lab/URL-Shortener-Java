package com.shorturl.service;

import com.shorturl.dao.DatabaseManager;
import com.shorturl.util.DBUtil;

public class DecodeUrl 
{
    public String encodedUrl;
    public String originalUrl;
    public DecodeUrl(String encodedUrl)
    {
        this.encodedUrl=encodedUrl;
        this.encodedUrl=encodedUrl.split("/")[3];
        System.out.println("EncodedUrl from DecodeUrl-->"+this.encodedUrl);
        originalUrl="";
    }    
    public String getOriginalUrl()
    {
        DatabaseManager dbMgr=new DatabaseManager();
        int dbId;
        try
        {
            dbId=Integer.parseInt(encodedUrl.split("-")[1]);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            return "Enter valid encoded URL";
        }
        originalUrl=dbMgr.getOriginalUrl(dbId);
        if (DBUtil.isUrlExpired(dbId))
        {
            dbMgr.deleteUrlFromDatabase(originalUrl);
            return "URL Expired";
        }
        return originalUrl;
    }
}
