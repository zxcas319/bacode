package com.example.myapplication;

/**
 * Created by DowonYoon on 2017-06-21.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MemberManageActivity extends AppCompatActivity implements View.OnClickListener {


    Button btn_Update;
    Button btn_Insert;
    Button btn_Select;
    Button logout;
    EditText edit_ID;
    EditText edit_Name;
    EditText edit_Age;

    CheckBox check_Man;
    CheckBox check_Woman;
    CheckBox check_ID;
    CheckBox check_Name;
    CheckBox check_Age;

    long nowIndex;
    String ID;
    String name;
    long age;
    String gender = "";
    String sort = "userid";
    ScrollView scrollView;
    ArrayAdapter<String> arrayAdapter;

    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();
    private DbOpenHelper mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_manage);
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);

        btn_Insert = (Button) findViewById(R.id.btn_insert);
        btn_Insert.setOnClickListener(this);
        btn_Update = (Button) findViewById(R.id.btn_update);
        btn_Update.setOnClickListener(this);
        btn_Select = (Button) findViewById(R.id.btn_select);
        btn_Select.setOnClickListener(this);
        edit_ID = (EditText) findViewById(R.id.edit_id);
        edit_Name = (EditText) findViewById(R.id.edit_name);
        edit_Age = (EditText) findViewById(R.id.edit_age);
        scrollView = (ScrollView)findViewById(R.id.ScrollView1);

        check_ID = (CheckBox) findViewById(R.id.check_userid);
        check_ID.setOnClickListener(this);
        check_Name = (CheckBox) findViewById(R.id.check_name);
        check_Name.setOnClickListener(this);


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.db_list_view);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(onClickListener);
        listView.setOnItemLongClickListener(longClickListener);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
}
 });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberManageActivity.this, loginActivity.class);
                //intent.putExtra("type", "R");
                startActivity(intent);

            }


        });
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        check_ID.setChecked(true);
        showDatabase(sort);

        //btn_Insert.setEnabled(true);
        //btn_Update.setEnabled(false);
    }

    public void setInsertMode(){
        edit_ID.setText("");
        edit_Name.setText("");

        //btn_Insert.setEnabled(true);
       // btn_Update.setEnabled(false);
    }

    private AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("On Click", "position = " + position);
            nowIndex = Long.parseLong(arrayIndex.get(position));
            Log.e("On Click", "nowIndex = " + nowIndex);
            Log.e("On Click", "Data: " + arrayData.get(position));
            String[] tempData = arrayData.get(position).split("\\s+");
            Log.e("On Click", "Split Result = " + tempData);
            edit_ID.setText(tempData[0].trim());
            edit_Name.setText(tempData[1].trim());


            //btn_Insert.setEnabled(false);
           // btn_Update.setEnabled(true);
        }
    };

    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("Long Click", "position = " + position);
            nowIndex = Long.parseLong(arrayIndex.get(position));
            String[] nowData = arrayData.get(position).split("\\s+");
            String viewData = nowData[0]+ ", " + nowData[1]  ;
            AlertDialog.Builder dialog = new AlertDialog.Builder(MemberManageActivity.this);
            dialog.setTitle("데이터 삭제")
                    .setMessage("해당 데이터를 삭제 하시겠습니까?" + "\n" + viewData)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MemberManageActivity.this, "데이터를 삭제했습니다.", Toast.LENGTH_SHORT).show();
                            mDbOpenHelper.deleteColumn(nowIndex);
                            showDatabase(sort);
                            setInsertMode();
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MemberManageActivity.this, "삭제를 취소했습니다.", Toast.LENGTH_SHORT).show();
                            setInsertMode();
                        }
                    })
                    .create()
                    .show();
            return false;
        }
    };

    public void showDatabase(String sort){
        Cursor iCursor = mDbOpenHelper.sortColumn(sort);
        Log.d("showDatabase", "DB Size: " + iCursor.getCount());
        arrayData.clear();
        arrayIndex.clear();
        while(iCursor.moveToNext()){
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
            String tempID = iCursor.getString(iCursor.getColumnIndex("userid"));
            tempID = setTextLength(tempID,40);
            String tempName = iCursor.getString(iCursor.getColumnIndex("name"));
            tempName = setTextLength(tempName,40);


            String Result = tempID + tempName;
            arrayData.add(Result);
            arrayIndex.add(tempIndex);
        }
        arrayAdapter.clear();
        arrayAdapter.addAll(arrayData);
        arrayAdapter.notifyDataSetChanged();
    }

    public String setTextLength(String text, int length){
        if(text.length()<length){
            int gap = length - text.length();
            for (int i=0; i<gap; i++){
                text = text + " ";
            }
        }
        return text;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                ID = edit_ID.getText().toString();
                name = edit_Name.getText().toString();

                mDbOpenHelper.open();
                mDbOpenHelper.insertColumn(ID, name);
                showDatabase(sort);
                setInsertMode();
                edit_ID.requestFocus();
                edit_ID.setCursorVisible(true);
                break;

            case R.id.btn_update:
                ID = edit_ID.getText().toString();
                name = edit_Name.getText().toString();

                mDbOpenHelper.updateColumn(nowIndex,ID, name);
                showDatabase(sort);

                edit_ID.requestFocus();
                edit_ID.setCursorVisible(true);
                break;

            case R.id.btn_select:
                showDatabase(sort);
                break;




            case R.id.check_userid:
                check_Name.setChecked(false);
                check_Age.setChecked(false);
                sort = "userid";
                break;

            case R.id.check_name:
                check_ID.setChecked(false);
                check_Age.setChecked(false);
                sort = "name";
                break;


        }
    }
}
