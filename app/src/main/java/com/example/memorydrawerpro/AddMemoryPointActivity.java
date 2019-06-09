package com.example.memorydrawerpro;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class AddMemoryPointActivity extends AppCompatActivity {

    private int flag = 0;
    EditText editTitle;
    EditText editContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory_point);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final Switch switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switch1.isChecked()) flag = 1;
                else flag = 0;
            }
        });

        editTitle = (EditText) findViewById(R.id.edit_memory_point_title);
        editContent = (EditText) findViewById(R.id.edit_memory_point_content);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_ok, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ok_item:
                if(editTitle.getText().toString().equals("") ||
                    editContent.getText().toString().equals(""))
                    Toast.makeText(this, "标题栏或内容栏不能为空哦", Toast.LENGTH_SHORT).show();
                else {
                    this.loadDB();
                    Intent intent = new Intent(AddMemoryPointActivity.this,
                            ChineseMemoryDrawerActivity.class);
                    startActivity(intent);
                }
                break;
            case  android.R.id.home:
                finish();
                return true;
            default:
        }

        return true;
    }



    public void loadDB(){
        MemoryPoint memoryPoint = new MemoryPoint();

        final String title = editTitle.getText().toString();
        String content = editContent.getText().toString();

        memoryPoint.setTitle(title);
        memoryPoint.setContent(content);
        memoryPoint.setMarked(flag);
        memoryPoint.save();
        memoryPoint.saveThrows();

        Intent intent = new Intent(AddMemoryPointActivity.this,
                ChineseMemoryDrawerActivity.class);
        intent.putExtra("txtTitle", title);

        if(memoryPoint.save())Toast.makeText(this,"添加成功", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
    }
}
