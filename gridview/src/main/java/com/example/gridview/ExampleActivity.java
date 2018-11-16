package com.example.gridview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class ExampleActivity extends AppCompatActivity {
    private GridView mgridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example1);
        mgridView = findViewById(R.id.gridview);

//        设置内容
        List<String> strList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            strList.add("慕课网" + i);

        }

        //数组适配器
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (this, R.layout.item_gridview1, strList);

        mgridView.setAdapter(arrayAdapter);




    }
}
