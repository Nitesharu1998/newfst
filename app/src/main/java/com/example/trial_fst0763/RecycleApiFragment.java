package com.example.trial_fst0763;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Controllers.NueclearApiController;
import com.example.ModelClasses.TestMasterResponseModel;
import com.example.adapters.TestMasterApiAdapter;
import com.example.fst_t0763.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static com.example.trial_fst0763.MainActivity.PREFERENCE;


public class RecycleApiFragment extends Fragment {
    RecyclerView rclView;
    TextView rcl_rate_table, rcl_testtype_table;
    ArrayList<TestMasterResponseModel.TestMaster> SortedData = new ArrayList<>();
    boolean FLAG_CHECKER = true;
    private String savedTestMaster, apicalled_date, checksystem_date;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor ed;
    NueclearApiController nueclearApiController;
    FloatingActionButton hard_sync;
    SimpleDateFormat /* timeFormat = new SimpleDateFormat("HH:mm"),*/
            dateFormat = new SimpleDateFormat("HH:mm, yyyy.MM.dd");
    Calendar calendar = Calendar.getInstance();
    Date sysDate;
    Date calledDate;
    Date tempDate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycle__api, container, false);
        rcl_rate_table = v.findViewById(R.id.rcl_rate_table);
        rcl_testtype_table = v.findViewById(R.id.rcl_testtype_table);
        rclView = v.findViewById(R.id.rcl_view);
        hard_sync = v.findViewById(R.id.hard_sync);
        sharedPreferences = v.getContext().getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        ed = sharedPreferences.edit();
        LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        rclView.setLayoutManager(manager);
        rclView.setHasFixedSize(true);

        String data = sharedPreferences.getString("TestMaster", null);
        if (data == null) {
            callApi();
            getAndSaveDate();

        } else {
            try {

                if (checkSystemTimeAndDate()){

                    getAndSaveDate();
                }
                else {
                    TypeToken<ArrayList<TestMasterResponseModel.TestMaster>> type = new TypeToken<ArrayList<TestMasterResponseModel.TestMaster>>() {
                    };
                    SortedData = new Gson().fromJson(data, type.getType());
                    SetAdatper(SortedData);
                    initListerner();
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        initListerner();
        return v;
    }

    private void callApi() {
        nueclearApiController = new NueclearApiController(RecycleApiFragment.this);
        nueclearApiController.callTestmaster();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAndSaveDate() {
        //apicalled_date = dateFormat.format(calendar.getTime());
        calledDate = calendar.getTime();
        System.out.println("called time" + apicalled_date);
        //shareds
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("date", String.valueOf(calledDate));

        ed.commit();
        ed.apply();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkSystemTimeAndDate() throws ParseException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        String testdatestring = sharedPreferences.getString("date", null);
        tempDate=null;
        try {
            tempDate = dateFormat.parse(testdatestring);

        }catch (Exception e){
            System.out.println("parse exception"+e.getLocalizedMessage());
        }

        sysDate = calendar.getTime();

        long difference;
        difference = sysDate.getTime()-tempDate.getTime();
        int numOfDays = (int) (difference / (1000 * 60 * 60 * 24));
        System.out.println("days    "+numOfDays);
        if (sysDate.after(tempDate)) {
            if (numOfDays>0){
                callApi();
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    private void forceSync() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Do you want Sync the Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCE, MODE_PRIVATE);
                SharedPreferences.Editor ed = sharedPreferences.edit();
                String model = sharedPreferences.getString("TestMaster", null);
                if (model != null) {
                    ed.remove("TestMaster");
                    ed.commit();
                    ed.apply();
                }
                callApi();
                dialog.dismiss();
                getAndSaveDate();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }


    private void initListerner() {
        hard_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forceSync();

            }
        });



        rcl_rate_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FLAG_CHECKER) {

                    FLAG_CHECKER = false;
                    rateSortingAscending();
                    SetAdatper(SortedData);
                    Toast.makeText(getActivity(), "Rate: Ascending", Toast.LENGTH_SHORT).show();
                } else {
                    FLAG_CHECKER = true;
                    rateSortingDescending();
                    Toast.makeText(getActivity(), "Rate: Descending", Toast.LENGTH_SHORT).show();
                    SetAdatper(SortedData);
                }
            }

        });


        rcl_testtype_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FLAG_CHECKER) {

                    FLAG_CHECKER = false;
                    testTypeSortingAscending();
                    SetAdatper(SortedData);
                    Toast.makeText(getActivity(), "Test Type: Ascending", Toast.LENGTH_SHORT).show();
                } else {
                    FLAG_CHECKER = true;
                    testTypeSortingDescending();
                    SetAdatper(SortedData);
                    Toast.makeText(getActivity(), "Test Type: Descending", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    private void testTypeSortingAscending() {

        Collections.sort(SortedData, new Comparator<TestMasterResponseModel.TestMaster>() {
            @Override
            public int compare(TestMasterResponseModel.TestMaster o1, TestMasterResponseModel.TestMaster o2) {
                return o1.getTesttype().compareTo(o2.getTesttype());
            }
        });

    }

    private void testTypeSortingDescending() {
        Collections.reverse(SortedData);
    }


    private void rateSortingDescending() {
        Collections.reverse(SortedData);

    }

    private void rateSortingAscending() {
        Collections.sort(SortedData, new Comparator<TestMasterResponseModel.TestMaster>() {


            @Override
            public int compare(TestMasterResponseModel.TestMaster o1, TestMasterResponseModel.TestMaster o2) {
//                        return o1.getRate().compareToIgnoreCase(o2.getRate());
                return Integer.valueOf(o1.getRate()).compareTo(Integer.parseInt(o2.getRate()));
            }


        });


    }

    public void SetAdatper(ArrayList<TestMasterResponseModel.TestMaster> sortedData) {
        TestMasterApiAdapter viewpopulated = new TestMasterApiAdapter(sortedData, getActivity());
        rclView.setAdapter(viewpopulated);
        saveList(sortedData);
    }

    private void saveList(ArrayList<TestMasterResponseModel.TestMaster> sortedData) {
        savedTestMaster = new Gson().toJson(sortedData);
        ed.putString("TestMaster", savedTestMaster);
        ed.commit();

    }

    public void getModel(ArrayList<TestMasterResponseModel.TestMaster> sortedData) {
        SortedData = sortedData;
        SetAdatper(SortedData);
    }
}

