package com.example.localstore01;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShareActivity extends AppCompatActivity {

    EditText accEdt, pwdEdt;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        accEdt = findViewById(R.id.et_account);
        pwdEdt = findViewById(R.id.et_password);
        button = findViewById(R.id.bt_login);

        /**
         * SharePreference的读取
         */
        //1. 获取SharePreference
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);

        //2. 根据KEY 获取内容(参数1: key   参数2: 当对应key不存在时, 返回参数2的内容作为默认值)
        String accStr = sharedPreferences.getString("account", "");
        String pwdStr = sharedPreferences.getString("password", "");

        accEdt.setText(accStr);
        pwdEdt.setText(pwdStr);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1. 获取两个输入框的内容
                String account = accEdt.getText().toString();
                String password = pwdEdt.getText().toString();


                /**
                 * SharePreference的写
                 */
                //2. 验证(admin 123)
                if (account.equals("admin") && password.equals("123")) {
                    //2.1(成功) 储存信息到SharePreference
                    //2.1.1 获取SharePreference

                    //参数1: 文件名(如果没有会自动创建)[其实是一个xml文件]
                    //参数2: 操作模式

                    SharedPreferences sharedPreferences =
                            getSharedPreferences("myshare",MODE_PRIVATE);


                    //2.1.2 获取Editor对象
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    //2.1.3 存储信息
                    //key-value形式的参数
                    editor.putString("account", account);
                    editor.putString("password", password);


                    //2.1.4 执行提交操作
                    editor.commit();


                    //正常来讲需要跳转到其他界面
                    Toast.makeText(ShareActivity.this,
                            "登录成功", Toast.LENGTH_SHORT).show();

                } else {
                    //2.2(失败) 验证失败, 提示用户
                    Toast.makeText(ShareActivity.this, "账号或者密码错误",
                            Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}
