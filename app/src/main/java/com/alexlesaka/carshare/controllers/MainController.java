package com.alexlesaka.carshare.controllers;

import android.app.Application;

/**
 * Created by Alex on 24/5/17.
 */

public class MainController extends Application
{
    private ImageController imageController =null;
    private LoginController loginController =null;
    private UsersController usersController =null;
    private GroupController groupController =null;



    @Override
    public void onCreate()
    {
        super.onCreate();
        loginController = new LoginController();
        usersController = new UsersController();
        imageController = new ImageController(this);
        groupController = new GroupController(this);
    }

    public ImageController getImageController(){return imageController;}
    public LoginController getLoginController(){return loginController;}
    public UsersController getUsersController(){return usersController;}
    public GroupController getGroupController(){return groupController;}
    
}