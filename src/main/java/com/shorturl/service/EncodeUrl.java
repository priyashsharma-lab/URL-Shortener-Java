package com.shorturl.service;

import com.shorturl.dao.DatabaseManager;
import com.shorturl.util.DBUtil;
import com.shorturl.util.UrlInfo;

public class EncodeUrl 
{
    private final String originalUrl;
    private String encodedUrl;
    private String expireTime;
    public EncodeUrl(String originalUrl,String expireTime) 
    {
        this.originalUrl=originalUrl;
        encodedUrl="";
        this.expireTime=expireTime;
    }
    public String generateEncodedUrl()
    {
        if (DBUtil.isOriginalUrlExists(originalUrl))
        {
            encodedUrl=DBUtil.getEncodedUrl(originalUrl);
            return encodedUrl;
        }
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
            switch(c)
            {
                case 120: c=97; break;
                case 121: c=98; break;
                case 122: c=99; break;
                case 88: c=65; break;
                case 89: c=66; break;
                case 90: c=67; break;
                default: c+=3; break;
            }
            encodedUrl+=(char)c;
        }
        encodedUrl+="-";
        encodedUrl+=dbId.toString();
        dbMgr.saveEncodedUrl(encodedUrl, dbId);
        dbMgr.setExpireDate(dbId,expireTime);
        return encodedUrl;
    }
        
}
