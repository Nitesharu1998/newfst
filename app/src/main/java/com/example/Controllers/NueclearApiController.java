package com.example.Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.APIClient_Classes.APIClient;
import com.example.Interface.API_Interface;
import com.example.ModelClasses.TestMasterResponseModel;
import com.example.ModelClasses.NueclearGetResponseModel;
import com.example.ModelClasses.NueclearResponseModel;
import com.example.ModelClasses.Nueclear_Request_Model;
import com.example.fst_t0763.NueclearApiCall;
import com.example.trial_fst0763.RecycleApiFragment;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NueclearApiController {
    private NueclearApiCall nueclearApiCall;
    private RecycleApiFragment recycleApiFragment;
    private Activity activity;

    public NueclearApiController(NueclearApiCall nueclearApiCall) {
        this.nueclearApiCall = nueclearApiCall;
        this.activity = nueclearApiCall;
    }

    public NueclearApiController(RecycleApiFragment recycleApiFragment) {
        this.recycleApiFragment = recycleApiFragment;
        this.activity = recycleApiFragment.getActivity();


    }

    private String AuthKey, volleyUrl, ReqModel, tempServiceName;
    API_Interface api_interface;
    int volley_flag = 0, retrofit_flag = 1;
    private ArrayList<NueclearGetResponseModel> serviceMaster = new ArrayList<>();
    private ArrayList<TestMasterResponseModel.TestMaster> sortedData = new ArrayList<>();
    private TestMasterResponseModel testMasterResponseModel;
    private ProgressDialog progressDialog;


    //TestMasterApi Call
    public void callTestmaster() {
        progressDialog=new ProgressDialog(activity,ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Please wait....");
        progressDialog.show();
        API_Interface api_interface = APIClient.getInstance().getClient("https://techso.thyrocare.cloud/techsoapi/api/").create(API_Interface.class);
        Call<TestMasterResponseModel> call = api_interface.getPost();
        call.enqueue(new Callback<TestMasterResponseModel>() {
            @Override
            public void onResponse(Call<TestMasterResponseModel> call, Response<TestMasterResponseModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    testMasterResponseModel = response.body();
                    sortedData = testMasterResponseModel.getTstratemaster();
                    if (sortedData != null) {
                        sendToFragment(sortedData);
                    }
                }

            }

            @Override
            public void onFailure(Call<TestMasterResponseModel> call, Throwable t) {
                System.out.println("testmaster Fail "+t.getLocalizedMessage());
                progressDialog.dismiss();
            }
        });

    }


    //RetrofitCall
    public void FetchNueclearToken_Retrofit(Nueclear_Request_Model nueclear_request_model) {
        api_interface = APIClient.getInstance().getClient("http://scanso.nueclear.com/api/").create(API_Interface.class);
        Call<NueclearResponseModel> responseModelCall = api_interface.nueclearCall(nueclear_request_model);
        responseModelCall.enqueue(new Callback<NueclearResponseModel>() {
            @Override
            public void onResponse(Call<NueclearResponseModel> call, Response<NueclearResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NueclearResponseModel nueclearResponseModel;
                    nueclearResponseModel = response.body();
                    AuthKey = nueclearResponseModel.getAccessToken();
                    if (AuthKey != null) {
                        getServiceName(AuthKey, retrofit_flag);
                    } else {
                        Toast.makeText(activity, "key was null", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<NueclearResponseModel> call, Throwable t) {
                System.out.println("Failed" + t.getLocalizedMessage());
            }
        });

    }
//VolleyCall

    public void FetchNueclearToken_Volley(Nueclear_Request_Model nueclear_request_model) {
        volleyUrl = "http://scanso.nueclear.com/api/Login/RegisteredUser";
        ReqModel = new Gson().toJson(nueclear_request_model);
        try {
            JsonObjectRequest jsonTokenRequest = new JsonObjectRequest(Request.Method.POST,
                    volleyUrl, new JSONObject(ReqModel),
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (response.length() != 0) {
                                try {
                                    AuthKey = response.getString("access_token");
                                    if (AuthKey.length() != 0) {
                                        getServiceName(AuthKey, volley_flag);
                                    }

                                } catch (JSONException e) {
                                    System.out.println("Response Exception " + e.getMessage());
                                }
                            }

                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Volley Error" + error.getMessage());

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

            };
            RequestQueue request = Volley.newRequestQueue(activity);
            request.add(jsonTokenRequest);
        } catch (Exception e) {
            System.out.println("volleyException " + e.getLocalizedMessage());
        }


    }

    private void getServiceName(String authKey, int Flag) {
        if (Flag == retrofit_flag) {
            try {
                api_interface = APIClient.getInstance().getClient("http://scanso.nueclear.com/api/MasterData/").create(API_Interface.class);
                Call<ArrayList<NueclearGetResponseModel>> nueclearGetResponseModelCall = api_interface.nueclearGetCall("Bearer " + authKey);
                nueclearGetResponseModelCall.enqueue(new Callback<ArrayList<NueclearGetResponseModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NueclearGetResponseModel>> call, Response<ArrayList<NueclearGetResponseModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            serviceMaster = response.body();
                            sendToActivity(serviceMaster, Flag);
                        } else {
                            Toast.makeText(activity, "response body:null", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<NueclearGetResponseModel>> call, Throwable t) {
                        System.out.println("retrofit_failure " + t.getLocalizedMessage());
                    }
                });

            } catch (Exception e) {
                System.out.println("Exception" + e.getLocalizedMessage());
            }
        } else if (Flag == volley_flag) {
            volleyUrl = "http://scanso.nueclear.com/api/MasterData/GetServiceMaster/CN00000001/7738943013";
            JsonArrayRequest serviceArrayRequest = new JsonArrayRequest(Request.Method.GET, volleyUrl,
                    null, new com.android.volley.Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int jData = 0; jData <= response.length(); jData++) {
                            JSONObject jsonObject = response.getJSONObject(jData);
                            tempServiceName = jsonObject.getString("ServiceName");
                            NueclearGetResponseModel getResponseModel = new NueclearGetResponseModel();
                            getResponseModel.setServiceName(tempServiceName);
                            serviceMaster.add(getResponseModel);
                            sendToActivity(serviceMaster, Flag);
                        }
                    } catch (Exception e) {
                        System.out.println("Volley_responseError " + e.getLocalizedMessage());
                    }

                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> volleytoken = new HashMap<String, String>();
                    volleytoken.put("Authorization", "Bearer " + authKey);
                    return volleytoken;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            requestQueue.add(serviceArrayRequest);
        }
    }


    private void sendToActivity(ArrayList<NueclearGetResponseModel> serviceMaster, int flag) {
        if (flag == volley_flag) {
            nueclearApiCall.getResponse(serviceMaster);
        } else if (flag == retrofit_flag) {
            {
                Collections.reverse(serviceMaster);
                nueclearApiCall.getResponse(serviceMaster);
                Toast.makeText(activity, "sample", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void sendToFragment(ArrayList<TestMasterResponseModel.TestMaster> sortedData) {
        recycleApiFragment.getModel(sortedData);
    }


}
