package com.shorturl.service;

import com.shorturl.dao.DatabaseManager;
import com.shorturl.util.DBUtil;

public class DeleteUrl 
{
    private String originalUrl;
    public DeleteUrl(String originalUrl)
    {
        this.originalUrl=originalUrl;
    }    
    public String deleteUrl()
    {
        if (!DBUtil.isOriginalUrlExists(originalUrl))
        {
            return "URL Not Found";
        }
        DatabaseManager dbMgr=new DatabaseManager();
        if (dbMgr.deleteUrlFromDatabase(originalUrl))
        {
            return "URL Deleted Successfully";
        }
        else
        {
            return "Some Internal Error";
        }
    }
}
