package com.alexlesaka.carshare.activities.Initialization;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.activities.User.AllUsersActivity;
import com.alexlesaka.carshare.activities.Group.CreateGroupActivity;
import com.alexlesaka.carshare.activities.Friendship.FriendListActivity;
import com.alexlesaka.carshare.activities.Friendship.FriendshipRequestListActivity;
import com.alexlesaka.carshare.activities.User.UserActivity;
import com.alexlesaka.carshare.adapters.GroupFirebaseAdapter;
import com.alexlesaka.carshare.myviews.CircularNetworkImageView;
import com.alexlesaka.carshare.listeners.MyValueEventListener;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainController mainController;
    private SharedPreferences pref;



    /*Graphics*/
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View headerLayout;
    private CircularNetworkImageView fotoUsuarioView;
    private TextView nameView;
    private MyValueEventListener nameListener;
    private TextView usernameView;

    private FloatingActionButton fab;


    //Group List
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("pref", MODE_PRIVATE);

        mainController = (MainController) getApplicationContext();
        inicializarVariablesGraphicos();
        cargarFuncionalidades();

        mainController = (MainController) getApplication();

        //Group List
        recyclerView = (RecyclerView) findViewById(R.id.groups_recycler_view);
        recyclerView.setAdapter(new GroupFirebaseAdapter(getApplicationContext(), mainController.getLoginController().getGroupsRef()));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_friendshiprequests) {
            Intent i = new Intent(this, FriendshipRequestListActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_allusers) {
            Intent i = new Intent(this, AllUsersActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_createGroup) {
            Intent i = new Intent(this, CreateGroupActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_friendlist) {
            Intent i = new Intent(this, FriendListActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_friendshiprequests) {
            Intent i = new Intent(this, FriendshipRequestListActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_signout) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mainController.getLoginController().doLogout(pref);
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }
                    });
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void inicializarVariablesGraphicos() {
        //Graphics
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        headerLayout = navigationView.getHeaderView(0);
        fotoUsuarioView = (CircularNetworkImageView) headerLayout.findViewById(R.id.user_image);
        nameView = (TextView) headerLayout.findViewById(R.id.user_name);
        //emailView = (TextView) headerLayout.findViewById(R.id.txtEmail);
        usernameView = (TextView) headerLayout.findViewById(R.id.user_username);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    public void cargarFuncionalidades() {


        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Header data: image, username, user and email
        Uri urlImagen = mainController.getLoginController().getFirebaseCurrentUser().getPhotoUrl();
        if (urlImagen != null) {
            fotoUsuarioView.setImageUrl(urlImagen.toString(), mainController.getImageController().getUserPhotoLoader());
        }

        nameListener = new MyValueEventListener(nameView);
        mainController.getLoginController().getNameRef().addValueEventListener(nameListener);


        usernameView.setText(mainController.getLoginController().getUsername());

        //Floating button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mainController, CreateGroupActivity.class);
                startActivity(i);
            }
        });

        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userOnClick(v);
            }
        });

    }

    public void userOnClick(View v) {
        Intent i = new Intent(this, UserActivity.class);
        startActivity(i);
    }
}