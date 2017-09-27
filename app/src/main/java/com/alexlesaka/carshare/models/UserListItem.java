package com.alexlesaka.carshare.models;

/**
 * Created by aabuin on 23/08/2017.
 */

public class UserListItem
{
    private String username;

    public UserListItem(){}

    public UserListItem(String username)
    {
        this.username=username;
    }

    public String getUsername(){return username;}
}
