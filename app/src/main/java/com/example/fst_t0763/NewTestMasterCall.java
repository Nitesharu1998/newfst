package com.example.fst_t0763;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.Controllers.NueclearApiController;
import com.example.ModelClasses.TestMasterResponseModel;

import java.util.ArrayList;

public class NewTestMasterCall extends AppCompatActivity {
    private NueclearApiController controller;
    private ArrayList<TestMasterResponseModel.TestMaster> sorteddata = new ArrayList<>();
    TableLayout tableLayout;
    LinearLayout tablelinear;
    ScrollView scrollView;
    /*TableRow tableRow;*/
    TextView testtype, fasting, description, rate;
    String type,fast,desc,Rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test_master_call);
        tablelinear = findViewById(R.id.table_linear);
        scrollView=new ScrollView(this);
        callAPI();

    }

    private void callAPI() {
        controller = new NueclearApiController(NewTestMasterCall.this);
        controller.callTestmaster();
    }

    public void getModel(ArrayList<TestMasterResponseModel.TestMaster> sortedData) {
        sorteddata = sortedData;
        try {

            populateTable(sorteddata);
        } catch (Exception e) {
            System.out.println("tableException" + e.getLocalizedMessage());
        }
    }

    @SuppressLint("ResourceAsColor")
    private void populateTable(ArrayList<TestMasterResponseModel.TestMaster> sorteddata) {

       /* tablelinear.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));*/

        tableLayout = new TableLayout(NewTestMasterCall.this);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        tableLayout.setStretchAllColumns(true);
        tableLayout.bringToFront();

        for (int i = 0; i < sorteddata.size(); i++) {


            TableRow tableRow = new TableRow(NewTestMasterCall.this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TestMasterResponseModel.TestMaster tstratemasterDTO = sorteddata.get(i);
            testtype = new TextView(NewTestMasterCall.this);
            fasting = new TextView(NewTestMasterCall.this);
            description = new TextView(NewTestMasterCall.this);
            rate = new TextView(NewTestMasterCall.this);

            testtype.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            testtype.setTextColor(R.color.black);
            testtype.setGravity(View.TEXT_ALIGNMENT_CENTER);
            type=tstratemasterDTO.getTesttype().toString();
            testtype.setText(type);
            tableRow.addView(testtype);


            fasting.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            fast=tstratemasterDTO.getFasting().toString();
            fasting.setText(fast);
            fasting.setGravity(View.TEXT_ALIGNMENT_CENTER);
            fasting.setTextColor(R.color.black);
            tableRow.addView(fasting);

            description.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            desc=tstratemasterDTO.getDesc().toString();
            description.setText(desc);
            description.setGravity(View.TEXT_ALIGNMENT_CENTER);
            description.setTextColor(R.color.black);
            tableRow.addView(description);


            rate.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            rate.setGravity(View.TEXT_ALIGNMENT_CENTER);

            rate.setTextColor(R.color.black);
            Rate=tstratemasterDTO.getRate().toString();
            rate.setText(Rate);
            tableRow.addView(rate);
            System.out.println("rows data   "+type+" "+fast+" "+desc+" "+Rate);

            tableLayout.addView(tableRow);

        }
        tablelinear.addView(tableLayout);

    }
}