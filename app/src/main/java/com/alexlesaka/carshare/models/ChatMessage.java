package com.alexlesaka.carshare.models;

import java.util.Date;

/**
 * Created by aabuin on 22/08/2017.
 */

public class ChatMessage
{
    String username="";
    String message= "";
    Date date;

    public ChatMessage(){}
    public ChatMessage(String username, String message, Date date)
    {
        this.username=username;
        this.message=message;
        this.date = date;
    }

    public String getUsername() {return username;}

    public String getMessage() {return message;}

    public Date getDate(){return date;}

}
