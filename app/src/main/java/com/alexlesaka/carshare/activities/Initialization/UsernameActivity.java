package com.alexlesaka.carshare.activities.Initialization;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.myviews.LoadingEditText;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class UsernameActivity extends AppCompatActivity {

    private MainController mainController;


    private LoadingEditText usernameInput2;
    private String posibleUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);


        mainController = (MainController) getApplicationContext();


        View.OnClickListener confirmListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmUsername();
            }
        };
        View.OnClickListener checkListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUsername(v);
            }
        };


        usernameInput2 = (LoadingEditText) findViewById(R.id.my_loading_username);
        usernameInput2.setCreateButtonOnClick(confirmListener);
        usernameInput2.setCheckButtonOnClick(checkListener);


    }

    public void checkUsername(View v) {
        usernameInput2.setThinking();

        posibleUsername = usernameInput2.getEditText().getText().toString();
        if (posibleUsername.length() > 0) {
            try {
                DatabaseReference ref = mainController.getUsersController().getAllUserRef();
                ref.child(posibleUsername).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            usernameInput2.setNo();
                        } else {
                            usernameInput2.setYes();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            } catch (Exception e) {
                usernameInput2.setNo();
            }
        }
        else
        {
            usernameInput2.setNo();
        }
    }


    public void confirmUsername() {
        if (posibleUsername.length() > 0) {
            Intent i = new Intent();
            i.putExtra("username", posibleUsername);
            setResult(RESULT_OK, i);
            finish();
        }
    }


    @Override
    public void onBackPressed()
    {
        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                        mainController.getLoginController().doLogout(pref);
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                });
    }

}
