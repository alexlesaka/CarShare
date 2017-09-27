package com.alexlesaka.carshare.activities.Group;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alexlesaka.carshare.R;


public class CreateGroupNameFragment extends Fragment {

    private CreateGroupActivity activity;
    private EditText groupName;
    private Button createButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        activity = (CreateGroupActivity) getActivity();
        activity.setCurrentFragment("name");
        View view=inflater.inflate(R.layout.fragment_create_group_name, container, false);

        groupName =(EditText) view.findViewById(R.id.create_group_name_edittext);
        createButton =(Button) view.findViewById(R.id.create_group_create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIdAndName();
                activity.createOnClick();
            }
        });
        return view;
    }

    public void setIdAndName()
    {
        if(groupName.getText().length()>0)
        {
            activity.createId();
            activity.setName(groupName.getText().toString());
            activity.nextOnClick();
        }
        else
        {
            Toast.makeText(activity, R.string.toast_emptyname, Toast.LENGTH_SHORT).show();
        }
    }

}
