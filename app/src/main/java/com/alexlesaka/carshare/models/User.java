package com.alexlesaka.carshare.models;

/**
 * Created by aabuin on 22/08/2017.
 */

public class User
{
    String username="username";
    String name="name";
    String imageurl ="url";

    public User(){}
    public User(String username, String name, String imageurl)
    {
        this.username=username;
        this.name=name;
        this.imageurl = imageurl;
    }

    public String getUsername() {return username;}

    public String getName(){return name;}

    public String getImageurl() {return imageurl;}

}
