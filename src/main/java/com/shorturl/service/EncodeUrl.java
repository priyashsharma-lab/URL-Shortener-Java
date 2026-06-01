package com.shorturl.service;

import com.shorturl.dao.DatabaseManager;
import com.shorturl.util.UrlInfo;

public class EncodeUrl 
{
    private final String originalUrl;
    private String encodedUrl;
    public EncodeUrl(String originalUrl) 
    {
        this.originalUrl=originalUrl;
        encodedUrl="";
    }
    public String generateEncodedUrl()
    {
        UrlInfo urlinfo=new UrlInfo(originalUrl);
        if (!urlinfo.isValidUrl())
        {
            return "Invalid URL";
        }
        DatabaseManager dbMgr=new DatabaseManager();
        Integer dbId=dbMgr.saveOriginalUrlGetId(originalUrl);
        if (dbId==-1)
        {
            return "Database Error dbId=-1 --> check saveOriginalUrlGetId";
        }
        String domainName=urlinfo.getDomainName();
        for (int i=0;i<3;i++)
        {
            int c=(int)domainName.charAt(i);
            c+=3;
            encodedUrl+=(char)c;
        }
        encodedUrl+="-";
        encodedUrl+=dbId.toString();
        dbMgr.saveEncodedUrl(encodedUrl, dbId);
        return encodedUrl;
    }
        
}
