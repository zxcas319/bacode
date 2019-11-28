package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;

import static com.example.myapplication.CustomScannerActivity.formatdatac;
import static com.example.myapplication.CustomScannerActivity.dangae;

public class cameraBacode extends AppCompatActivity {
    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TestLog";
    final static String foldernameImage = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Camera";
    final static String filenameB = "B.txt";
    final static String filenameG = "G.txt";
    /*final static String filenameG = "Gtest.txt";*/
    final static String filenameH = "H.txt";
    String time1="";
    public static String re = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    protected void onResume(){
        super.onResume();

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CustomScannerActivity.class);

        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("onActivityResult", "onActivityResult: .");


        if (resultCode == Activity.RESULT_OK) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            re = scanResult.getContents();
            String message = re;

            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");

            time1 = format1.format(System.currentTimeMillis());
            int space_num = 14-re.length();
            //띄어쓰기 갯수
            String space_str="";
            for(int i=0;i<space_num;i++){
                space_str+=" ";
            }

                formatdatac = time1 + dangae + re +space_str;
                Log.d("KeyUP Event", "바코드 액션");

                if(dangae.equals("B")){
                    WriteTextFile(foldername,filenameB);}

                if(dangae.equals("G")){
                    WriteTextFile(foldername,filenameG);}

                if(dangae.equals("H")){
                    WriteTextFile(foldername,filenameH);}



            Log.d("onActivityResult", "onActivityResult: ." + re);
            Toast.makeText(this, re, Toast.LENGTH_LONG).show();
        }
    }

    private void WriteTextFile(String foldername, String filename) {
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
            writer.write(formatdatac+"1"+'\r'+'\n');
            writer.flush();

            writer.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


}
