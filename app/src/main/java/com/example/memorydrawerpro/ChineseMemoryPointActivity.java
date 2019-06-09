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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLInput;
import java.util.StringTokenizer;

public class ChineseMemoryPointActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private TextView txtTitle;
    private TextView txtContent;
    private ImageView imageMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinese_memory_point);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new MyDatabaseHelper(this, "MemoryPointStore.db", null, 4);
        txtTitle = (TextView) findViewById(R.id.txt_memory_point_title);
        txtContent = (TextView) findViewById(R.id.txt_memory_point_content);
        imageMark = (ImageView) findViewById(R.id.image_mark);
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

            case R.id.edit_item:
            Intent intent = new Intent(ChineseMemoryPointActivity.this,
                    EditMemoryPointActivity.class);
            intent.putExtra("title", txtTitle.getText().toString());
            intent.putExtra("content", txtContent.getText().toString());
            Log.d("memorydrawerpro", "传入的"+txtTitle.getText().toString());
            startActivity(intent);
        }
        return  super.onOptionsItemSelected(item);
    }

    private void loadData(){

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        Log.d("memorydrawerpro", title);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("MemoryPoint", null, "title = ?",
                new String[]{title}, null, null, null);
        if(cursor.moveToFirst()){
            String str = cursor.getString(cursor.getColumnIndex("title"));
            int isMarked = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ismarked")));
            if(isMarked==1) imageMark.setVisibility(View.VISIBLE);
            else imageMark.setVisibility(View.INVISIBLE);
            txtTitle.setText(str);
            Log.d("memorydrawerpro", cursor.getString(cursor.getColumnIndex("title")));
            txtContent.setText(cursor.getString(cursor.getColumnIndex("content")));
        }
        cursor.close();
    }

}
