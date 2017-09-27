package com.alexlesaka.carshare.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alexlesaka.carshare.activities.Group.GroupActivity;

/**
 * Created by aabuin on 24/08/2017.
 */

public class OnClickOpenGroupActivity implements View.OnClickListener
{
    private Context context;
    private String groupId;

    public OnClickOpenGroupActivity(Context context, String groupid)
    {
        super();
        this.context=context;
        this.groupId = groupid;
    }

    @Override
    public void onClick(View v)
    {
        Intent i = new Intent(context, GroupActivity.class);
        i.putExtra("groupid", groupId);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(i);
    }
}
