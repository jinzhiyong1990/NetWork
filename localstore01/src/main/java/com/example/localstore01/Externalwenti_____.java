package com.example.localstore01;

import android.Manifest;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Externalwenti_____ extends AppCompatActivity implements View.OnClickListener {
    Button button1, button2;
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external);

        editText = findViewById(R.id.et_external);
        textView = findViewById(R.id.tv_external);
        /**
         * 申请动态权限
         *
         */


        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }



        editText.setOnClickListener(this);
        textView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //获取文件位置
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/22222.txt";
        Log.e("TAG", path);


        switch (v.getId()) {
            case R.id.bt_external_store:
                File f = new File(path);
                try {
                    if (!f.exists()) {
                        f.createNewFile();
                    }

                    //参数1: 文件路径. 参数2: 是否追加
                    FileOutputStream fileOutputStream = new FileOutputStream(path, true);
                    String str = editText.getText().toString();
                    fileOutputStream.write(str.getBytes());

                } catch (IOException e) {
                    e.printStackTrace();
                }
//
                break;

            case R.id.bt_external_read:
                try {
                    FileInputStream fis = new FileInputStream(path);
                    byte[] b = new byte[1024];
                    int len = fis.read(b);
                    String str2 = new String(b, 0, len);
                    textView.setText(str2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
