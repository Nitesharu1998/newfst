package com.example.fst_t0763;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;


public class homePage extends AppCompatActivity {
    FrameLayout frmlayout;
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawer;
    CircleImageView circ_profile;
    public static final String PREFERENCE = "preference";
    DBHelper dbHelper;
    TextView username, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        navigationView = findViewById(R.id.navviewid);
        frmlayout = findViewById(R.id.frm_layout);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawerlayoutid);
        email = findViewById(R.id.nav_email);

        convertToByte ctb = new convertToByte();
        dbHelper = new DBHelper(this);
        View header = navigationView.getHeaderView(0);


        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone", null);
        if (phone != null) {
            circ_profile = header.findViewById(R.id.nav_profile);
            username = header.findViewById(R.id.nav_username);
            email = header.findViewById(R.id.nav_email);
            Cursor getdata = dbHelper.getUdata(phone);
            while (getdata.moveToNext()) {

                String userName = getdata.getString(0);

                username.setText(userName);
                String stremail = getdata.getString(1);

                email.setText(stremail);
                byte[] imgArray = getdata.getBlob(2);
                if (imgArray != null) {


                    circ_profile.setImageBitmap(ctb.getImage(imgArray));
                } else {
                    circ_profile.setImageResource(R.drawable.def_profle);

                    Toast.makeText(homePage.this, "no image was there", Toast.LENGTH_SHORT).show();
                }


            }
        } else {


            Toast.makeText(this, "no data retrieve phone is empty", Toast.LENGTH_SHORT).show();
        }


        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_drawer_open, R.string.close_nav_drawer);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.frm_layout, new barcode_scanner()).commit();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.recycler_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frm_layout, new RecycleApiFragment()).commit();

                        drawer.closeDrawers();

                        break;
                    case R.id.map:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frm_layout, new RouteMap()).commit();
                        /* startActivity(new Intent(homePage.this,RouteMap.class));*/

                        drawer.closeDrawers();

                        break;
                    case R.id.scanner:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frm_layout, new barcode_scanner()).commit();
                        drawer.closeDrawers();
                        break;
                    case R.id.otp_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frm_layout, new OtpReaderGenerator()).commit();
                        drawer.closeDrawers();
                        break;

                    case R.id.book_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frm_layout, new BookMovie()).commit();
                        drawer.closeDrawers();
                        break;
                    case R.id.encrypt:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frm_layout, new Base()).commit();
                        drawer.closeDrawers();
                        break;

                    case R.id.postAuthApi:
                       startActivity(new Intent(homePage.this, AuthApiCall.class));
                        break;

                    case R.id.testMasterNewCall:
                        startActivity(new Intent(homePage.this,NewTestMasterCall.class));
                        drawer.closeDrawers();
                        break;


                    case R.id.signout:

                        drawer.closeDrawers();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(homePage.this);
                        alertDialog.setTitle("Logout");
                        alertDialog.setMessage("Confirm to logout");

                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
                                SharedPreferences.Editor ed = sharedPreferences.edit();
                                String phone = sharedPreferences.getString("phone", null);
                                if (phone != null) {
                                    ed.remove("phone");
                                    ed.remove("saved_arraylist");
                                    ed.remove("TestMaster");
                                    ed.remove("date");
                                    ed.apply();
                                    ed.commit();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    dialog.dismiss();
                                    finish();
                                } else {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    dialog.dismiss();
                                }


                            }
                        });

                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.create();
                        alertDialog.show();

                        break;

                }


                return true;

            }
        });

    }

}