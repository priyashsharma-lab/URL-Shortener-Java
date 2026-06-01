package com.shorturl.service;

import com.shorturl.dao.DatabaseManager;

public class DecodeUrl 
{
    public String encodedUrl;
    public String originalUrl;
    public DecodeUrl(String encodedUrl)
    {
        this.encodedUrl=encodedUrl;
        originalUrl="";
    }    
    public String getOriginalUrl()
    {
        DatabaseManager dbMgr=new DatabaseManager();
        int dbId=Integer.parseInt(encodedUrl.split("-")[1]);
        originalUrl=dbMgr.getOriginalUrl(dbId);
        return originalUrl;
    }
}
