package com.example.messageboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageboard.api.RetroClient;
import com.example.messageboard.response.Id_checkResponse;
import com.example.messageboard.response.ReceiveMessageResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainBoard extends AppCompatActivity {
    TextView txt_messageShow;
    ImageView imgView1;
    SharedPreferences preferences;
    Bitmap bitmap;
    int i = 0,presentId;
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);
        txt_messageShow=findViewById(R.id.layout_txtMessageShw);
        imgView1=findViewById(R.id.layout_ImageView);

        rl = findViewById(R.id.RelLayout_MainBoard);

        Thread t =new Thread(){

            @Override
            public void run() {
                while(!isInterrupted()){
                    try{
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new MainBoard.task().execute();
                                // i++;
                                //  Toast.makeText(getApplicationContext(),i,Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                }

            }
        };





        //-------------Checking Board is set or not
        preferences = getSharedPreferences("board_id", Context.MODE_PRIVATE);
        if(preferences.contains(("id_board")))
        {
            if(!(preferences.getInt("id_board",0)>0)){
                Intent openBoard = new Intent(MainBoard.this,MainActivity.class);
                startActivity(openBoard);
            }
            else
            {
                t.start();
            }
        }

        //--------------CLOSING of CHECKING Board




    }//closing of on create



    private void callAPI(int boardID)
    {
        Call<ReceiveMessageResponse> set_id_CALL = RetroClient.getInstance().getApi().recvMessageApi(boardID);
        set_id_CALL.enqueue(new Callback<ReceiveMessageResponse>() {
            @Override
            public void onResponse(Call<ReceiveMessageResponse> call, Response<ReceiveMessageResponse> response) {
                ReceiveMessageResponse messgeRecv = response.body();
                if(messgeRecv != null)
                {

                    rl.setBackgroundColor(Color.parseColor("#"+messgeRecv.getColor()));
                    presentId=messgeRecv.getMsg_id();
                        txt_messageShow.setText(messgeRecv.getMessage());


                        if(messgeRecv.getFile().length()>2) {

                            downloadimage dwnld = new downloadimage();
                            dwnld.execute();
                        }
                        else
                        {
                            imgView1.setImageBitmap(null);
                        }


                }
                else
                {
                    rl.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    imgView1.setImageBitmap(null);
                    txt_messageShow.setText("No Message Found with Board ID");
                }
            }

            @Override
            public void onFailure(Call<ReceiveMessageResponse> call, Throwable t) {

            }
        });

    }//closing of CALL API


    private  class task extends android.os.AsyncTask<Void , Void ,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            callAPI(preferences.getInt("id_board",0));
            return null;
        }

    }




    //---------------------------------------------------------
    private class downloadimage extends AsyncTask<Object,String[], Bitmap> {
       // ImageView iv=(ImageView)findViewById(R.id.layout_ImageView);
        @Override
        protected void onPostExecute(Bitmap result) {
            Log.i("MY","image downloaded and set to imageView1 refresh number:"+i);
            // tv.setText("image downloaded and set to imageView1 refresh number:"+i);
            imgView1.setImageBitmap(result);
            super.onPostExecute(result);
        }

        @Override
        protected Bitmap doInBackground(Object... params) {
            try {
                HttpURLConnection connection =
                        (HttpURLConnection)new URL("http://172.16.3.156:80/message_board/images/"+presentId+".jpeg").openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
                input.close();
            }
            catch (IOException ioe){
                Log.i("MY","image");
            }
            return bitmap;
        }
    }




}// closing of Class
