package com.example.trial_fst0763;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

//import static com.example.fst_t0763.MainActivity.PREFERENCE;
import com.example.fst_t0763.R;

import static com.example.trial_fst0763.MainActivity.PREFERENCE;

public class splash extends AppCompatActivity {
    ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pbar=findViewById(R.id.progress);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                pbar.setVisibility(View.VISIBLE);
                SharedPreferences sharedPreferences =getSharedPreferences(PREFERENCE, MODE_PRIVATE);
                String phone= sharedPreferences.getString("phone",null);

                if (phone!=null){
                    pbar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(splash.this,homePage.class));
                    finish();

                }
                else {pbar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(splash.this,MainActivity.class));
                    finish();}
            }
        },3000);


    }


}