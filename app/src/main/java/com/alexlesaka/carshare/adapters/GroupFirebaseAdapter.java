package com.alexlesaka.carshare.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.listeners.OnClickOpenGroupActivity;
import com.alexlesaka.carshare.models.GroupListItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aabuin on 21/08/2017.
 */

public class GroupFirebaseAdapter extends FirebaseRecyclerAdapter<GroupListItem, GroupFirebaseAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private MainController mainController;
    private String myUsername;


    public GroupFirebaseAdapter(Context context, DatabaseReference groupRef) {

        super(GroupListItem.class, R.layout.item_simple_group, GroupFirebaseAdapter.ViewHolder.class, groupRef);
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainController = (MainController) context.getApplicationContext();
        myUsername = mainController.getLoginController().getUsername();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView username;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.group_card);
            username = (TextView) itemView.findViewById(R.id.group_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_simple_group, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void populateViewHolder(final ViewHolder holder, GroupListItem su, int position) {

        //holder.username.setText("@" + su.getUsernames());
        mainController.getGroupController().getGroupNameRef(su.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())holder.username.setText(dataSnapshot.getValue().toString());
                else holder.username.setText(R.string.label_error);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.cardView.setOnClickListener(new OnClickOpenGroupActivity(mainController,su.getId()));


    }

}
