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
import com.alexlesaka.carshare.models.User;
import com.alexlesaka.carshare.myviews.CircularNetworkImageView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aabuin on 21/08/2017.
 */

public class UniqueSelectableUserFirebaseAdapter extends FirebaseRecyclerAdapter<User, UniqueSelectableUserFirebaseAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private MainController mainController;


    public UniqueSelectableUserFirebaseAdapter(Context context, Query usersRef) {

        super(User.class, R.layout.item_selectable_user, UniqueSelectableUserFirebaseAdapter.ViewHolder.class, usersRef);
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainController = (MainController) context.getApplicationContext();

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

    }



    public void computeClick(View v) {
        String username = ((TextView) v.findViewById(R.id.user_username)).getText().toString().substring(1);
    }


}
