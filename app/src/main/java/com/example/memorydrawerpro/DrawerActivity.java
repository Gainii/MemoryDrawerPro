package com.example.memorydrawerpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class DrawerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //语文按钮
        Button chineseDrawerButton = (Button) findViewById(R.id.button_chinese_drawer);
        chineseDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrawerActivity.this,
                        ChineseMemoryDrawerActivity.class);
                startActivity(intent);
            }
        });


        Button mathDrawerButton = (Button) findViewById(R.id.button_math_drawer);
        mathDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DrawerActivity.this, "请点击语文记忆橱", Toast.LENGTH_SHORT).show();
            }
        });

        Button englishDrawerButton = (Button) findViewById(R.id.button_english_drawer);
        englishDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DrawerActivity.this, "请点击语文记忆橱", Toast.LENGTH_SHORT).show();
            }
        });

        Button historyDrawerButton = (Button) findViewById(R.id.button_history_drawer);
        historyDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DrawerActivity.this, "请点击语文记忆橱", Toast.LENGTH_SHORT).show();
            }
        });

        Button politicsDrawerButton = (Button) findViewById(R.id.button_politics_drawer);
        politicsDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DrawerActivity.this, "请点击语文记忆橱", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_me, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.me_item:
                Intent intent = new Intent(this, MeActivity.class);
                startActivity(intent);

                break;
            default:
        }
        return true;
    }

    //    //右上角菜单
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.drawer, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.add_drawer_item:
//
//            default:
//        }
//        return true;
//    }
}
