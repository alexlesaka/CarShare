package com.alexlesaka.carshare.activities.Group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.adapters.SelectMemberFirebaseAdapter;


public class SelectMemberActivity extends AppCompatActivity {

    private MainController mainController;
    private String groupId;
    private String eventId;

    //Member List
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_member);

        mainController = (MainController) getApplicationContext();

        Bundle extras = getIntent().getExtras();
        groupId= extras.getString("groupid",null);
        if(extras.getString("groupid",null)==null) finish();

        eventId = extras.getString("eventid","");


        //Member
        recyclerView = (RecyclerView) findViewById(R.id.select_member_recycler_view);
        recyclerView.setAdapter(new SelectMemberFirebaseAdapter(this,mainController.getGroupController().getGroupMembersRef(groupId),eventId));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }





}
