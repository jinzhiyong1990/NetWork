package com.example.database;

import android.Manifest;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText nameEdt, ageEdt, idEdt;
    private RadioGroup genderGroup;
    private String genderStr = "男";
    private ListView stuList;
    private RadioButton maleButton;
    SQLiteDatabase db1;


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
                //rawQuery(); 查询
                //execSQL(); 增,删,改, 创建


                //String sql = "insert into info_tb(name,age,gender) values('"+nameStr+"', "+ageStr+", '"+genderStr+"')";

                String sql = "insert into info_tb(name,age,gender) values(?,?,?)";
                db1.execSQL(sql, new String[]{nameStr, ageStr, genderStr});
                Toast.makeText(this,"插入成功", Toast.LENGTH_SHORT).show();

                break;

            case R.id.select_btn:
                //select * from where id = ?
                String sql2 = "select * from info_tb";


                //查询选中
                if (!idStr.equals("")) {
                    sql2 += " where _id=" + idStr;
                }
                //查询结果
                Cursor c = db1.rawQuery(sql2, null);

                /*
                参数3: 数据源
                 */
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item, c,
                        new String[]{"_id", "name", "age", "gender"},
                        new int[]{R.id.id_item, R.id.name_item, R.id.age_item, R.id.gender_item},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);//将页面更新
                stuList.setAdapter(adapter);
                Toast.makeText(this,"查询成功", Toast.LENGTH_SHORT).show();

                break;

            case R.id.delete_btn:
                String sql3 = "delete from info_tb where _id=?";
                Toast.makeText(this,"删除成功", Toast.LENGTH_SHORT).show();

                db1.execSQL(sql3, new String[]{idStr});


                break;

            case R.id.update_btn:
                String sql4 = "update info_tb set name=?, age=?, gender=? where _id=?";
                db1.execSQL(sql4, new String[]{nameStr, ageStr, genderStr, idStr});
                Toast.makeText(this,"修改成功", Toast.LENGTH_SHORT).show();

                break;
        }

        //回归初始状态
        nameEdt.setText("");
        ageEdt.setText("");
        idEdt.setText("");
        maleButton.setChecked(true);
    }
}
