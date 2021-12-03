package com.example.Interface;

import com.example.ModelClasses.TestMasterResponseModel;
import com.example.ModelClasses.NueclearGetResponseModel;
import com.example.ModelClasses.NueclearResponseModel;
import com.example.ModelClasses.Nueclear_Request_Model;
import com.example.ModelClasses.Requset_Data_Model;
import com.example.ModelClasses.Response_Data_Model;
import com.example.ModelClasses.TechsoTokenResponseModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API_Interface {

    @GET("BrandTestRateList/2")
    Call<TestMasterResponseModel> getPost();


    @POST("Complaint_module/DH_SUBMIT_LID")
    Call<Response_Data_Model> postAPI(@Body Requset_Data_Model requset_data_model);

    @Multipart
    @POST("OrderAllocation/SelfiafterWOE")
    Call<String> sendAuthHeader(
            @Header("Authorization") String bearer,
            @Part("Btechid") RequestBody Btechid,
            @Part("ORDERNO") RequestBody ORDERNO,
            @Part MultipartBody.Part Image);


    @FormUrlEncoded
    @POST("techsoapi/Token")
    Call<TechsoTokenResponseModel> getThatToken(@Field("UserName") String Username,
                                                @Field("Password") String Password,
                                                @Field("grant_type") String grant_type);


    @POST("Login/RegisteredUser")
    Call<NueclearResponseModel> nueclearCall(@Body Nueclear_Request_Model nueclear_request_model);

    @GET("GetServiceMaster/CN00000001/7738943013")
    Call<ArrayList<NueclearGetResponseModel>> nueclearGetCall(@Header("Authorization") String Bearer);
}
