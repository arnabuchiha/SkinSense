package com.arnab.skinsense;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;

import java.util.Iterator;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Firebase Variables
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String email;
    android.support.v4.app.Fragment frag;

    private TextView nameofuser, emailofuser, surprisetext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        Hawk.init(NavigationActivity.this).setEncryption(new NoEncryption()).build();
        if(Hawk.contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            ListHolder.list=Hawk.get(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("user");
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Toast.makeText(NavigationActivity.this, "Signed in as: "+user.getEmail(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(NavigationActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(NavigationActivity.this, LoginActivity.class), 9);
                }
            }
        };

        setContentView(R.layout.activity_navigation);
        surprisetext = findViewById(R.id.surprisetext);

        if(ListHolder.list.size() == 0)
            surprisetext.setVisibility(View.VISIBLE);
        else
            surprisetext.setVisibility(View.GONE);

        frag = new UserProfile();
        if (frag!=null)
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction ft=fragmentManager.beginTransaction();

            ft.replace(R.id.fragmentview,frag);
            ft.commit();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameofuser = findViewById(R.id.nameofuser);
        emailofuser = findViewById(R.id.emailofuser);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(NavigationActivity.this, MainActivity.class));
                android.util.Log.d("Tanay", "Detection Activity");
                //Toast.makeText(NavigationActivity.this, "Snap", Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> itr = dataSnapshot.getChildren();
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String username = "";
                Iterator<DataSnapshot> iterator = itr.iterator();
                while(iterator.hasNext()){
                    DataSnapshot temp = iterator.next();
                    if(email.equalsIgnoreCase(temp.child("email").getValue().toString())){
                        username = temp.child("username").getValue().toString();
                        break;
                    }
                }
                //Toast.makeText(NavigationActivity.this, username+":::"+email, Toast.LENGTH_LONG).show();
                //fetch data from database
                nameofuser = findViewById(R.id.nameofuser);
                emailofuser = findViewById(R.id.emailofuser);
                nameofuser.setText(username);
                emailofuser.setText(email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == resultCode){
            Toast.makeText(this, "Signed in as:" + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Toast.makeText(NavigationActivity.this, "Signed in as: "+user.getEmail(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(NavigationActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(NavigationActivity.this, LoginActivity.class), 9);
                }
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            android.support.v4.app.Fragment fragment = new Settings();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction ft=fragmentManager.beginTransaction();

            ft.replace(R.id.fragmentview,fragment);
            ft.commit();
        }
        if (id == R.id.action_signout) {
            firebaseAuth.signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.Fragment fragment = null;
        if (id == R.id.home) {
            fragment = frag;
        } else if (id == R.id.hospitals) {
            Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.settings) {
            fragment=new Settings();

        } else if (id == R.id.about_us) {
            fragment=new AboutUc();
        }

        if (fragment!=null)
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction ft=fragmentManager.beginTransaction();

            ft.replace(R.id.fragmentview,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
