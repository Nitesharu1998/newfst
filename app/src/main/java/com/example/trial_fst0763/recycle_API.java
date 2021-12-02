package com.example.trial_fst0763;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.APIClient_Classes.APIClient;
import com.example.Interface.API_Interface;
import com.example.ModelClasses.DataModel;
import com.example.adapters.rcl_view_holder;
import com.example.fst_t0763.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class recycle_API extends Fragment {
    RecyclerView rclView;
    SwipeRefreshLayout swipeRefreshLayout;
    //    Retrofit retrofit;
//    ArrayList<DataModel> dataModel;
//    rcl_view_holder viewpopulated;
//    rcl_view_holder retrofitAPIClient;
    TextView rcl_rate_table, rcl_testtype_table;
    DataModel dataModel;
    ArrayList<DataModel.TestMaster> SortedData = new ArrayList<>();
    boolean FLAG_CHECKER = true;
    ProgressDialog progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycle__api, container, false);
        rcl_rate_table = v.findViewById(R.id.rcl_rate_table);
        rcl_testtype_table = v.findViewById(R.id.rcl_testtype_table);
        rclView = v.findViewById(R.id.rcl_view);
        swipeRefreshLayout=v.findViewById(R.id.swipeLayout_recycle_api);
        setRetainInstance(true);
        LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        rclView.setLayoutManager(manager);
        rclView.setHasFixedSize(true);


        callAPI();
        initListerner();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAPI();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return v;
    }


    private void callAPI() {
        progressBar = new ProgressDialog(getActivity());
        progressBar.setTitle("Please Wait...");
        progressBar.show();

        //api calling
        API_Interface api_interface = APIClient.getInstance().getClient("https://techso.thyrocare.cloud/techsoapi/api/").create(API_Interface.class);
        //api response
        Call<DataModel> call = api_interface.getPost();

        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                progressBar.dismiss();
                if (response.isSuccessful() && response != null) {
                    Toast.makeText(getActivity(), "api retrieval successful", Toast.LENGTH_SHORT).show();
                    dataModel = response.body();
                    SortedData = dataModel.getTstratemaster();
                    SetAdatper(SortedData);
                } else {
                    Toast.makeText(getActivity(), "No response was noted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initListerner() {

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

        Collections.sort(SortedData, new Comparator<DataModel.TestMaster>() {
            @Override
            public int compare(DataModel.TestMaster o1, DataModel.TestMaster o2) {
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
        Collections.sort(SortedData, new Comparator<DataModel.TestMaster>() {


            @Override
            public int compare(DataModel.TestMaster o1, DataModel.TestMaster o2) {
//                        return o1.getRate().compareToIgnoreCase(o2.getRate());
                return Integer.valueOf(o1.getRate()).compareTo(Integer.parseInt(o2.getRate()));
            }


        });


    }

    public void SetAdatper(ArrayList<DataModel.TestMaster> sortedData) {
        rcl_view_holder viewpopulated = new rcl_view_holder(sortedData, getActivity());
        rclView.setAdapter(viewpopulated);
    }
}
