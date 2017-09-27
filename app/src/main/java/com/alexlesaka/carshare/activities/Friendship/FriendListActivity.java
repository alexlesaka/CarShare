package com.alexlesaka.carshare.activities.Friendship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.adapters.UserFirebaseAdapter;


public class FriendListActivity extends AppCompatActivity {

    private MainController mainController;
    //Friend List
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        mainController = (MainController) getApplicationContext();

        //Friends
        recyclerView = (RecyclerView) findViewById(R.id.friends_recycler_view);
        recyclerView.setAdapter(new UserFirebaseAdapter(getApplicationContext(),mainController.getLoginController().getFriendsRef()));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
