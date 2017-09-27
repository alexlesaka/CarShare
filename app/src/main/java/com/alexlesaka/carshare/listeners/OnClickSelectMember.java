package com.alexlesaka.carshare.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * Created by aabuin on 24/08/2017.
 */

public class OnClickSelectMember implements View.OnClickListener
{
    private Activity activity;
    private String selectedUsername;
    private String eventId;

    public OnClickSelectMember(Activity activity, String selectedUsername, String eventId)
    {
        super();
        this.activity=activity;
        this.selectedUsername=selectedUsername;
        this.eventId=eventId;
    }

    @Override
    public void onClick(View v)
    {
        Intent i = new Intent();
        i.putExtra("username", selectedUsername);
        if(eventId!="") i.putExtra("eventid", eventId);
        activity.setResult(Activity.RESULT_OK, i);
        activity.finish();
    }
}
