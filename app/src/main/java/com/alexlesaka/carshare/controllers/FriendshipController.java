package com.alexlesaka.carshare.controllers;

import android.content.Context;

import com.alexlesaka.carshare.models.UserListItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by aabuin on 24/08/2017.
 */

public class FriendshipController
{
    private MainController mainController;
    private String username;
    private FirebaseDatabase database;
    private DatabaseReference userFriendshipRequestsRef;

    private final String FRIENDSHIP_REQUEST= "friendshiprequest";

    public FriendshipController(Context context, String username)
    {
        this.username = username;
        database = FirebaseDatabase.getInstance();
        userFriendshipRequestsRef = database.getReference().child(FRIENDSHIP_REQUEST).child(username);
        mainController = (MainController) context.getApplicationContext();
    }


    public DatabaseReference getFriendshipRequestsRef(){return userFriendshipRequestsRef;}

    public void acceptFriend(String friendsUsername)
    {
        mainController.getLoginController().getFriendsRef().child(friendsUsername).setValue(new UserListItem(friendsUsername));
        mainController.getUsersController().getUserFriendsRef(friendsUsername).child(username).setValue(new UserListItem(username));
        userFriendshipRequestsRef.child(friendsUsername).removeValue();
    }

    public void declineFriend(String friendsUsername)
    {
        userFriendshipRequestsRef.child(friendsUsername).removeValue();
    }


    public void createFriendshipRequest(String friendsUsername)
    {
        mainController.getUsersController().getUserFriendshipRequestRef(friendsUsername).child(username).setValue(new UserListItem(username));
    }

}
