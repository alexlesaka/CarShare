package com.alexlesaka.carshare.activities.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.adapters.UserFirebaseAdapter;
import com.alexlesaka.carshare.controllers.MainController;

public class AllUsersActivity extends AppCompatActivity {

    private MainController mainController;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        mainController = (MainController) getApplication();
        recyclerView = (RecyclerView) findViewById(R.id.all_users_recycler_view);
        recyclerView.setAdapter(new UserFirebaseAdapter(getApplicationContext(),mainController.getUsersController().getAllUserRef()));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
