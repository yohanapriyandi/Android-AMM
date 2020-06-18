package com.siskopsya.amm.APIppob;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiService {

    //... rest of code

    @Headers({
            "Accept:application/json",
            "Content-Type:application/json"
    })
    @POST
    Call<DataResultList> createDataSet(@Body DataSet dataSet, @Url String url);


}