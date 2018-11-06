package com.example.listview2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list_view);

        ListView listView = findViewById(R.id.app_list_view);

        listView.setAdapter(new AppListAdapter(getAllAppInfo()));

//        头部视图
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View headerView = layoutInflater.inflate(R.layout.header_list_view, null);
        listView.addHeaderView(headerView);

    }

    /**
     * 获取所有的应用信息
     * @return
     */

    private List<ResolveInfo> getAllAppInfo(){
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return getPackageManager().queryIntentActivities(intent, 0);
    }


    public class AppListAdapter extends BaseAdapter {

        //这就是要填充的数据列表
        List<ResolveInfo> myAppInfos;

        public AppListAdapter(List<ResolveInfo> myAppInfos) {
            this.myAppInfos = myAppInfos;
        }


        // 有多少条数据
        @Override
        public int getCount() {

            return myAppInfos.size();
        }

        // 返回当前position位置的这一条
        @Override
        public Object getItem(int position) {
            return myAppInfos.get(position);
        }

        // 返回当前position位置的这一条id
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 处理 view -- data 适配的过程(填充数据)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // 缓存
            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                //解析layout, 变成一个view
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = layoutInflater.inflate(R.layout.item_app_list_view, null);

                viewHolder.mAppIconImageView = convertView.findViewById(R.id.icon_image_view);
                viewHolder.mAppNameTextView= convertView.findViewById(R.id.app_name_text_view);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }


//            读出来imageview 和 textview



//                                 (这里面的get position 得到就是"QQ", "微信", 之类的)
            viewHolder.mAppNameTextView.setText(myAppInfos.get(position).activityInfo.loadLabel(getPackageManager()));

//            获取图标
            viewHolder.mAppIconImageView.setImageDrawable(myAppInfos.get(position).activityInfo.loadIcon(getPackageManager()));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    拿出包名
                    String packageName = myAppInfos.get(position).activityInfo.packageName;

//                    应用的主activity类
                    String classString = myAppInfos.get(position).activityInfo.name;

//                    构造主键
                    ComponentName componentName = new ComponentName(packageName, classString);

                    final Intent intent = new Intent();
                    intent.setComponent(componentName);
                    startActivity(intent);

                }
            });

            return convertView;
        }

        public class ViewHolder{
            public ImageView mAppIconImageView;
            public TextView mAppNameTextView;
        }
    }
}
