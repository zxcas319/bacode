package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.example.myapplication.MainActivity.filenameB;
import static com.example.myapplication.MainActivity.foldername;
import static com.example.myapplication.cameraBacode.re;

/**
 * Created by samsung on 2017-08-28.
 */

public class CustomScannerActivity extends Activity implements DecoratedBarcodeView.TorchListener{
    TextView btext,gtext,htext;
    Button bselect,gselect,hselect,logout,menuselect;
    public static String dangae = "";
    String cylenderValue="";
    public static String formatdatac="";
    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TestLog";
    final static String foldernameImage = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Camera";
    final static String filenameB = "B.txt";
    final static String filenameG = "G.txt";
    /*final static String filenameG = "Gtest.txt";*/
    final static String filenameH = "H.txt";
    String time1="";
    String s1[] = new String[100];

    String user = "";
    String pwd= "";
    public Button sendmail1 =null;
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private BackPressCloseHandler backPressCloseHandler;
    private ImageButton setting_btn,switchFlashlightButton;
    private Boolean switchFlashlightButtonCheck;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scanner);
        btext = (TextView) findViewById(R.id.btext);
        bselect = (Button) findViewById(R.id.bselect);

        gtext = (TextView) findViewById(R.id.btext);
        gselect = (Button) findViewById(R.id.gselect);

        htext = (TextView) findViewById(R.id.btext);
        hselect = (Button) findViewById(R.id.hselect);

        logout = (Button) findViewById(R.id.logout);
        menuselect = (Button) findViewById(R.id.backbtn);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        pwd = intent.getStringExtra("pwd");

        s1[0]="0";

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        sendmail1 = (Button) findViewById(R.id.sendmail1);
        sendmail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });



        bselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dangae = "B";

            }
        });

        gselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dangae = "G";

            }
        });

        hselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dangae = "H";

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CustomScannerActivity.this, loginActivity.class);
                //intent.putExtra("type", "R");
                startActivity(intent);


            }
        });

        menuselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CustomScannerActivity.this, MenuActivity.class);
                //intent.putExtra("type", "R");
                startActivity(intent);


            }
        });

        switchFlashlightButtonCheck = true;

        backPressCloseHandler = new BackPressCloseHandler(this);

        setting_btn = (ImageButton)findViewById(R.id.setting_btn);
        switchFlashlightButton = (ImageButton)findViewById(R.id.switch_flashlight);

        if (!hasFlash()) {
            switchFlashlightButton.setVisibility(View.GONE);
        }

        barcodeScannerView = (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
        barcodeScannerView.setTorchListener(this);
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }
    private void sendEmail()
    {
        try
        {

            emailClient email = new emailClient(user,
                    pwd);
            email.sendMailWithFile("원부자재 텍스트 파일 전송",
                    "sichan.kim@magnachip.com", "donggyu.kim@magnachip.com","body",
                    foldername+"/"+"readme.txt",foldername+"/"+"B.txt",foldername+"/"+"G.txt",foldername+"/"+"H.txt");
            /*email.sendMailWithFile("sendEmailwithFile",
                    "zxcas319@gmail.com", "donggyu.kim@magnachip.com",
                    foldernameImage+"/"+"IMG_20191121_1.05458.jpg", "IMG_20191121_105458.jpg");
            email.sendMail("s","body","zxcas319@gmail.com","donggyu.kim@magnachip.com");*/
        } catch (Exception e)
        {
            Log.d("lastiverse", e.toString());
            Log.d("lastiverse", e.getMessage());
        } // try-catch
    } // void sendEmail
    //텍스트내용을 경로의 텍스트 파일에 쓰기
    public void WriteTextFile(String foldername, String filename){

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
    public void WriteTextFile(String foldername, String filename, String contents){



        try{
            File dir = new File(foldername);
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



    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    public void switchFlashlight(View view) {
        if (switchFlashlightButtonCheck) {
            barcodeScannerView.setTorchOn();
        } else {
            barcodeScannerView.setTorchOff();
        }
    }

    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onTorchOn() {
        switchFlashlightButton.setImageResource(R.drawable.ic_flash_on_white_36dp);
        switchFlashlightButtonCheck = false;
    }

    @Override
    public void onTorchOff() {
        switchFlashlightButton.setImageResource(R.drawable.ic_flash_off_white_36dp);
        switchFlashlightButtonCheck = true;
    }
}