package com.alexlesaka.carshare.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.listeners.OnClickSelectMember;
import com.alexlesaka.carshare.models.Member;
import com.alexlesaka.carshare.myviews.CircularNetworkImageView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aabuin on 21/08/2017.
 */

public class SelectMemberFirebaseAdapter extends FirebaseRecyclerAdapter<Member, SelectMemberFirebaseAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private Activity activity;
    private MainController mainController;
    private String eventId;
    private View.OnClickListener onclick;



    public SelectMemberFirebaseAdapter( Activity activity, DatabaseReference memberRef, String eventId) {

        super(Member.class, R.layout.item_member, SelectMemberFirebaseAdapter.ViewHolder.class, memberRef);
        this.activity = activity;
        this.context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainController = (MainController) context.getApplicationContext();
        this.eventId=eventId;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public CircularNetworkImageView userImage;
        public TextView username;
        public TextView name;
        public TextView role;


        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.member_card);
            userImage = (CircularNetworkImageView) itemView.findViewById(R.id.user_image);
            username = (TextView) itemView.findViewById(R.id.user_username);
            name = (TextView) itemView.findViewById(R.id.user_name);
            role = (TextView) itemView.findViewById(R.id.member_role);
            role.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_member, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void populateViewHolder(final ViewHolder holder, Member su, int position)
    {

        holder.card.setOnClickListener(new OnClickSelectMember(activity ,su.getUsername(),eventId));
        holder.username.setText("@" +su.getUsername());

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
}
