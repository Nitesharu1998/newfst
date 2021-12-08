package com.example.fst_t0763;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.Controllers.NueclearApiController;
import com.example.ModelClasses.TestMasterResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.fst_t0763.MainActivity.PREFERENCE;

public class NewTestMasterCall extends AppCompatActivity {
    private NueclearApiController controller;
    private ArrayList<TestMasterResponseModel.TestMaster> sorteddata = new ArrayList<>();
    TableLayout tableLayout;


    ScrollView scrollView;
    /*TableRow tableRow;*/
    TextView testtype, fasting, description, rate, serial;
    String type, fast, desc, Rate;
    ProgressDialog progressDialog;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test_master_call);
        tableLayout = findViewById(R.id.table_linear);
        progressDialog = new ProgressDialog(NewTestMasterCall.this);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        TypeToken<ArrayList<TestMasterResponseModel.TestMaster>> typeToken = new TypeToken<ArrayList<TestMasterResponseModel.TestMaster>>() {
        };
        String storedList = sharedPreferences.getString("TestMaster", null);

        if (storedList == null) {
            callAPI();

        } else {
            sorteddata = new Gson().fromJson(storedList, typeToken.getType());
            populateTable(sorteddata);
        }

    }

    private void callAPI() {
        controller = new NueclearApiController(NewTestMasterCall.this);
        controller.callTestmaster();
    }

    public void getModel(ArrayList<TestMasterResponseModel.TestMaster> sortedData) {

        try {
            sorteddata = sortedData;
            populateTable(sorteddata);
        } catch (Exception e) {
            System.out.println("tableException" + e.getLocalizedMessage());
        }

    }

    @SuppressLint("ResourceAsColor")
    private void populateTable(ArrayList<TestMasterResponseModel.TestMaster> sorteddata) {
        TableRow tableRow=null;
        for (int i = 0; i < sorteddata.size(); i++) {
            tableRow = new TableRow(NewTestMasterCall.this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT));

            TestMasterResponseModel.TestMaster testMaster = sorteddata.get(i);

            View v = LayoutInflater.from(NewTestMasterCall.this).inflate(R.layout.recycle_row, null);

            serial = v.findViewById(R.id.rclcount);
            testtype = v.findViewById(R.id.rcl_testtype);
            fasting = v.findViewById(R.id.rcl_fasting);

            description = v.findViewById(R.id.rcl_description);
            rate = v.findViewById(R.id.rcl_rate);

            serial.setText(String.valueOf(i));
            testtype.setText(testMaster.getTesttype());
            fasting.setText(testMaster.getFasting());
            if (testMaster.getDesc().equals("")) {
                description.setText(testMaster.getTestCode());
            } else {
                description.setText(testMaster.getDesc());
            }
            rate.setText(testMaster.getRate());


            tableRow.addView(v);

            tableLayout.addView(tableRow);

        }

    }


}






















