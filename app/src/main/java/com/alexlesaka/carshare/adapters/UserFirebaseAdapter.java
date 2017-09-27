package com.alexlesaka.carshare.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.listeners.OnClickButtonAddFriend;
import com.alexlesaka.carshare.listeners.OnClickAnswerFriendshipRequestButton;
import com.alexlesaka.carshare.models.User;
import com.alexlesaka.carshare.myviews.CircularNetworkImageView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aabuin on 21/08/2017.
 */

public class UserFirebaseAdapter extends FirebaseRecyclerAdapter<User, UserFirebaseAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private MainController mainController;
    private String myUsername;

    public UserFirebaseAdapter(Context context, DatabaseReference userRef) {

        super(User.class, R.layout.item_complete_user, UserFirebaseAdapter.ViewHolder.class, userRef);
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainController = (MainController) context.getApplicationContext();
        myUsername = mainController.getLoginController().getUsername();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircularNetworkImageView userImage;
        public TextView username;
        public TextView name;
        public Button addFriendButton;
        public TextView friends_message;
        public TextView peticionEnviada;
        public LinearLayout requestLinearLayout;
        public Button acceptFriendship;
        public Button declineFriendship;


        public ViewHolder(View itemView)
        {
            super(itemView);
            userImage = (CircularNetworkImageView) itemView.findViewById(R.id.user_image);
            username = (TextView) itemView.findViewById(R.id.user_username);
            name = (TextView) itemView.findViewById(R.id.user_name);
            addFriendButton = (Button) itemView.findViewById(R.id.simpleuser_addFriendButton);
            friends_message = (TextView) itemView.findViewById(R.id.simpleuser_viewProfile);
            peticionEnviada = (TextView) itemView.findViewById(R.id.simpleuser_peticionEnviada);
            requestLinearLayout = (LinearLayout) itemView.findViewById(R.id.simpleuser_requestlinearlayout);
            acceptFriendship = (Button) itemView.findViewById(R.id.simpleuser_accept_button);
            declineFriendship = (Button) itemView.findViewById(R.id.simpleuser_decline_button);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_complete_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void populateViewHolder(final ViewHolder holder, User su, int position) {

        holder.username.setText("@" + su.getUsername());
        mainController.getUsersController().getUserName(su.getUsername()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    holder.name.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Imagen
        mainController.getUsersController().getUserImageRef(su.getUsername()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    holder.userImage.setImageUrl(dataSnapshot.getValue().toString(), mainController.getImageController().getUserPhotoLoader());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        if (su.getUsername().equals(myUsername)) {

            holder.addFriendButton.setVisibility(View.GONE);
            holder.friends_message.setVisibility(View.GONE);
            holder.peticionEnviada.setVisibility(View.GONE);
            holder.requestLinearLayout.setVisibility(View.GONE);

        } else {

            //Petición enviada por mi
            mainController.getUsersController().getUserFriendshipRequestRef(su.getUsername()).child(myUsername).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        holder.addFriendButton.setVisibility(View.GONE);
                        holder.peticionEnviada.setVisibility(View.VISIBLE);
                        holder.friends_message.setVisibility(View.GONE);
                        holder.requestLinearLayout.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });


            //Petición enviado por el amigo
            mainController.getUsersController().getUserFriendshipRequestRef(myUsername).child(su.getUsername()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {

                        holder.addFriendButton.setVisibility(View.GONE);
                        holder.peticionEnviada.setVisibility(View.GONE);
                        holder.friends_message.setVisibility(View.GONE);
                        holder.requestLinearLayout.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            //Es mi amigo
            mainController.getLoginController().getFriendsRef().child(su.getUsername()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        holder.addFriendButton.setVisibility(View.GONE);
                        holder.friends_message.setVisibility(View.VISIBLE);
                        holder.peticionEnviada.setVisibility(View.GONE);
                        holder.requestLinearLayout.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            holder.addFriendButton.setOnClickListener(new OnClickButtonAddFriend(mainController, su.getUsername()));
            holder.acceptFriendship.setOnClickListener(new OnClickAnswerFriendshipRequestButton(mainController,su.getUsername(),true));
            holder.declineFriendship.setOnClickListener(new OnClickAnswerFriendshipRequestButton(mainController,su.getUsername(),false));
        }

    }

}
