package edu.northeastern.numad23sp_parthkhaladkar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Patterns;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class WebLink {
    private String Name;
    private String linkUrl;

    public WebLink(String linkName, String linkUrl) {
        this.Name = linkName;
        this.linkUrl = linkUrl;
    }


    public String getName() {

        return Name;
    }

    public String getLinkUrl() {

        return linkUrl;
    }

    public void onLinkUnitClicked(Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl));
        context.startActivity(browserIntent);
    }




    public boolean Verifylink() {
        try {
            new URL(linkUrl).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return Patterns.WEB_URL.matcher(linkUrl).matches();
    }
}
