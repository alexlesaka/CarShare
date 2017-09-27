package com.alexlesaka.carshare.activities.User;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.listeners.MyValueEventListener;
import com.android.volley.toolbox.NetworkImageView;

public class UserActivity extends AppCompatActivity {

    private MainController mainController;
    private NetworkImageView fotoUsuarioView;
    private TextView nameView;
    private MyValueEventListener nameLinstener;
    private TextView emailView;
    private MyValueEventListener emailListener;
    private TextView usernameView;
    private Button editarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mainController = (MainController) getApplicationContext();

        fotoUsuarioView = (NetworkImageView) findViewById(R.id.imageView);
        nameView = (TextView) findViewById(R.id.txtName);
        emailView = (TextView) findViewById(R.id.txtEmail);
        usernameView= (TextView) findViewById(R.id.txtUsername);
        editarButton= (Button) findViewById(R.id.editar_user_button);

        Uri urlImagen = mainController.getLoginController().getFirebaseCurrentUser().getPhotoUrl();
        if (urlImagen != null)
        {
            fotoUsuarioView.setImageUrl(urlImagen.toString(), mainController.getImageController().getUserPhotoLoader());
        }
        nameLinstener = new MyValueEventListener(nameView);
        mainController.getLoginController().getNameRef().addValueEventListener(nameLinstener);
        emailListener = new MyValueEventListener(emailView);
        mainController.getLoginController().getEmailRef().addValueEventListener(emailListener);

        usernameView.setText(mainController.getLoginController().getUsername());

        editarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUserOnclick(v);
            }
        });
    }

    public void editUserOnclick(View v)
    {
        //Fuente: http://guides.codepath.com/android/shared-element-activity-transition
        Intent i = new Intent(this, UserEditActivity.class);
        i.putExtra("name",nameLinstener.getCurrentValue());
        i.putExtra("email",emailListener.getCurrentValue());
        startActivity(i);
    }
}
