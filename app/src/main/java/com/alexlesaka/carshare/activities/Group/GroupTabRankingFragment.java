package com.alexlesaka.carshare.activities.Group;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.adapters.MemberRankingFirebaseAdapter;


public class GroupTabRankingFragment extends Fragment {

    private MainController mainController;
    private String groupId;

    //Member List
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_group_tab_ranking, container, false);

        mainController = ((GroupActivity)getActivity()).getMainController();
        groupId = ((GroupActivity)getActivity()).getGroupId();


        //Friends
        recyclerView = (RecyclerView) view.findViewById(R.id.group_memberranking_recycler_view);
        recyclerView.setAdapter(new MemberRankingFirebaseAdapter(getActivity().getApplicationContext(),mainController.getGroupController().getGroupMembersRef(groupId).orderByChild("score")));
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }
}
