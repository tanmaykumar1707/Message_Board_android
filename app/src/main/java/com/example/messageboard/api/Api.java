package com.example.messageboard.api;

import com.example.messageboard.response.Id_checkResponse;
import com.example.messageboard.response.ReceiveMessageResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("id_check_api.php")
    Call<Id_checkResponse> id_checkApi(
        @Field("board_id") int board_id
    );

    @FormUrlEncoded
    @POST("message_api.php")
    Call<ReceiveMessageResponse> recvMessageApi(
            @Field("board_id") int board_id
    );


}
