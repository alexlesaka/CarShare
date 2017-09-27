package com.alexlesaka.carshare.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.listeners.OnClickChangeDoneUser;
import com.alexlesaka.carshare.listeners.OnClickChangePlannedUser;
import com.alexlesaka.carshare.models.Event;
import com.alexlesaka.carshare.activities.Group.GroupActivity;
import com.alexlesaka.carshare.myviews.CircularNetworkImageView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by aabuin on 21/08/2017.
 */

public class EventFirebaseAdapter extends FirebaseRecyclerAdapter<Event, EventFirebaseAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private MainController mainController;
    private String groupId;
    private GroupActivity activity;

    public EventFirebaseAdapter(GroupActivity activity, DatabaseReference eventRef, String groupId) {

        super(Event.class, R.layout.item_event_simple, EventFirebaseAdapter.ViewHolder.class, eventRef);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainController = (MainController) activity.getApplicationContext();
        this.activity=activity;
        this.groupId = groupId;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardview;
        public TextView name;
        public TextView origin;
        public TextView destination;
        public TextView date;
        public TextView hour;
        public TextView planUserName;
        public CircularNetworkImageView planImage;
        public TextView doneUserName;
        public CircularNetworkImageView doneImage;
        public LinearLayout planClickable;
        public LinearLayout doneClickable;


        public ViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.event_card);
            name = (TextView) itemView.findViewById(R.id.event_name);
            origin = (TextView) itemView.findViewById(R.id.event_origin);
            destination = (TextView) itemView.findViewById(R.id.event_destination);

            date = (TextView) itemView.findViewById(R.id.event_date);
            hour = (TextView) itemView.findViewById(R.id.event_hour);

            planUserName = (TextView) itemView.findViewById(R.id.event_planuser_name);
            planImage = (CircularNetworkImageView) itemView.findViewById(R.id.event_planuser_image);

            doneUserName = (TextView) itemView.findViewById(R.id.event_doneuser_name);
            doneImage = (CircularNetworkImageView) itemView.findViewById(R.id.event_doneuser_image);

            planClickable = (LinearLayout) itemView.findViewById(R.id.event_plan_clickable);
            doneClickable = (LinearLayout) itemView.findViewById(R.id.event_done_clickable);


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_event_simple, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void populateViewHolder(final ViewHolder holder, Event su, int position) {

        holder.name.setText(su.getName());
        holder.origin.setText(su.getFrom().toString());
        holder.destination.setText(su.getTo().toString());


        //FECHA
        SimpleDateFormat df = new SimpleDateFormat("EE, yyyy/MM/dd");
        String stringDate = df.format(su.getDate());

        Calendar c = Calendar.getInstance();
        c.setTime(su.getDate());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek--;
        if (dayOfWeek == 0) dayOfWeek = 7;


        holder.date.setText(stringDate);
        holder.hour.setText(su.getHour());

        if (su.getUsernamePlan() != "") {
            mainController.getUsersController().getUserName(su.getUsernamePlan()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        holder.planUserName.setText(dataSnapshot.getValue().toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mainController.getUsersController().getUserImageRef(su.getUsernamePlan()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        holder.planImage.setImageUrl(dataSnapshot.getValue().toString(),
                                mainController.getImageController().getUserPhotoLoader());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        if (su.getUsernameDone() != "") {
            mainController.getUsersController().getUserName(su.getUsernameDone()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        holder.doneUserName.setText(dataSnapshot.getValue().toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mainController.getUsersController().getUserImageRef(su.getUsernameDone()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        holder.doneImage.setImageUrl(dataSnapshot.getValue().toString(),
                                mainController.getImageController().getUserPhotoLoader());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        holder.planClickable.setOnClickListener(
                new OnClickChangePlannedUser(activity, groupId, su.getId(),su.getUsernamePlan()));

        holder.doneClickable.setOnClickListener(
                new OnClickChangeDoneUser(activity, groupId, su.getId(),su.getUsernameDone()));
    }
}
