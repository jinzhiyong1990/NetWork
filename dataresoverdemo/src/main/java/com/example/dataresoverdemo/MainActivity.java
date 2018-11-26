package com.example.dataresoverdemo;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button insert, delete, update, query;
    private EditText nameEdt, ageEdt, idEdt;
    private RadioGroup radioGroup;
    private String gender;
    private ListView listView;


    //ContentResolver 用来做数据访问的
    ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取ContentResolver对象
        resolver = getContentResolver();
        /**
         * resolver.query()
         * resolver.insert()
         * resolver.delete()
         * resolver.update()
         *
         */

//        resolver.query();

        nameEdt = findViewById(R.id.name);
        ageEdt = findViewById(R.id.age);
        idEdt = findViewById(R.id.et_id);
        radioGroup = findViewById(R.id.rg_gender);
        listView = findViewById(R.id.result);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male) {

                    gender = "男";

                } else {
                    gender = "女";

                }
            }
        });


    }

    public void operate(View view) {
        Uri uri = Uri.parse("content://com.imooc.zhiyongprovider");
        String name = nameEdt.getText().toString();
        String age = ageEdt.getText().toString();
        String _id = idEdt.getText().toString();

        switch (view.getId()) {
            case R.id.bt_add:

        /*
        参数1: URI对象(Uniform Resource Identifer,统一资源定位符)
        参数2:
        参数3:
        参数4:
        参数5:
         */
                ContentValues values = new ContentValues();
                // key = 对应的表的列名
                // value =
                values.put("name", name);
                values.put("age", age);
                values.put("gender", gender);
                Uri uri2 = resolver.insert(uri, values);
                //解析uri
                long id = ContentUris.parseId(uri2);
                Toast.makeText(this, "添加成功, 新学生的学号是: " + id, Toast.LENGTH_SHORT).show();
                break;


            case R.id.bt_look:

                //参数1: Uri对象
                //参数2: 查询的列, 如果是null就是查询所有的列
                //参数3: 条件
                //参数4: 条件值
                //参数5: 排序
                Cursor c = resolver.query(uri, null, null, null, null);

                /**
                 * 参数1: 环境上下文
                 * 参数2: 每一个学员信息对象所显示的样式布局
                 * 参数3: 数据源
                 * 参数4: 查询结果中所有列的列名, 搭配参数5
                 * 参数5: 数据未来所要加载到的对应控件的id数组
                 * 参数6: 数据刷新
                 */
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item,
                        c, new String[]{"_id", "name", "age", "gender"},
                        new int[]{R.id.id_txt, R.id.name_txt, R.id.age_txt, R.id.gender_txt},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);


                listView.setAdapter(adapter);
                break;

            case R.id.bt_delete:
                int result = resolver.delete(uri, "_id=?", new String[]{_id});
                if (result > 0) {
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
                }


                break;

        }
    }

}
