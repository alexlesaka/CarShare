package com.alexlesaka.carshare.listeners;

import com.alexlesaka.carshare.controllers.MainController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aabuin on 26/09/2017.
 */

public class ValueEventChangeDoneUsername implements ValueEventListener {
    private MainController mainController;
    private String groupId;
    private String eventId;
    private String newUsername;

    public ValueEventChangeDoneUsername(MainController mainController,
                                        String groupId, String eventId, String newUsername) {
        this.mainController = mainController;
        this.groupId = groupId;
        this.eventId = eventId;
        this.newUsername=newUsername;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists())
        {
            if(dataSnapshot.getValue()!=newUsername)
            {
                dataSnapshot.getRef().setValue(newUsername);
                mainController.getGroupController().sumMemberScore(groupId,newUsername);
                if(dataSnapshot.getValue().toString()!="")
                    mainController.getGroupController().restMemberScore(groupId,dataSnapshot.getValue().toString());

            }
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
