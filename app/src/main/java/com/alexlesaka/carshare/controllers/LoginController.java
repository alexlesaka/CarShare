package com.alexlesaka.carshare.controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * Created by aabuin on 17/08/2017.
 */

public class LoginController {

    private FirebaseAuth auth;
    private String username="";
    private FirebaseDatabase database;
    private FriendshipController friendshipController;
    private DatabaseReference userPrivateRef;
    private DatabaseReference userPublicRef;

    public LoginController()
    {
        auth = FirebaseAuth.getInstance();
    }


    public void initialize()
    {
        database = FirebaseDatabase.getInstance();
        userPrivateRef = database.getReference().child("authentification").child(auth.getCurrentUser().getUid());
    }

    public String getUsername(){return username;}
    public DatabaseReference getUsernameRef()
    {
        return userPrivateRef.child("username");
    }

    public FirebaseUser getFirebaseCurrentUser() {
        return auth.getCurrentUser();
    }
    public FriendshipController getFriendshipController(){return friendshipController;}
    public DatabaseReference getNameRef()
    {
        return userPublicRef.child("name");
    }
    public DatabaseReference getImageRef() {
        return userPublicRef.child("imageurl");
    }
    public DatabaseReference getEmailRef() {
        return userPrivateRef.child("email");
    }
    public DatabaseReference getLoginMethodRef() {return userPrivateRef.child("loginmethod");}
    public DatabaseReference getRegistrationDateRef() {return userPrivateRef.child("registrationdate");}
    public DatabaseReference getFriendsRef(){return userPublicRef.child("friends");}
    public DatabaseReference getGroupsRef(){return userPublicRef.child("groups");}

    public void updateName(String name) {
        getNameRef().setValue(name);
    }
    public void updateEmail(String email) {
        getEmailRef().setValue(email);
    }

    public void doFirstLogin(Context context)
    {
        getEmailRef().setValue(auth.getCurrentUser().getEmail());
        getLoginMethodRef().setValue(auth.getCurrentUser().getProviders().get(0));
        getRegistrationDateRef().setValue(new Date().toString());
    }

    public void doLogin(Context context, String username)
    {
        this.username=username;
        userPublicRef = database.getReference().child("user").child(username);
        friendshipController = new FriendshipController(context,username);
    }

    public void createPublicUsername(String username, Context context)
    {
        userPublicRef = database.getReference().child("user").child(username);
        getNameRef().setValue(auth.getCurrentUser().getDisplayName());
        getImageRef().setValue(auth.getCurrentUser().getPhotoUrl().toString());
        userPublicRef.child("username").setValue(username);
        getUsernameRef().setValue(username);
        friendshipController = new FriendshipController(context,username);
    }

    public void doLogout(SharedPreferences pref)
    {
        pref.edit().remove("user_uid").commit();
        username="";
        userPublicRef=null;
        userPrivateRef=null;
        friendshipController=null;
        database=null;
    }

}
