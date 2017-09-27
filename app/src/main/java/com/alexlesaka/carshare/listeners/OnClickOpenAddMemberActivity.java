package com.alexlesaka.carshare.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.activities.Group.AddMemberToGroupActivity;

/**
 * Created by aabuin on 24/08/2017.
 */

public class OnClickOpenAddMemberActivity implements View.OnClickListener
{
    private MainController mainController;
    private String groupId;

    public OnClickOpenAddMemberActivity(Context context, String groupId)
    {
        super();
        this.mainController= (MainController) context.getApplicationContext();
        this.groupId =groupId;

    }

    @Override
    public void onClick(View v)
    {
        Intent i = new Intent(mainController, AddMemberToGroupActivity.class);
        i.putExtra("groupid", groupId);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainController.getApplicationContext().startActivity(i);
    }
}
