package com.example.memorydrawerpro;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLInput;
import java.util.StringTokenizer;

public class EditMemoryPointActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private TextView txtTitle;
    private TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memory_point);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new MyDatabaseHelper(this, "MemoryPointStore.db", null, 3);
        txtTitle = (TextView) findViewById(R.id.txt_memory_point_title);
        txtContent = (TextView) findViewById(R.id.txt_memory_point_content);
        txtTitle.setText("编辑功能暂未实现");
        txtContent.setText("尽情期待...");
//        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_ok, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

            case R.id.ok_item:
                Toast.makeText(this,"此功能暂未实现，尽情期待", Toast.LENGTH_SHORT).show();
                return true;

        }
        return  true;
    }

    private void loadData(){

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        Log.d("memorydrawerpro", title);
        Log.d("memorydrawerpro", content);
        txtTitle.setHint(title);
        txtContent.setHint(content);
    }

}
