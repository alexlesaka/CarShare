package com.alexlesaka.carshare.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.alexlesaka.carshare.activities.Group.SelectMemberActivity;

/**
 * Created by aabuin on 24/08/2017.
 */

public class OnClickChangePlannedUser implements View.OnClickListener
{

    private final int PLANNEDUSER=4;

    private Activity activity;
    private String groupId;
    private String eventId;
    private String currentUser;

    public OnClickChangePlannedUser(Activity activity, String goupId, String eventid, String currentUser)
    {
        super();
        this.activity = activity;
        this.groupId=goupId;
        this.eventId=eventid;
        this.currentUser=currentUser;
    }

    @Override
    public void onClick(View v)
    {
        Intent i = new Intent(activity.getApplicationContext(), SelectMemberActivity.class);
        i.putExtra("groupid",groupId);
        i.putExtra("eventid",eventId);
        activity.startActivityForResult(i, PLANNEDUSER);
    }
}
