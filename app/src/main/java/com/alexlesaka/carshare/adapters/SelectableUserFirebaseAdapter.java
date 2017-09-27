package com.alexlesaka.carshare.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.models.User;
import com.alexlesaka.carshare.myviews.CircularNetworkImageView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

/**
 * Created by aabuin on 21/08/2017.
 */

public class SelectableUserFirebaseAdapter extends FirebaseRecyclerAdapter<User, SelectableUserFirebaseAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private MainController mainController;
    private Vector<String> selectedUsernames;
    private Vector<String> unabledUsernames;


    public SelectableUserFirebaseAdapter(Context context, Query usersRef, Vector<String> selectedUsernames, Vector<String> unabledUsernames) {

        super(User.class, R.layout.item_selectable_user, SelectableUserFirebaseAdapter.ViewHolder.class, usersRef);
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainController = (MainController) context.getApplicationContext();

        if (selectedUsernames == null) this.selectedUsernames = new Vector<String>();
        else this.selectedUsernames = selectedUsernames;

        if (unabledUsernames == null) this.unabledUsernames = new Vector<String>();
        else this.unabledUsernames = unabledUsernames;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public CircularNetworkImageView userImage;
        public TextView username;
        public TextView name;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.user_card);
            userImage = (CircularNetworkImageView) itemView.findViewById(R.id.user_image);
            username = (TextView) itemView.findViewById(R.id.user_username);
            name = (TextView) itemView.findViewById(R.id.user_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_selectable_user, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeClick(v);
            }
        });
        return new ViewHolder(v);
    }

    @Override
    public void populateViewHolder(final ViewHolder holder, User su, int position) {

        holder.username.setText("@" + su.getUsername());
        mainController.getUsersController().getUserName(su.getUsername()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
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

        updateCardViewColor(holder.cardView, su.getUsername());

    }


    public void updateCardViewColor(CardView card, String username) {
        if(unabledUsernames.contains(username))
            card.setCardBackgroundColor(mainController.getResources().getColor(R.color.unabledBackgroud));
        else if (selectedUsernames.contains(username))
            card.setCardBackgroundColor(mainController.getResources().getColor(R.color.colorAccent));
        else
            card.setCardBackgroundColor(Color.WHITE);
    }


    public void computeClick(View v) {
        String username = ((TextView) v.findViewById(R.id.user_username)).getText().toString().substring(1);
        if (!unabledUsernames.contains(username)) {
            if (selectedUsernames.contains(username)) {
                selectedUsernames.remove(username);

            } else {
                selectedUsernames.addElement(username);
            }
        }
        updateCardViewColor((CardView) v.findViewById(R.id.user_card), username);
    }

    public Vector<String> getUsernames() {
        return selectedUsernames;
    }

}
