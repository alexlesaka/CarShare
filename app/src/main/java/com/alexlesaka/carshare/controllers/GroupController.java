package com.alexlesaka.carshare.controllers;

import android.content.Context;

import com.alexlesaka.carshare.listeners.ValueEventChangeDoneUsername;
import com.alexlesaka.carshare.listeners.ValueEventChangePlannedUsername;
import com.alexlesaka.carshare.models.ChatMessage;
import com.alexlesaka.carshare.models.GroupListItem;
import com.alexlesaka.carshare.models.Member;
import com.alexlesaka.carshare.models.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Created by aabuin on 28/08/2017.
 */

public class GroupController
{
    private FirebaseDatabase database;
    private DatabaseReference allGroupRef;
    private Context context;
    private final String GROUP = "group";
    private final String EVENTS = "events";

    public GroupController(Context context)
    {
        database = FirebaseDatabase.getInstance();
        allGroupRef = database.getReference().child(GROUP);
        this.context=context;
    }

    public void createGroup(String id, String name, String creatorUsername, Vector<Member> members)
    {
        allGroupRef.child(id).child("id").setValue(id);
        allGroupRef.child(id).child("name").setValue(name);
        allGroupRef.child(id).child("creator").setValue(creatorUsername);
        allGroupRef.child(id).child("date").setValue(new Date().toString());
        for(int i=0;i<members.size();i++) addMemberToGroup(id,members.elementAt(i));
    }

    public void addMemberToGroup(String id, Member m)
    {
        allGroupRef.child(id).child("members").child(m.getUsername()).setValue(m);
        ((MainController)context.getApplicationContext()).getUsersController().getUserGroupRef(m.getUsername()).child(id).setValue(new GroupListItem(id));
    }

    public DatabaseReference getGroupNameRef(String groupId){return allGroupRef.child(groupId).child("name");}
    public DatabaseReference getGroupMembersRef(String groupId){return allGroupRef.child(groupId).child("members");}
    public DatabaseReference getGroupMemberScoreRef(String groupId, String username){return getGroupMembersRef(groupId).child(username).child("score");}

    public DatabaseReference getGroupCreatorRef(String groupId){return allGroupRef.child(groupId).child("creator");}
    public DatabaseReference getGroupDateRef(String groupId){return allGroupRef.child(groupId).child("date");}
    public DatabaseReference getGroupChatMessagesRef(String groupId){return allGroupRef.child(groupId).child("messages");}

    public void sendMessageToGroup(String username,String id, String message)
    {
        Date date = new Date();
        String stringDate = String.valueOf(date.getTime()/1000);
        getGroupChatMessagesRef(id).child(stringDate).setValue(new ChatMessage(username,message,date));
    }


    //Events
    public DatabaseReference getAllEventsRef(String groupId){return allGroupRef.child(groupId).child(EVENTS);}
    public DatabaseReference getEventRef(String groupId, String eventId){return getAllEventsRef(groupId).child(eventId);}
    public DatabaseReference getEventPlannedUser(String groupId, String eventId){return getEventRef(groupId,eventId).child("usernamePlan");}
    public DatabaseReference getEventDoneUser(String groupId, String eventId){return getEventRef(groupId,eventId).child("usernameDone");}

    public void createEvent(String groupId, String name, String from, String to, Date date, String hour, String planed, String done)
    {

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String eventId = df.format(date) + (new Date()).getTime();
        if(done!="") sumMemberScore(groupId,done);
        Event ne = new Event(eventId,name,from,to,date, hour, planed, done);
        getEventRef(groupId,ne.getId()).setValue(ne);
    }


    public void updateEventPlanned(String groupid, String eventid, String newusername)
    {
        getEventPlannedUser(groupid,eventid).addListenerForSingleValueEvent(
                new ValueEventChangePlannedUsername((MainController)context,groupid,eventid,newusername));
    }

    public void updateEventDone(String groupid, String eventid, String newusername)
    {
        getEventDoneUser(groupid,eventid).addListenerForSingleValueEvent(
                new ValueEventChangeDoneUsername((MainController)context,groupid,eventid,newusername));
    }

    public void sumMemberScore(String groupId, String username)
    {
        getGroupMemberScoreRef(groupId, username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                        Integer score =  Integer.parseInt(dataSnapshot.getValue().toString());
                        score--;
                        dataSnapshot.getRef().setValue(score);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void restMemberScore(String groupId, String username)
    {
        getGroupMemberScoreRef(groupId, username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                    Integer score =  Integer.parseInt(dataSnapshot.getValue().toString());
                    score++;
                    dataSnapshot.getRef().setValue(score);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }






}
