package com.example.messageboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messageboard.api.RetroClient;
import com.example.messageboard.response.Id_checkResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button setID_button;
    EditText board_id; int id;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setID_button=findViewById(R.id.btn_setID);
        board_id=findViewById(R.id.et_SetId);
        progressDialog =new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Validating Board ID");


         preferences = getSharedPreferences("board_id", Context.MODE_PRIVATE);


        if(preferences.contains(("id_board")))
            if(preferences.getInt("id_board",0)>1){
                callAPI(preferences.getInt("id_board",0));
            }
        else {
                progressDialog.dismiss();
            }

        setID_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();


                String Board_idStr = board_id.getText().toString();




                if(Board_idStr.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Username must not empty", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;

                }


                if(Board_idStr.length()<=3 && Board_idStr.length()>=10 )
                {
                    Toast.makeText(MainActivity.this, "Username must be between 5 and 10 digits", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }


                if(((Board_idStr != null)
                        && (!Board_idStr.equals(""))
                        && (Board_idStr.matches("^[a-zA-Z0-9]*$"))))
                {

                    id = Integer.parseInt(Board_idStr);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putInt("id_board",id);
                    editor.commit();
                    editor.apply();
                  //  String android_id = Settings.Secure.getString(MainActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                    callAPI(id);

                }

                else
                {
                    Toast.makeText(MainActivity.this, "username must contains alphanumeric", Toast.LENGTH_SHORT).show();
                }

            }//closing of onClick
        });//closing of SetOnclickListener



    }//SetOncreate  Closing

    private void callAPI(int id1)
    {
        Call<Id_checkResponse> set_id_CALL = RetroClient.getInstance().getApi().id_checkApi(id1);
        set_id_CALL.enqueue(new Callback<Id_checkResponse>() {
            @Override
            public void onResponse(Call<Id_checkResponse> call, Response<Id_checkResponse> response) {
                Id_checkResponse id_check = response.body();
                if(id_check != null)
                {
                    if(id_check.isStatus())

                    {

                        Toast.makeText(MainActivity.this,id_check.getMessage(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this,StageActivity.class);

                        startActivity(intent);
                        progressDialog.dismiss();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,id_check.getMessage(),Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Id_checkResponse> call, Throwable t) {

            }
        });

    }//closing of CALL API




}
