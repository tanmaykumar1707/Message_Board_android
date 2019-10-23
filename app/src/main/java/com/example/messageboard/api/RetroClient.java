package com.example.messageboard.api;

import android.content.Intent;

import com.example.messageboard.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static final String  BASE_URL = "http://172.16.3.156/message_board/api/" ;
    private static RetroClient mInstance;
    private Retrofit retrofit;
    private RetroClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }



    public  static synchronized RetroClient getInstance()
    {
        if(mInstance==null)
        {
            mInstance=new RetroClient();
        }
        return mInstance;
    }

    public Api getApi()
    {
        return retrofit.create(Api.class);
    }
}
