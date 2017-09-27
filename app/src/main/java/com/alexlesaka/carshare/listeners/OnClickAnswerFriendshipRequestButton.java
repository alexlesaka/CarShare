package com.alexlesaka.carshare.listeners;

import android.content.Context;
import android.view.View;

import com.alexlesaka.carshare.controllers.MainController;

/**
 * Created by aabuin on 24/08/2017.
 */

public class OnClickAnswerFriendshipRequestButton implements View.OnClickListener
{

    private final int USERNAME_CODE=1;

    private Context context;
    private String friendUsername;
    private boolean accept;

    public OnClickAnswerFriendshipRequestButton(Context context, String friendUsername, boolean accept)
    {
        super();
        this.context=context;
        this.friendUsername = friendUsername;
        this.accept=accept;
    }

    @Override
    public void onClick(View v)
    {
        if(accept)
        {
            ((MainController) context.getApplicationContext()).getLoginController().getFriendshipController().acceptFriend(friendUsername);
        }
        else
        {
            ((MainController)context.getApplicationContext()).getLoginController().getFriendshipController().declineFriend(friendUsername);
        }
    }
}
