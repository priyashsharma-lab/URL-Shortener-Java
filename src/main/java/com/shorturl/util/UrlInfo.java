package com.shorturl.util;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlInfo
{
    private final String url;

    public UrlInfo(String url) 
    {
        this.url=url;
    }
    public boolean isValidUrl()
    {
        try
        {
            URI uri=new URI(url);
            return uri.getScheme()!=null && uri.getHost()!=null;
        }
        catch(URISyntaxException e)
        {
            return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public String getDomainName()
    {
        try
        {
            URI uri=new URI(url);
            String host=uri.getHost();
            String arr[]=host.split("\\.");
            if (host.startsWith("www."))
            {
                return arr[1];
            }
            else
            {
                return arr[0];
            }
        }
        catch(URISyntaxException e)
        {
            return null;
        }
    }
}