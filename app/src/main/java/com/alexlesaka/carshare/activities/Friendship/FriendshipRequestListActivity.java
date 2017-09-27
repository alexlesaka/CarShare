package com.alexlesaka.carshare.activities.Friendship;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alexlesaka.carshare.adapters.FriendshipRequestFirebaseAdapter;
import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;


public class FriendshipRequestListActivity extends AppCompatActivity {

    private MainController mainController;
    //Friend List
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendship_request_list);

        mainController = (MainController) getApplicationContext();

        //Friends
        recyclerView = (RecyclerView) findViewById(R.id.friendship_requests_recycler_view);
        recyclerView.setAdapter(new FriendshipRequestFirebaseAdapter(getApplicationContext(),
                mainController.getLoginController().getFriendshipController().getFriendshipRequestsRef()));
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
    }
}
