package com.example.practicaltestt.wsinterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/api/users?offset=10&limit=100")
    Call<ResponseBody> getUserInfoList();
}