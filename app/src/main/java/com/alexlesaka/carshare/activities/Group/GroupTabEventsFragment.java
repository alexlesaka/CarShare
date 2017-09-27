package com.alexlesaka.carshare.activities.Group;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.adapters.EventFirebaseAdapter;


public class GroupTabEventsFragment extends Fragment {

    private MainController mainController;
    private String groupId;
    private FloatingActionButton fab;


    //NewEvents List
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_group_tab_newevents, container, false);

        mainController = ((GroupActivity)getActivity()).getMainController();
        groupId = ((GroupActivity)getActivity()).getGroupId();

        fab = (FloatingActionButton) view.findViewById(R.id.addNewEventFloatingButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewEvent();
            }
        });

        //Friends
        recyclerView = (RecyclerView) view.findViewById(R.id.group_newevents_recycler_view);
        recyclerView.setAdapter(
                new EventFirebaseAdapter((GroupActivity)this.getActivity(),mainController.getGroupController().getAllEventsRef(groupId),groupId));
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    public void createNewEvent() {
        Intent i = new Intent(mainController, CreateEventActivity.class);
        i.putExtra("groupid", groupId);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainController.getApplicationContext().startActivity(i);
    }

}
