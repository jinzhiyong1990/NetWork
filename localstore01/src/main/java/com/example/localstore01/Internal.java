package com.example.localstore01;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Internal extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    EditText editText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);

        initView();
    }

    private void initView() {
        textView = findViewById(R.id.tv_internal);
        editText = findViewById(R.id.et_internal);
        textView.setOnClickListener(this);
        editText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_internal_store:
                File f = new File(getFilesDir(), "nihao.txt");
                try {
                    if (!f.exists()) {
                        f.createNewFile();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(f);
                    fileOutputStream.write(editText.getText().toString().getBytes());
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.bt_internal_read:

                break;
        }

    }
}
