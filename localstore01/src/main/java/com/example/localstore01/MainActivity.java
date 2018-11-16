package com.example.localstore01;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button1, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        


    }

    private void initView() {
        button1 = findViewById(R.id.sharepreference);
        button2 = findViewById(R.id.outstore);
        button3 = findViewById(R.id.instore);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sharepreference:
                Intent intent = new Intent(MainActivity.this, ShareActivity.class);
                startActivity(intent);
                break;

            case R.id.outstore:
                Intent intent1 = new Intent(MainActivity.this, Externalwenti_____.class);
                startActivity(intent1);
                break;

            case R.id.instore:
                Intent intent2 = new Intent(MainActivity.this, Internal.class);
                startActivity(intent2);
                break;
        }

    }
}
