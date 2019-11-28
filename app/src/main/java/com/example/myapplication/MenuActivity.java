package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    TextView title;
    Button bacodeview,cameraview,logoutm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        title = (TextView) findViewById(R.id.btext);
        bacodeview = (Button) findViewById(R.id.bacodeview);
        cameraview = (Button) findViewById(R.id.cameraview);
        logoutm = (Button) findViewById(R.id.logoutm);
        Intent intent = getIntent();
        final String user = intent.getStringExtra("user");
        final String pwd = intent.getStringExtra("pwd");

        bacodeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("pwd", pwd);
                startActivity(intent);

            }
        });

        cameraview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, cameraBacode.class);
                intent.putExtra("user", user);
                intent.putExtra("pwd", pwd);
                startActivity(intent);

            }
        });

        logoutm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, loginActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("pwd", pwd);
                startActivity(intent);

            }
        });


    }
}
