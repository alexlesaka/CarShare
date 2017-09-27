package com.alexlesaka.carshare.listeners;

import android.content.Context;
import android.view.View;

import com.alexlesaka.carshare.controllers.MainController;

/**
 * Created by aabuin on 24/08/2017.
 */

public class OnClickButtonAddFriend implements View.OnClickListener
{
    private Context context;
    private String friendUsername;

    public OnClickButtonAddFriend(Context context, String friendUsername)
    {
        super();
        this.context=context;
        this.friendUsername = friendUsername;
    }

    @Override
    public void onClick(View v)
    {
        ((MainController) context.getApplicationContext()).getLoginController().getFriendshipController().createFriendshipRequest(friendUsername);
    }
}
