package com.alexlesaka.carshare.activities.Group;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.adapters.GroupTabAdapter;
import com.firebase.ui.auth.ResultCodes;

public class GroupActivity extends AppCompatActivity {

    public final int PLANUSER =4;
    public final int DONEUSER =5;


    private String groupId;
    private String currentUsername;

    private MainController mainController;


    private FragmentTabHost tabHost;
    private TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_tab);

        Bundle extras = getIntent().getExtras();
        groupId= extras.getString("groupid",null);
        if(extras.getString("groupid",null)==null) finish();

        mainController = (MainController) getApplication();
        currentUsername= mainController.getLoginController().getUsername();


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_graph_white));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_calendar_white));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_users_white));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_chat_white));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final GroupTabAdapter adapter = new GroupTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    public String getGroupId(){return groupId;}
    public MainController getMainController(){return mainController;}


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Se ha cambiado el plan de algún evento
        if (requestCode == PLANUSER && resultCode == ResultCodes.OK)
        {
            String eventId = data.getExtras().getString("eventid");
            String newUsername=data.getExtras().getString("username");
            data.removeExtra("eventid");
            data.getExtras().remove("username");

            mainController.getGroupController().updateEventPlanned(groupId,eventId,newUsername);

        }

        //Se ha cambiado el done de algún evento
        if (requestCode == DONEUSER && resultCode == ResultCodes.OK)
        {
            String eventId = data.getExtras().getString("eventid");
            String newUsername=data.getExtras().getString("username");
            data.removeExtra("eventid");
            data.getExtras().remove("username");

            mainController.getGroupController().updateEventDone(groupId,eventId,newUsername);
        }
    }


}