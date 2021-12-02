package com.example.trial_fst0763;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.APIClient_Classes.PostAPIClient;
import com.example.Interface.API_Interface;
import com.example.ModelClasses.Requset_Data_Model;
import com.example.ModelClasses.Response_Data_Model;
import com.example.fst_t0763.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostAPI extends Fragment {
    Button requestButton;
    Requset_Data_Model requsetDataModel;
    Requset_Data_Model.ALLLEAVESDTO request_leaves;
    Response_Data_Model response_data_model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_post_api, container, false);
        requestButton = v.findViewById(R.id.postapiButton);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPostRequest();

            }
        });
        return v;
    }

    private void sendPostRequest() {
        API_Interface api_interface = (API_Interface) PostAPIClient.getRetrofitInstance()
                .postClient("http://stagingapi.charbi.com/ThyrostaffWeb/").create(API_Interface.class);

        requsetDataModel = new Requset_Data_Model();
        requsetDataModel.setECODE("1343E");
        requsetDataModel.setALL_LEAVES(set_leavedetails());

        Call<Response_Data_Model> call = api_interface.postAPI(requsetDataModel);
        /* requsetDataModel.setALL_LEAVES(com.example.ModelClasses.Requset_Data_Model.ALL_LEAVES);*/
        call.enqueue(new Callback<Response_Data_Model>() {
            @Override
            public void onResponse(Call<Response_Data_Model> call, Response<Response_Data_Model> response) {
                if (response.isSuccessful() && response.body() != null) {
                    response_data_model = response.body();
                    Toast.makeText(getContext(), "Response:" + response_data_model.getResponse() + "\n" + "Response ID:" + response_data_model.getResponse_Id(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "no response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response_Data_Model> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.i("Exeption", t.getLocalizedMessage());

            }
        });


    }

    private List<Requset_Data_Model.ALLLEAVESDTO> set_leavedetails() {
        List<Requset_Data_Model.ALLLEAVESDTO> leaves_list = new ArrayList<>();
        Requset_Data_Model.ALLLEAVESDTO request_leaves = new Requset_Data_Model.ALLLEAVESDTO();


        request_leaves.setLEAVE_ID("L174201");
        request_leaves.setACTION("Reject");

        leaves_list.add(request_leaves);
        return leaves_list;
    }
}
