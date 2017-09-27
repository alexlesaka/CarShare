package com.alexlesaka.carshare.activities.Group;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.firebase.ui.auth.ResultCodes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateEventActivity extends AppCompatActivity {

    private String groupId;
    private MainController mainController;
    private final int PLANUSER =1;
    private final int DONEUSER =2;
    private String selectedPlanUser="";
    private String selectedDoneUser="";


    //Graphics
    private EditText name;
    private EditText origin;
    private EditText destination;
    private EditText fromDate;
    private EditText hour;
    private TextView selectedPlanUserTextView;
    private Button selectPlanUserButton;
    private Button removePlanUserButton;
    private TextView selectedDoneUserTextView;
    private Button selectDoneUserButton;
    private Button removeDoneUserButton;

    /*private EditText toDate;
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;
    private CheckBox sunday;*/


    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Bundle extras = getIntent().getExtras();
        groupId = extras.getString("groupid", null);
        if (extras.getString("groupid", null) == null) finish();

        mainController = (MainController) getApplicationContext();

        //graphics
        name = (EditText) findViewById(R.id.create_event_name);
        origin = (EditText) findViewById(R.id.create_event_origin);
        destination = (EditText) findViewById(R.id.create_event_destination);
        fromDate = (EditText) findViewById(R.id.create_event_fromdate);
        hour = (EditText) findViewById(R.id.create_event_hour);
       /*toDate = (EditText) findViewById(R.id.create_event_todate);
        monday = (CheckBox) findViewById(R.id.create_event_monday);
        tuesday = (CheckBox) findViewById(R.id.create_event_tuesday);
        wednesday = (CheckBox) findViewById(R.id.create_event_wednesday);
        thursday = (CheckBox) findViewById(R.id.create_event_thursday);
        friday = (CheckBox) findViewById(R.id.create_event_friday);
        saturday = (CheckBox) findViewById(R.id.create_event_saturday);
        sunday = (CheckBox) findViewById(R.id.create_event_sunday);*/

        selectedPlanUserTextView = (TextView) findViewById(R.id.create_event_selectedplanuser);
        removePlanUserButton = (Button) findViewById(R.id.create_event_removeplanuserbutton);
        selectPlanUserButton = (Button) findViewById(R.id.create_event_selectplanuserbutton);
        selectPlanUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPlanUser();
            }
        });

        removePlanUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePlanUser();
            }
        });

        selectedDoneUserTextView = (TextView) findViewById(R.id.create_event_selecteddoneuser);
        removeDoneUserButton = (Button) findViewById(R.id.create_event_removedoneuserbutton);
        selectDoneUserButton = (Button) findViewById(R.id.create_event_selectdoneuserbutton);
        selectDoneUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDoneUser();
            }
        });

        removeDoneUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDoneUser();
            }
        });


        create = (Button) findViewById(R.id.create_event_createbutton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewEvent();
            }
        });


    }

    public void createNewEvent() {

        if(name.getText().toString().length()==0)
        {
            Toast.makeText(mainController, R.string.toast_emptyname, Toast.LENGTH_SHORT).show();
            return;
        }

        if(origin.getText().toString().length()==0)
        {
            Toast.makeText(mainController, R.string.toast_emptyorigin, Toast.LENGTH_SHORT).show();
            return;
        }

        if(destination.getText().toString().length()==0)
        {
            Toast.makeText(mainController, R.string.toast_emptydestination, Toast.LENGTH_SHORT).show();
            return;
        }

        Date fromDateDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            fromDateDate = sdf.parse(fromDate.getText().toString());

        } catch (Exception e) {
            Toast.makeText(mainController, R.string.toast_wrongdate, Toast.LENGTH_SHORT).show();
            return;
        }


        if(hour.getText().length()==0)
        {
            Toast.makeText(mainController, R.string.toast_wronghour, Toast.LENGTH_SHORT).show();
            return;
        }

        mainController.getGroupController().createEvent(groupId, name.getText().toString(),origin.getText().toString(),destination.getText().toString(),fromDateDate,hour.getText().toString(),selectedPlanUser,selectedDoneUser);
        finish();
    }

    public void selectPlanUser()
    {
        Intent i = new Intent(getApplicationContext(), SelectMemberActivity.class);
        i.putExtra("groupid",groupId);
        startActivityForResult(i, PLANUSER);
    }
    public void removePlanUser()
    {
        selectedPlanUser="";
        selectedPlanUserTextView.setText(R.string.label_notselected);
    }

    public void selectDoneUser()
    {
        Intent i = new Intent(getApplicationContext(), SelectMemberActivity.class);
        i.putExtra("groupid",groupId);
        startActivityForResult(i, DONEUSER);
    }
    public void removeDoneUser()
    {
        selectedDoneUser="";
        selectedDoneUserTextView.setText(R.string.label_notselected);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLANUSER && resultCode == ResultCodes.OK)
        {
            selectedPlanUser=data.getExtras().getString("username");
            data.getExtras().remove("username");
            selectedPlanUserTextView.setText(selectedPlanUser);
        }
        if (requestCode == DONEUSER && resultCode == ResultCodes.OK)
        {
            selectedDoneUser=data.getExtras().getString("username");
            data.getExtras().remove("username");
            selectedDoneUserTextView.setText(selectedDoneUser);
        }
    }

}
