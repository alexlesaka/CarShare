package com.alexlesaka.carshare.activities.Group;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.adapters.SelectableUserFirebaseAdapter;


public class CreateGroupUserListFragment extends Fragment {

    private CreateGroupActivity activity;

    private Button next;

    //Friend List
    private MainController mainController;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


   @Override
   public void onCreate(Bundle savedInstanceState)
   {
       super.onCreate(savedInstanceState);
   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        activity = (CreateGroupActivity) getActivity();
        activity.setCurrentFragment("members");
        View view = inflater.inflate(R.layout.fragment_create_group_user_list,container,false);
        next = (Button) view.findViewById(R.id.create_group_next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                activity.setUsernames(((SelectableUserFirebaseAdapter)recyclerView.getAdapter()).getUsernames());
                activity.nextOnClick();
            }
        });


        mainController = (MainController) getActivity().getApplicationContext();

        //Friends
        recyclerView = (RecyclerView) view.findViewById(R.id.friends_recycler_view);
        recyclerView.setAdapter(new SelectableUserFirebaseAdapter(mainController,mainController.getLoginController().getFriendsRef(),activity.getUsernames(),null));
        layoutManager = new LinearLayoutManager(mainController);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

}
