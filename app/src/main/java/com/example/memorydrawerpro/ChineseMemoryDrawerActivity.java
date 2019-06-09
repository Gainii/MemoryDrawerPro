package com.example.memorydrawerpro;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import org.litepal.LitePalApplication;
import org.litepal.LitePalDB;
import org.litepal.crud.LitePalSupport;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class ChineseMemoryDrawerActivity extends AppCompatActivity {

    private  MyDatabaseHelper dbHelper;
    private Vector<String> titles;
    private Vector<String> temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinese_memory_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new MyDatabaseHelper(this, "MemoryPointStore.db", null, 3);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                ChineseMemoryDrawerActivity.this, android.R.layout.simple_list_item_1,this.loadData());
        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ChineseMemoryDrawerActivity.this);
                dialog.setMessage("确定要删除该记易点吗？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeData(position);
                        titles.remove(position);
                        listView.setAdapter(adapter);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChineseMemoryDrawerActivity.this,
                        ChineseMemoryPointActivity.class);
                intent.putExtra("title", temp.get(position));
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ChineseMemoryDrawerActivity.this);
                dialog.setTitle("提示：");
                dialog.setMessage("##长按一条记易点，即可删除\n" +
                        "##开头带有“*”的条目为标记过重点的记易点");
                dialog.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_add, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Intent intent1 = new Intent(ChineseMemoryDrawerActivity.this,
                        AddMemoryPointActivity.class);
                startActivity(intent1);
                break;

            case android.R.id.home:
                Intent intent2 = new Intent(ChineseMemoryDrawerActivity.this,
                        DrawerActivity.class);
                startActivity(intent2);
                return true;
            default:
        }
        return true;
    }


    public Vector<String> loadData(){
        titles = new Vector<String>();
        temp = new Vector<String>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("MemoryPoint", null, null, null,
                null, null, null);
        if(cursor.moveToFirst()){
            int i = 0;
            do{
                String str = cursor.getString(cursor.getColumnIndex("title"));
                temp.add(str);
                int isMarked = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ismarked")));
                if(isMarked==1) titles.add(" ※ " + str);
                else titles.add(str);
                Log.d("memorydrawerpro", cursor.getString(cursor.getColumnIndex("title")));
                Log.d("memorydrawerpro", cursor.getString(cursor.getColumnIndex("id")));
                Log.d("memorydrawerpro", cursor.getString(cursor.getColumnIndex("ismarked")));
//                Log.d("memorydrawerpro", "标记："+cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(3)))));
                i++;
            }while (cursor.moveToNext());
        }
        cursor.close();

        return titles;
    }

    private void removeData(int position){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("MemoryPoint", "title = ?", new String[]{titles.get(position)});
    }

}
