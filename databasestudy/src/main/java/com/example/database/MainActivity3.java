package com.example.database;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {
    private EditText nameEdt, ageEdt, idEdt;
    private RadioGroup genderGroup;
    private String genderStr = "男";
    private ListView stuList;
    private RadioButton maleButton;
    SQLiteDatabase db1;
    private StudentDao_data_access_object dao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdt = findViewById(R.id.name_et);
        ageEdt = findViewById(R.id.age_et);
        idEdt = findViewById(R.id.id_et);
        genderGroup = findViewById(R.id.gender_gp);
        maleButton = findViewById(R.id.male);
        stuList = findViewById(R.id.stu_list);

        dao = new StudentDao_data_access_object(this);

        //选项切换事件监听
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male) {
                    //保存男性
                    genderStr = "男";

                } else {
                    //保存女性
                    genderStr = "女";
                }
            }
        });
    }

    public void operate(View view) {
        String idStr = idEdt.getText().toString();
        String nameStr = nameEdt.getText().toString();
        String ageStr = ageEdt.getText().toString();

        switch (view.getId()) {

            case R.id.insert_btn:

                Student stu = new Student(nameStr, Integer.parseInt(ageStr), genderStr);
                dao.addStudent(stu);
                Toast.makeText(this,"添加成功", Toast.LENGTH_SHORT).show();
                break;

            case R.id.select_btn:
                String key = "", value = "";

                if (!nameStr.equals("")) {
                    value = nameStr;
                    key = "name";
                } else if (!ageStr.equals("")) {
                    value = ageStr;
                    key = "age";
                } else if (!idStr.equals("")) {
                    value = idStr;
                    key = "_id";
                }
                Cursor cursor;
                if (key.equals("")) {
                    cursor = dao.getStudent();
                } else {
                    cursor = dao.getStudent(key, value);
                }

                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                        this, R.layout.item, cursor,
                        new String[]{"_id", "name", "age", "gender"},
                        new int[]{R.id.id_item, R.id.name_item, R.id.age_item, R.id.gender_item}
                );
                stuList.setAdapter(simpleCursorAdapter);

                Toast.makeText(this,"查询成功", Toast.LENGTH_SHORT).show();
                break;

            case R.id.delete_btn:

                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();


                break;

            case R.id.update_btn:

                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();


                break;
        }

        //回归初始状态
        nameEdt.setText("");
        ageEdt.setText("");
        idEdt.setText("");
        maleButton.setChecked(true);
    }
}
