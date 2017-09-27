package com.alexlesaka.carshare.activities.Group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.models.Member;
import com.alexlesaka.carshare.enumerations.UserRole;
import com.alexlesaka.carshare.adapters.SelectableUserFirebaseAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Vector;


public class AddMemberToGroupActivity extends AppCompatActivity {

    private MainController mainController;
    private Vector<String> unabledUsernames;
    private String groupId;

    private Button addMemberButton;

    //Friend List
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Bundle extras = getIntent().getExtras();
        groupId = extras.getString("groupid", null);
        if (extras.getString("groupid", null) == null) finish();

        mainController = (MainController) getApplication().getApplicationContext();
        unabledUsernames = new Vector<String>();

        addMemberButton = (Button) findViewById(R.id.add_member_button);
        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextOnClick();
            }
        });

        mainController.getGroupController().getGroupMembersRef(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                    while(iterator.hasNext())
                    {
                        Member m = iterator.next().getValue(Member.class);
                        unabledUsernames.addElement(m.getUsername());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Friends
        recyclerView = (RecyclerView) findViewById(R.id.add_member_friends_recycler_view);
        recyclerView.setAdapter(new SelectableUserFirebaseAdapter(getApplicationContext(), mainController.getLoginController().getFriendsRef(), null, unabledUsernames));
        layoutManager = new LinearLayoutManager(mainController);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void nextOnClick()
    {
        Vector<String> selection = ((SelectableUserFirebaseAdapter) recyclerView.getAdapter()).getUsernames();
        if (selection.size() > 0)
        {
            for(int i=0;i<selection.size();i++)
            {
                mainController.getGroupController().addMemberToGroup(groupId,new Member(selection.elementAt(i), UserRole.MEMBER,0));
            }
            finish();
        } else {
            Toast.makeText(mainController, R.string.toast_selectusers, Toast.LENGTH_SHORT).show();
        }
    }

}
