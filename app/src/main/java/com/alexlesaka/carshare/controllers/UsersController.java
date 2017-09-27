package com.alexlesaka.carshare.controllers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by aabuin on 22/08/2017.
 */

public class UsersController
{
    private FirebaseDatabase database;
    private DatabaseReference allUserRef;
    private DatabaseReference allFriendshipRequestRef;

    public UsersController()
    {
        database = FirebaseDatabase.getInstance();
        allUserRef = database.getReference().child("user");
        allFriendshipRequestRef = database.getReference().child("friendshiprequest");
    }

    public DatabaseReference getAllUserRef(){return allUserRef;}
    public DatabaseReference getUserName(String username){return allUserRef.child(username).child("name");}
    public DatabaseReference getUserImageRef(String username){return allUserRef.child(username).child("imageurl");}
    public DatabaseReference getUserFriendsRef(String username){return allUserRef.child(username).child("friends");}
    public DatabaseReference getUserFriendshipRequestRef(String username){return allFriendshipRequestRef.child(username);}
    public DatabaseReference getUserGroupRef(String username) {return allUserRef.child(username).child("groups");}

}
