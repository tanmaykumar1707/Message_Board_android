package com.example.messageboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StageActivity extends AppCompatActivity {

    TextView txt_ShowId;
    SharedPreferences preferences;
    Button btn_OpenBoard,btnChnageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        txt_ShowId = findViewById(R.id.Layouttxt_shwID);

        //-------------Checking Board is set or not
        preferences = getSharedPreferences("board_id", Context.MODE_PRIVATE);
                if(preferences.contains(("id_board")))
                {
                    if(!(preferences.getInt("id_board",0)>0)){
                        Intent openBoard = new Intent(StageActivity.this,MainActivity.class);
                        startActivity(openBoard);
                    }
                    else
                    {
                        String st= "Board Id: ";

                        txt_ShowId.setText(st+preferences.getInt("id_board",0));
                    }
                }

        //--------------CLOSING of CHECKING Board



        btn_OpenBoard= findViewById(R.id.btn_OpenBoard);
        btn_OpenBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openBoard = new Intent(StageActivity.this,MainBoard.class);
                startActivity(openBoard);
            }
        });


        btnChnageId=findViewById(R.id.btn_changeBoardId);
        btnChnageId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent ChnageId = new Intent(StageActivity.this,MainActivity.class);
                startActivity(ChnageId);
            }
        });




    }//closing of onCreate
}//closing of Class
