package com.siskopsya.amm.APIppob;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface TopupPulsaApiService {

    //... rest of code

    @Headers({
            "Accept:application/json",
            "Content-Type:application/json"
    })
    @POST
    Call<TopupResultList> createDataSet(@Body TopupSet topupSet, @Url String url);


}