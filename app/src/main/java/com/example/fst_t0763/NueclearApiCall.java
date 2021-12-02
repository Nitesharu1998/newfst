package com.example.fst_t0763;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.APIClient_Classes.APIClient;
import com.example.Interface.API_Interface;
import com.example.ModelClasses.NueclearGetResponseModel;
import com.example.ModelClasses.NueclearResponseModel;
import com.example.ModelClasses.Nueclear_Request_Model;
import com.example.adapters.NueclearAPIAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.trial_fst0763.MainActivity.PREFERENCE;

public class NueclearApiCall extends AppCompatActivity {
    TextView generateToken;
    Button callNueclearAPI;
    String authkey, ServiceName;
    RecyclerView nueclear_recyclerView;
    ArrayList<NueclearGetResponseModel> serviceMaster = new ArrayList<>();
    ArrayList<NueclearGetResponseModel> sample = new ArrayList<>();

    ProgressDialog progressDialog;
    NueclearAPIAdapter nueclearAPIAdapter;

    String volleyURL;
    Boolean apiCallFlag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nueclear_api_call);
        progressDialog = new ProgressDialog(this);
        generateToken = findViewById(R.id.nueclear_token_generate);
        callNueclearAPI = findViewById(R.id.nueclear_api_call);
        nueclear_recyclerView = findViewById(R.id.nueclear_api_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(NueclearApiCall.this);
        nueclear_recyclerView.setLayoutManager(manager);


        generateToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createToken();
            }
        });
        callNueclearAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] apiMethods = {"volley", "retrofit"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(NueclearApiCall.this);
                dialog.setTitle("select the api call");
                dialog.setItems(apiMethods, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (apiMethods[which].equals("volley")) {
                            dialog.dismiss();
                            sendVolleyCall();
                        } else if (apiMethods[which].equals("retrofit")) {
                            dialog.dismiss();
                            sendRetrofitcall();
                        }
                    }
                });
                dialog.show();
            }
        });

    }

    private void sendVolleyCall() {
        progressDialog.setTitle("Volley api call in progress");
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String savelist = sharedPreferences.getString("saved_arraylist", null);
        if (savelist == null) {
            volleyURL="http://scanso.nueclear.com/api/Login/RegisteredUser";
            Nueclear_Request_Model nueclear_request_model=new Nueclear_Request_Model();
            nueclear_request_model.setEmail("COMM_DSA");

            String post=new Gson().toJson(nueclear_request_model);
            try {
                JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, volleyURL,new JSONObject(post) , new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }
                        , new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }


            volleyURL = "http://scanso.nueclear.com/api/MasterData/GetServiceMaster/CN00000001/7738943013";
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, volleyURL, null, new com.android.volley.Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i <= response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ServiceName = jsonObject.getString("ServiceName");
                            NueclearGetResponseModel sample = new NueclearGetResponseModel();
                            sample.setServiceName(ServiceName);
                            serviceMaster.add(sample);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    setAdapter(serviceMaster);

                }
            },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(NueclearApiCall.this, "Error" + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> header = new HashMap<String, String>();
                    header.put("Authorization", "Bearer " + authkey);
                    return header;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);
        }else{
           getList();
        }
    }


    private void setAdapter(ArrayList<NueclearGetResponseModel> serviceMaster) {

        //sends response model and populates the view.
        nueclearAPIAdapter = new NueclearAPIAdapter(serviceMaster);
        nueclear_recyclerView.setAdapter(nueclearAPIAdapter);
        //saving list in sharedprefs
        saveList(serviceMaster);


    }

    private void saveList(ArrayList<NueclearGetResponseModel> serviceMaster) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String save = new Gson().toJson(serviceMaster);
        editor.putString("saved_arraylist", save);
        editor.commit();
        editor.apply();

    }
    private void getList() {
        Gson gson=new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String data = sharedPreferences.getString("saved_arraylist", null);
        TypeToken<ArrayList<NueclearGetResponseModel>> type = new TypeToken<ArrayList<NueclearGetResponseModel>>() {};
        sample = gson.fromJson(data, type.getType());
        setAdapter(sample);
    }


    private void sendRetrofitcall() {
        try {

            progressDialog.setTitle("Retrofit api call in progress");
            progressDialog.setMessage("please wait...");
            progressDialog.show();
            if (authkey != null) {

                API_Interface apiInterface = APIClient.getInstance().getClient("http://scanso.nueclear.com/api/MasterData/").create(API_Interface.class);
                /*String testAuth="Bearer "+authkey;*/
                Call<ArrayList<NueclearGetResponseModel>> nueclearGetResponseModel = apiInterface.nueclearGetCall("Bearer " + authkey);
                nueclearGetResponseModel.enqueue(new Callback<ArrayList<NueclearGetResponseModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NueclearGetResponseModel>> call, Response<ArrayList<NueclearGetResponseModel>> response) {
                        if (response.isSuccessful() && response != null) {
                            serviceMaster = response.body();
                            setAdapter(serviceMaster);
                            progressDialog.dismiss();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(NueclearApiCall.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<ArrayList<NueclearGetResponseModel>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(NueclearApiCall.this, "failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        } catch (Exception e) {
            Log.i("Exception", e.getLocalizedMessage());
        }
    }

    private void createToken() {
        Nueclear_Request_Model nueclear_request_model = new Nueclear_Request_Model();

        API_Interface api_interface = APIClient.getInstance().getClient("http://scanso.nueclear.com/api/")
                .create(API_Interface.class);
        nueclear_request_model.setEmail("COMM_DSA");
        api_interface.nueclearCall(nueclear_request_model);
        Call<NueclearResponseModel> call = api_interface.nueclearCall(nueclear_request_model);
        call.enqueue(new Callback<NueclearResponseModel>() {
            @Override
            public void onResponse(Call<NueclearResponseModel> call, Response<NueclearResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NueclearResponseModel nueclearResponseModel;
                    nueclearResponseModel = response.body();

                    authkey = nueclearResponseModel.getAccessToken();
                    Toast.makeText(NueclearApiCall.this, "success \n" + authkey, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NueclearApiCall.this, "response null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NueclearResponseModel> call, Throwable t) {
                Log.i("Error", t.getLocalizedMessage());

            }
        });

    }
}