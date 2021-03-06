package com.example.betonit_bettor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;
import org.w3c.dom.Text;

public class BettorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "BettorActivity";
    private DrawerLayout drawer;
    final ParseUser parseUser = ParseUser.getCurrentUser();
    String username, realName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bettor);

        // IMPLEMENT TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // GET USERNAME AND REAL NAME FROM CURRENT USER.
        username = parseUser.getUsername();
        realName = parseUser.getString("user_First") + " " + parseUser.getString("user_Last");

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // GET THE CURRENT USER AND THEIR REAL NAME TO APPEAR ON NAVIGATION DRAWER.
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.userTV);
        TextView navRealname = headerView.findViewById(R.id.userRNTV);
        ImageView logOut = headerView.findViewById(R.id.logOut);
        navUsername.setText(username);
        navRealname.setText(realName);

        navigationView.setNavigationItemSelectedListener(this);

        // ENABLE CLOSING AND OPENING OF NAVIGATION DRAWER.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // DEFAULT FRAGMENT TO OPEN.
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new UPFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_prof);
        }

    }

    // SWITCH CASE TO CHANGE FRAGMENTS.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_prof:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UPFragment()).commit();
                break;

            case R.id.nav_wager:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CWFragment()).commit();
                break;

            case R.id.nav_challenge:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChFragment2()).commit();
                break;

            case R.id.nav_users:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FOFragment()).commit();
//                Intent i = new Intent(this, SearchActivity.class);
//                startActivity(i);
                break;

            case R.id.nav_hist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BHFragment()).commit();
                break;

            case R.id.nav_acceptcase:
                Toast.makeText(this, "Not our use case.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_showcases:
                Toast.makeText(this, "Also not our use case.", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // ENSURE THE BACK BUTTON DOES NOT LOG YOU OUT/CLOSE THE APP IF THE DRAWER IS OPEN.
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // LOGGING OUT OF APPLICATION.
    public void signOut(View view) {
        Log.i(TAG,"aaaaaaaaaa clicked log out");
        ParseUser.logOut();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void getUserImage() {

        final ImageView profilePic = (ImageView)findViewById(R.id.icon);
        ParseFile imageFile = (ParseFile) parseUser.get("user_Icon");
        imageFile.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    profilePic.setImageBitmap(bitmap);
                }
            }
        });
    }

}

