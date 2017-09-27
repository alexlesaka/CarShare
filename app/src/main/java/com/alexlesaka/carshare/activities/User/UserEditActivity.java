package com.alexlesaka.carshare.activities.User;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.R;
import com.android.volley.toolbox.NetworkImageView;

public class UserEditActivity extends AppCompatActivity {

    private MainController mainController;
    private NetworkImageView fotoUsuarioView;
    private EditText nameView;
    private EditText emailView;
    private TextView usernameView;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        mainController = (MainController) getApplicationContext();

        fotoUsuarioView = (NetworkImageView) findViewById(R.id.imageView);
        usernameView = (TextView) findViewById(R.id.txtUsername);
        nameView = (EditText) findViewById(R.id.txtName);
        emailView = (EditText) findViewById(R.id.txtEmail);
        updateButton = (Button) findViewById(R.id.updateUserProfileButton);

        usernameView.setText(mainController.getLoginController().getUsername());

        Bundle b = getIntent().getExtras();
        String name = "", email = "";

        if (b != null) {
            name = b.getString("name");
            email = b.getString("email");
        }

        nameView.setText(name);
        emailView.setText(email);

        Uri urlImagen = mainController.getLoginController().getFirebaseCurrentUser().getPhotoUrl();
        if (urlImagen != null) {
            fotoUsuarioView.setImageUrl(urlImagen.toString(), mainController.getImageController().getUserPhotoLoader());
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }

    public void updateUser() {
        if (nameView.getText().toString().length() == 0) {
            Toast.makeText(mainController, R.string.toast_emptyname, Toast.LENGTH_SHORT).show();
        } else {

            mainController.getLoginController().updateName(nameView.getText().toString());
            mainController.getLoginController().updateEmail(emailView.getText().toString());
            finish();
        }
    }
}
