package com.example.fst_t0763;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Controllers.NueclearApiController;
import com.example.ModelClasses.NueclearGetResponseModel;
import com.example.ModelClasses.Nueclear_Request_Model;
import com.example.adapters.NueclearAPIAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static com.example.fst_t0763.MainActivity.PREFERENCE;

public class NueclearApiCall extends AppCompatActivity {

    Button callNueclearAPI;
    String authkey, ServiceName;
    RecyclerView nueclear_recyclerView;
    ArrayList<NueclearGetResponseModel> serviceMaster = new ArrayList<>();
    ArrayList<NueclearGetResponseModel> sample = new ArrayList<>();
    Nueclear_Request_Model nueclear_request_model;
    ProgressDialog progressDialog;
    NueclearAPIAdapter nueclearAPIAdapter;
    NueclearApiController apiController;
    boolean checker = false;
    String volleyURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nueclear_api_call);
        progressDialog = new ProgressDialog(this);

        callNueclearAPI = findViewById(R.id.nueclear_api_call);
        nueclear_recyclerView = findViewById(R.id.nueclear_api_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(NueclearApiCall.this);
        nueclear_recyclerView.setLayoutManager(manager);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String data = sharedPreferences.getString("saved_arraylist", null);
        if (data != null) {
            getList();
            callNueclearAPI.setText("Clear the Response");
            checker=true;

        } else {

            callNueclearAPI.setText("Call Nueclear API");
        }


        callNueclearAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker) {
                    clearTheList();
                } else {
                    startApiCall();
                }
            }
        });

    }

    private void startApiCall() {

        final CharSequence[] apiMethods = {"volley", "retrofit"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(NueclearApiCall.this);
        dialog.setTitle("select the api call");

        dialog.setItems(apiMethods, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (apiMethods[which].equals("volley")) {
                    dialog.dismiss();
                    nueclear_request_model = new Nueclear_Request_Model();
                    nueclear_request_model.setEmail("COMM_DSA");
                    apiController = new NueclearApiController(NueclearApiCall.this);
                    apiController.FetchNueclearToken_Volley(nueclear_request_model);
                    callNueclearAPI.setText("Clear The Response");
                    checker = true;

                } else if (apiMethods[which].equals("retrofit")) {
                    dialog.dismiss();
                    nueclear_request_model = new Nueclear_Request_Model();
                    nueclear_request_model.setEmail("COMM_DSA");
                    apiController = new NueclearApiController(NueclearApiCall.this);
                    apiController.FetchNueclearToken_Retrofit(nueclear_request_model);

                    callNueclearAPI.setText("Clear The Response");
                    checker = true;


                }
            }
        });
        dialog.show();
    }

    private void clearTheList() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        String savedData = sharedPreferences.getString("saved_arraylist", null);
        if (savedData != null) {
            ed.remove("saved_arraylist");
            ed.apply();
        }
        serviceMaster.clear();
        setAdapter(serviceMaster);
        nueclearAPIAdapter.notifyDataSetChanged();
        callNueclearAPI.setText("Call Nueclear API");
        checker = false;
    }


    //Volleycall



    private void setAdapter(ArrayList<NueclearGetResponseModel> serviceMaster) {

        //sends response model and populates the view.
        nueclearAPIAdapter = new NueclearAPIAdapter(serviceMaster);
        nueclear_recyclerView.setAdapter(nueclearAPIAdapter);
        //saving list in sharedprefs
        saveList(serviceMaster);


    }

    private void saveList(ArrayList<NueclearGetResponseModel> serviceMaster) {
        if (!serviceMaster.isEmpty()) {
            SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String save = new Gson().toJson(serviceMaster);
            editor.putString("saved_arraylist", save);
            editor.commit();
            editor.apply();
        } else {
            Toast.makeText(this, "empty service master", Toast.LENGTH_SHORT).show();
        }

    }

    private void getList() {

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String data = sharedPreferences.getString("saved_arraylist", null);
        TypeToken<ArrayList<NueclearGetResponseModel>> type = new TypeToken<ArrayList<NueclearGetResponseModel>>() {
        };
        sample = new Gson().fromJson(data, type.getType());
        setAdapter(sample);
    }

    public void getResponse(ArrayList<NueclearGetResponseModel> serviceMaster) {
        setAdapter(serviceMaster);

    }
}


