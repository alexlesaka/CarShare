package com.alexlesaka.carshare.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.listeners.OnClickAnswerFriendshipRequestButton;
import com.alexlesaka.carshare.models.UserListItem;
import com.alexlesaka.carshare.myviews.CircularNetworkImageView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aabuin on 21/08/2017.
 */

public class FriendshipRequestFirebaseAdapter extends FirebaseRecyclerAdapter<UserListItem, FriendshipRequestFirebaseAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;

    public FriendshipRequestFirebaseAdapter(Context context, DatabaseReference requestsRef) {

        super(UserListItem.class, R.layout.item_complete_user, FriendshipRequestFirebaseAdapter.ViewHolder.class, requestsRef);
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        //AÑADIR BOTONES
        public CircularNetworkImageView userImage;
        public TextView username;
        public TextView name;
        public Button accept;
        public Button decline;


        public ViewHolder(View itemView)
        {
            super(itemView);
            userImage = (CircularNetworkImageView) itemView.findViewById(R.id.item_friendshiprequest_userimage);
            username = (TextView) itemView.findViewById(R.id.item_friendshiprequest_username);
            name = (TextView) itemView.findViewById(R.id.item_friendshiprequest_name);
            accept = (Button) itemView.findViewById(R.id.item_friendshiprequest_accept_button);
            decline = (Button) itemView.findViewById(R.id.item_friendshiprequest_decline_button);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = inflater.inflate(R.layout.item_friendship_request, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void populateViewHolder(final ViewHolder holder, UserListItem uli, int position)
    {
        MainController a = (MainController) context.getApplicationContext();

        a.getUsersController().getUserImageRef(uli.getUsername()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    holder.userImage.setImageUrl(dataSnapshot.getValue().toString(),
                            ((MainController) context.getApplicationContext()).getImageController().getUserPhotoLoader());
                }
                else
                {
                    holder.userImage.setImageResource(R.drawable.icon_users_accent);
                    holder.username.setText(R.string.label_removedusername);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        a.getUsersController().getUserName(uli.getUsername()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())holder.name.setText(dataSnapshot.getValue().toString());
                else holder.name.setText(R.string.label_removedusername);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        holder.username.setText("@" + uli.getUsername());
        holder.accept.setOnClickListener(new OnClickAnswerFriendshipRequestButton(context,uli.getUsername(),true));
        holder.decline.setOnClickListener(new OnClickAnswerFriendshipRequestButton(context,uli.getUsername(),false));
    }
}
