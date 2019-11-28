package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends Activity {

    EditText id, pwd,auth;
    Button loginBtn, changebtn,changeBtnhidden;

    private DbOpenHelper mDbOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();
        id = (EditText)findViewById(R.id.inputId);
        pwd = (EditText)findViewById(R.id.inputPwd);
        auth = (EditText)findViewById(R.id.auth);
        auth.setText("");

        loginBtn = (Button)findViewById(R.id.loginBtn);
        changebtn = (Button)findViewById(R.id.changeBtn);
        changeBtnhidden = (Button)findViewById(R.id.changeBtnhidden);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor iCursor = mDbOpenHelper.sortColumn("userid");
                Log.d("showDatabase", "DB Size: " + iCursor.getCount());

                while(iCursor.moveToNext()){
                    String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
                    String tempID = iCursor.getString(iCursor.getColumnIndex("userid"));
                    String tempName = iCursor.getString(iCursor.getColumnIndex("name"));
                    String id_temp = id.getText().toString();
                    String pwd_temp = pwd.getText().toString();
                    if(tempID.equals(id_temp)&&tempName.equals(pwd_temp)){
                        Intent intent = new Intent(loginActivity.this, MenuActivity.class);
                        intent.putExtra("user", id_temp);
                        intent.putExtra("pwd", pwd_temp);
                        startActivity(intent);
                    }

                }


            }


        });
        changebtn.setVisibility(View.INVISIBLE);
        changeBtnhidden.setVisibility(View.INVISIBLE);

        changeBtnhidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changebtn.setVisibility(View.INVISIBLE);
                changeBtnhidden.setVisibility(View.INVISIBLE);

            }


        });

        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, MemberManageActivity.class);
                //intent.putExtra("type", "R");
                startActivity(intent);

            }


        });

        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = auth.getText().toString();

                if(a.equals("magnabb")){
                changebtn.setVisibility(View.VISIBLE);
                changeBtnhidden.setVisibility(View.VISIBLE);
                }
                else{
                    changebtn.setVisibility(View.INVISIBLE);
                    changeBtnhidden.setVisibility(View.INVISIBLE);
                }

            }


        });

    } // onCreate


}

