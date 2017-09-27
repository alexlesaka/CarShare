package com.alexlesaka.carshare.listeners;

import android.widget.TextView;

import com.alexlesaka.carshare.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aabuin on 18/08/2017.
 */

public class MyValueEventListener implements ValueEventListener {
    private TextView view;

    private String currentValue;
    private int updates;


    public MyValueEventListener(TextView view)
    {
        super();
        this.view = view;
        currentValue="";
        updates=0;

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        currentValue = (String) dataSnapshot.getValue();
        if(currentValue!=null) view.setText(currentValue.toString());
        else view.setText(R.string.label_error);
        updates++;
    }

    @Override
    public void onCancelled(DatabaseError databaseError)
    {
        view.setText(R.string.label_error);
    }

    public String getCurrentValue(){return currentValue;}

    public boolean isReady()
    {
        return updates>0;
    }

    public int getUpdates()
    {
        return updates;
    }

}

