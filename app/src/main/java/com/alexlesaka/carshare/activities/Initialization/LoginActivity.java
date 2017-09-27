package com.alexlesaka.carshare.activities.Initialization;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

/**
 * Created by Alex on 24/5/17.
 */

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    private static final int USERNAME_CODE = 2;
    private SharedPreferences pref;
    private MainController mainController;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mainController = (MainController) getApplicationContext();
        pref = getSharedPreferences("pref", MODE_PRIVATE);

        doLogin();
    }

    //Cuando vuelve de la pantalla de login de Firebase
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode == ResultCodes.OK) doLogin();
        else if (requestCode == USERNAME_CODE) {
            if (resultCode == RESULT_OK) {
                String username = data.getExtras().getString("username");
                mainController.getLoginController().createPublicUsername(username, getApplicationContext());
            } else {
                AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        mainController.getLoginController().doLogout(pref);
                    }
                });
                onRestart();
            }
        }
    }

    //Comprobación del Login
    private void doLogin() {
        currentUser = mainController.getLoginController().getFirebaseCurrentUser();
        if (currentUser == null) //NO AUTH(1)
        {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder().setProviders(Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                    .setIsSmartLockEnabled(false) //recuerda los usuarios que han accedido anteriormente
                    .setTheme(R.style.AppTheme)
                    .build(), RC_SIGN_IN);
        } else // AUTH(1)
        {
            mainController.getLoginController().initialize();

            //Comprobación si es un usuario nuevo o es un usuario "viejo"
            mainController.getLoginController().getUsernameRef().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) //AUTH(1) + USERNAME(2)
                    {
                        mainController.getLoginController().doLogin(getApplicationContext(), dataSnapshot.getValue().toString());

                        pref.edit().putString("user_uid", currentUser.getUid()).commit();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else //AUTH(1) + NO USERNAME(2)
                    {
                        mainController.getLoginController().doFirstLogin(getApplicationContext());
                        Intent i = new Intent(getApplicationContext(), UsernameActivity.class);
                        startActivityForResult(i, USERNAME_CODE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}