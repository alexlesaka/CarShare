package com.alexlesaka.carshare.listeners;

import android.content.Context;
import android.view.View;

import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.models.Member;

import java.util.Vector;

/**
 * Created by aabuin on 28/08/2017.
 */

public class OnClickCreateGroupButton implements View.OnClickListener
{
    private Context context;
    private String id;
    private String name;
    private String creatorUsername;
    private Vector<Member> memberVector;

    public OnClickCreateGroupButton(Context context, String id, String name, String creatorUsername, Vector<Member> memberVector) {
        this.context = context;
        this.id = id;
        this.name = name;
        this.creatorUsername = creatorUsername;
        this.memberVector = memberVector;
    }

    @Override
    public void onClick(View v)
    {
        ((MainController) context.getApplicationContext()).getGroupController().createGroup(id,name,creatorUsername,memberVector);
    }
}
