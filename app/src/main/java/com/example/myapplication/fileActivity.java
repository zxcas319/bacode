package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class fileActivity extends AppCompatActivity {
    TextView btn;
    Button bts;
    String dangae = "";
    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TestLog";
    final static String filename = "GasData12.txt";
    String time1="";
    String s1[] = new String[100];
    String fomatdata="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (TextView) findViewById(R.id.rt);
        bts = (Button) findViewById(R.id.bts);

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String s[] = clipboardManager.getPrimaryClip().getItemAt(0).toString().split(":");
        s1 = s[1].split(" ");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");

        time1 = format1.format(System.currentTimeMillis());
        btn.setText(time1 + dangae + s1[0] + "\t" + 1);
        fomatdata = time1 + dangae + s1[0] + "\t" + 1;

        Toast.makeText(fileActivity.this, s1[0], Toast.LENGTH_SHORT).show();
        try{
            File dir = new File (foldername);
            //디렉토리 폴더가 없으면 생성함
            if(!dir.exists()){
                dir.mkdir();
            }
            //파일 output stream 생성
            FileOutputStream fos = new FileOutputStream(foldername+"/"+filename, true);
            //파일쓰기
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write("1");
            writer.flush();

            writer.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }





        bts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dangae = "G";

                WriteTextFile(foldername, filename, fomatdata);
                WriteTextFile(foldername, filename, "1");

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String s[] = clipboardManager.getPrimaryClip().getItemAt(0).toString().split(":");
                String s1[] = s[1].split(" ");
                SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
                Date time = new Date();

                String time1 = format1.format(System.currentTimeMillis());
                btn.setText(time1 + dangae + s1[0] + "\t" + 1);
                Toast.makeText(fileActivity.this, s1[0], Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void WriteTextFile(String foldername, String filename, String contents){



        try{
            File dir = new File (foldername);
            //디렉토리 폴더가 없으면 생성함
            if(!dir.exists()){
                dir.mkdir();
            }
            //파일 output stream 생성
            FileOutputStream fos = new FileOutputStream(foldername+"/"+filename, true);
            //파일쓰기
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(contents+"\n");
            writer.flush();

            writer.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
