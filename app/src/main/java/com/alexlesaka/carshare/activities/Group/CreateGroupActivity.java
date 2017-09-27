package com.alexlesaka.carshare.activities.Group;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.models.Member;
import com.alexlesaka.carshare.enumerations.UserRole;

import java.util.Date;
import java.util.Vector;

public class CreateGroupActivity extends FragmentActivity {

    private ProgressBar progress;
    private String id = "";
    private String name = "";
    public Vector<String> usernames;
    private FragmentTransaction transaction;

    private String currentFragment;
    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        progress = (ProgressBar) findViewById(R.id.create_group_progress);

        CreateGroupUserListFragment fragment01 = new CreateGroupUserListFragment();
        getFragmentManager().beginTransaction().add(R.id.create_group_fragment_container, fragment01).commit();

        mainController = (MainController) getApplication().getApplicationContext();
        usernames = new Vector<String>();
    }


    public void nextOnClick()
    {
        if (usernames.size() > 0) {
            CreateGroupNameFragment fragment02 = new CreateGroupNameFragment();
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.create_group_fragment_container, fragment02);
            transaction.addToBackStack(null);
            transaction.commit();
            progress.setProgress(50);
        } else {
            Toast.makeText(mainController, R.string.toast_selectusers, Toast.LENGTH_SHORT).show();
        }
    }

    public void createOnClick() {
        if(name.length()>0){
        progress.setProgress(100);
        Vector<Member> members = new Vector<Member>();
        for (int i = 0; i < usernames.size(); i++) {
            members.addElement(new Member(usernames.elementAt(i), UserRole.MEMBER,0));
        }
        members.addElement(new Member(mainController.getLoginController().getUsername(), UserRole.ADMIN,0));
        mainController.getGroupController().createGroup(id, name, mainController.getLoginController().getUsername(), members);
        finish();}
        else
            Toast.makeText(mainController, R.string.toast_emptyname, Toast.LENGTH_SHORT).show();

    }

    public void setCurrentFragment(String cf) {
        currentFragment = cf;
    }

    public void createId() {
        String username = ((MainController) getApplication().getApplicationContext()).getLoginController().getUsername();
        String date = new Date().toString();
        String id = "" + (username + date).hashCode();
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(Vector<String> usernames) {
        this.usernames = usernames;
    }
}
