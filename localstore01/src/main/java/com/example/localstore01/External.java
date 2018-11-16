package com.example.localstore01;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class External extends AppCompatActivity {
    Button button1, button2;
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external);

        editText = findViewById(R.id.et_external);
        textView = findViewById(R.id.tv_external);




    }
}
