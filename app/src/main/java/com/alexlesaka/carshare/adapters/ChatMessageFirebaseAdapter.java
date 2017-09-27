package com.alexlesaka.carshare.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.models.ChatMessage;
import com.alexlesaka.carshare.myviews.CircularNetworkImageView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

/**
 * Created by aabuin on 21/08/2017.
 */

public class ChatMessageFirebaseAdapter extends FirebaseRecyclerAdapter<ChatMessage, ChatMessageFirebaseAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private MainController mainController;
    private String myUsername;

    public ChatMessageFirebaseAdapter(Context context, DatabaseReference chatRef) {

        super(ChatMessage.class, R.layout.item_chat_message, ChatMessageFirebaseAdapter.ViewHolder.class, chatRef);
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainController = (MainController) context.getApplicationContext();
        myUsername = mainController.getLoginController().getUsername();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircularNetworkImageView userImage;
        public TextView name;
        public TextView date;
        public TextView message;


        public ViewHolder(View itemView) {
            super(itemView);
            userImage = (CircularNetworkImageView) itemView.findViewById(R.id.chat_message_image);
            name = (TextView) itemView.findViewById(R.id.chat_message_name);
            date = (TextView) itemView.findViewById(R.id.chat_message_date);
            message = (TextView) itemView.findViewById(R.id.chat_message_message);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_chat_message, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void populateViewHolder(final ViewHolder holder, ChatMessage cm, int position)
    {

        mainController.getUsersController().getUserName(cm.getUsername()).addValueEventListener(new ValueEventListener() {
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


        String dateString;
        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat("d MMM yy, H:m");
        dateString = formatter.format(cm.getDate());

        holder.date.setText(dateString);



        //Imagen
        mainController.getUsersController().getUserImageRef(cm.getUsername()).addValueEventListener(new ValueEventListener() {
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

        holder.message.setText(cm.getMessage());
    }

}
