package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView btext,gtext,htext;
    Button bselect,gselect,hselect,logout,menuselect;
    String dangae = "";
    String cylenderValue="";

    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TestLog";
    final static String foldernameImage = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Camera";
    final static String filenameB = "B.txt";
    final static String filenameG = "G.txt";
    /*final static String filenameG = "Gtest.txt";*/
    final static String filenameH = "H.txt";
    String time1="";
    String s1[] = new String[100];
    String formatdata="";
    String user = "";
    String pwd= "";
    public Button sendmail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        sendmail = (Button) findViewById(R.id.sendmail);
        sendmail.setOnClickListener(new View.OnClickListener() {
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

                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                //intent.putExtra("type", "R");
                startActivity(intent);


            }
        });

        menuselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                //intent.putExtra("type", "R");
                startActivity(intent);


            }
        });


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

    // 시스템에 붙어있는 키를 눌렀을 때 발생하는 이벤트
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        cylenderValue = s1[0];
        if(keyCode == KeyEvent.KEYCODE_MEDIA_AUDIO_TRACK){

            Toast.makeText(this,"시스템 버튼 눌림", Toast.LENGTH_LONG).show();
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            String s[] = clipboardManager.getPrimaryClip().getItemAt(0).toString().split(":");
            // 클립보드 데이터를 실린더 데이터만 추출하기위해 스플릿 하는 과정
            s1 = s[1].split(" ");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");

            time1 = format1.format(System.currentTimeMillis());
            int space_num = 14-s1[0].length();
            //띄어쓰기 갯수
            String space_str="";
            for(int i=0;i<space_num;i++){
                space_str+=" ";
            }
            if(!cylenderValue.equals(s1[0])){
            formatdata = time1 + dangae + s1[0] +space_str;
            Log.d("KeyUP Event", "바코드 액션");

            if(dangae.equals("B")){
            WriteTextFile(foldername,filenameB);}

            if(dangae.equals("G")){
                    WriteTextFile(foldername,filenameG);}

            if(dangae.equals("H")){
                    WriteTextFile(foldername,filenameH);}

            }
            return true;
        }

        return false;
        // 이런식으로 처리를 하게 되면 시스템 백 버튼을 누르게 되어도 바탕화면으로 나가지지않고
        // 다른 기능을 실행시킬 수 있도록 시스템 버튼을 제어 할수 있다.


    }

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
            writer.write(formatdata+"1"+'\r'+'\n');
            writer.flush();

            writer.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}




