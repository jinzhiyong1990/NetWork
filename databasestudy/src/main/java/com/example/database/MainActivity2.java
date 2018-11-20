package com.example.database;

import android.content.ContentValues;
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

public class MainActivity2 extends AppCompatActivity {
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

        //添加操作

        /**
         * 参数1: 环境
         * 参数2: 数据库名称
         * 情况1: 如果只有一个数据库名称, 那么这个数据库的位置会是在私有目录中
         * 情况2: 如果有路径, 那么数据库位置就在指定的路径下
         * 参数3: 游标, 通过什么样的游标操作数据, 可以写null, 会给默认选择
         * 参数4: 版本号
         */

        String path = Environment.getExternalStorageDirectory() + "/text5.db";
        SQLiteOpenHelper helper = new SQLiteOpenHelper(this, path, null, 2 ) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                //创建
                Toast.makeText(MainActivity2.this, "数据库创建", Toast.LENGTH_SHORT).show();

                //如果数据库不存在, 则会调用onCrete方法, 那么我们就可以将表的创建工作放在这里完成

//                        String sql = "create table text_tb(_id integer primary key autoincrement," +
//                                "name varchar(20)," +
//                                "age integer)";
//
//                        String sql2 = "create table user (_id integer PRIMARY KEY AUTOINCREMENT NOT NULL," +
//                                "name varchar,age int)";
                String sql3 = "CREATE TABLE info_tb(_id integer primary key autoincrement,name varchar(30) not null, age integer, gender varchar(2) not null);";
//                        db.execSQL(sql2);
                db.execSQL(sql3);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                //升级
                Toast.makeText(MainActivity2.this, "数据库升级", Toast.LENGTH_SHORT).show();
            }
        };

        //用于获取数据库对象
        //1.数据库存在, 则直接打开数据库
        //2. 数据库不存在, 则调用创建数据库的方法, 再打开数据库
        //3. 数据库存在, 但是版本号升高了, 则调用数据库升级方法
        db1 = helper.getReadableDatabase();
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
                /*
                在SqliteDatabase类下, 提供四个方法
                insert, delete, update, query
                都不需要写sql语句
                 */
                //参数1: 要操作的数据库表的名称
                //参数2: 可以为空的列. 如果第三个参数是null或者里面没有数据
//                       那么我们的sql语句就会变成insert into info_tb() values(), 在语法上就是错误的
                //       此时通过参数3指定一个可以为空的列, 语句就变成了insert into info_tb(可空列) values(null)
                //参数3:
                ContentValues values = new ContentValues();
                values.put("name", nameStr);
                values.put("age", ageStr);
                values.put("gender", genderStr);

                db1.insert("info_tb", null, values);
                Toast.makeText(this,"添加成功", Toast.LENGTH_SHORT).show();

                break;

            case R.id.select_btn:
                /*
                参数2: 你所要查询的列. name age gender. 如果想要查询所有就传入null或者{"*"}
                参数3: 条件 列1=?
                参数4: 对应的 ? 里面要传入的值
                参数5: 分组
                聚合函数: 用于统计数据的函数 count(列名) 统计总数
                select count(*) from info_tb
                分别统计出各个年龄阶段的人数
                select count(*), age from info_tb group by age

                参数6: 当group by 对数据进行分组后, 可以通过having去除不符合条件的组

                select count(*), age from info_tb group by age having age>23
                参数7: 排序
                select count(*), age from info_tb group by age having age>23 order by age desc
                 */
                Cursor c = db1.query("info_tb", null, null, null, null, null, null);

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
                /*
                参数1: 表名
                参数2: where条件
                参数3: 条件值
                 */

                int count = db1.delete("info_tb", "_id=?", new String[]{idStr});

                if (count > 0) {
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.update_btn:
                /*
                参数1: 表名
                参数2: ContentValues
                参数3: where条件
                参数4: 条件值
                 */
                ContentValues values1 = new ContentValues();
                //update info_tb set 列1 =xx, 列2=xx where 列名 = 值
                values1.put("name", nameStr);
                values1.put("age", ageStr);
                values1.put("gender", genderStr);


                int count2 = db1.update("info_tb", values1, "_id=?", new String[]{idStr});

                if (count2 > 0) {
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }

                break;
        }

        //回归初始状态
        nameEdt.setText("");
        ageEdt.setText("");
        idEdt.setText("");
        maleButton.setChecked(true);
    }
}
