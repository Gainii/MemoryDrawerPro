package com.example.memorydrawerpro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;

public class MeActivity extends AppCompatActivity {

    TextView txt;
    Typeface typeface;
    TextView tv1;
    TextView tv2;
    TextView tt;
    TextView date;
    TextView ttt;
    private boolean b = false;
    private int count = 0;
    private  MyDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        typeface = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD_ITALIC);
        txt = findViewById(R.id.txt);
        tv1 = findViewById(R.id.text_1);
        tv2 = findViewById(R.id.text_2);
        ttt = findViewById(R.id.ttt);
        date = findViewById(R.id.date);
        ttt.setTypeface(typeface);
        tv1.setTypeface(typeface);
        tv2.setTypeface(typeface);
        txt.setTypeface(typeface);
        dbHelper = new MyDatabaseHelper(this, "MemoryPointStore.db", null, 3);

        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.set(2019, 5, 30);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Date beginDate = new Date(2019, 5, 30);
        Date curDate = new Date(year, month, day);


        ttt.setText("今天是我使用记易橱的第 " + this.getTimeDistance(beginDate,curDate)+" 天");
        tv2.setText("记易橱内有 "+setAmount()+" 个记易点");

//        Drawable drawable1 = getResources().getDrawable(R.drawable.jindutiao);
//        drawable1.setBounds(100,300,40,40);
//        tt.setCompoundDrawables(drawable1,null,null,null);


        final Button button1 = findViewById(R.id.button_1);

        SharedPreferences D = getSharedPreferences("D",MODE_PRIVATE);
        final SharedPreferences.Editor e = D.edit();
        count = D.getInt("Count",0);
        count++;
        txt.setText("已 签 到 " + String.valueOf(count) + " 天");

        SharedPreferences datePress = getSharedPreferences("s",MODE_PRIVATE);
        final SharedPreferences.Editor editor = datePress.edit();
        int Mon = datePress.getInt("Month",0);
        int Dat = datePress.getInt("Day",0);//读取SharedPreferences中的日期（即按下签到按钮时的日期）
        date.setText("上一次签到为 "+ Mon + " 月 " + Dat + " 日 ");
        if((Mon == month)&&(Dat == day)){         //month和day是当前日期，与SharedPreferences储存的日期做比较
            button1.setEnabled(false);
            button1.setTextColor(Color.parseColor("#808080"));
            button1.setBackgroundColor(Color.parseColor("#dcdcdc"));
            button1.setText("🏁已签到");
//            button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_all_shape));
        }else{
            button1.setEnabled(true);
            button1.setTextColor(Color.parseColor("#0000EE"));
            button1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            button1.setText("🚩签到");
//            button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_all_shape));
        }

//        Drawable drawable = getResources().getDrawable(R.drawable.huodongqizhibiaoji);
//        drawable.setBounds(100, 100, 5, 5);
//        button1.setCompoundDrawables(drawable, null, null, null);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                b = true;
                txt.setTypeface(typeface);
                txt.setText("已 签 到 " + String.valueOf(count) + " 天");
                button1.setEnabled(false);
                button1.setTextColor(Color.parseColor("#808080"));
                button1.setBackgroundColor(Color.parseColor("#dcdcdc"));
                button1.setText("🚩已签到");
//                button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_all_shape));
                Calendar calendar1 = Calendar.getInstance();
                int month1 = calendar1.get(Calendar.MONTH) + 1;
                int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
                e.putInt("Count",count);
                e.commit();
                date.setText("上一次签到为 "+month1 + " 月 " + day1 + " 日 ");
                editor.putInt("Month",month1);
                editor.putInt("Day",day1);
                editor.commit();//把按下按钮的日期存进SharedPreferences


            }


        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_nothing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

            case R.id.drawer_item:
                Intent intent = new Intent(this, DrawerActivity.class);
                startActivity(intent);
        }
        return true;
    }

    public static int getTimeDistance(Date beginDate, Date endDate) {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        long beginTime = beginCalendar.getTime().getTime();
        long endTime = endCalendar.getTime().getTime();
        int betweentDays = (int) (endTime - beginTime) / (1000 * 60 * 60 * 24);
        endCalendar.add(Calendar.DAY_OF_MONTH, -betweentDays);
        endCalendar.add(Calendar.DAY_OF_MONTH, -1);
        if (beginCalendar.get(Calendar.DAY_OF_MONTH) == endCalendar.get(Calendar.DAY_OF_MONTH)) {
            return betweentDays + 1;
        } else {
            return betweentDays;
        }
    }

    public int setAmount(){
        int i = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("MemoryPoint", null, null, null,
                null, null, null);
        if(cursor.moveToFirst()){
            while (cursor.moveToNext()) i++;
        }
        cursor.close();
        return i;
    }
}