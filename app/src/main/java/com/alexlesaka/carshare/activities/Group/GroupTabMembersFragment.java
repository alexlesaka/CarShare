package com.alexlesaka.carshare.activities.Group;

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
import com.alexlesaka.carshare.listeners.OnClickOpenAddMemberActivity;
import com.alexlesaka.carshare.adapters.MemberFirebaseAdapter;


public class GroupTabMembersFragment extends Fragment {

    private MainController mainController;
    private String groupId;
    private FloatingActionButton fab;

    //Member List
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_group_tab_members, container, false);

        mainController = ((GroupActivity)getActivity()).getMainController();
        groupId = ((GroupActivity)getActivity()).getGroupId();

        fab = (FloatingActionButton) view.findViewById(R.id.addMemberFloatingButton);
        fab.setOnClickListener(new OnClickOpenAddMemberActivity(getContext(),groupId));

        //Friends
        recyclerView = (RecyclerView) view.findViewById(R.id.group_member_recycler_view);
        recyclerView.setAdapter(new MemberFirebaseAdapter(getActivity().getApplicationContext(),mainController.getGroupController().getGroupMembersRef(groupId)));
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }





}
