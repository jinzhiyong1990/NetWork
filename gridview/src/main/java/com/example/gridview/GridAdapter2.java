package com.example.gridview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class GridAdapter2 extends BaseAdapter {

    private Context context;
    private List<AppInfo> appInfoList;

    public GridAdapter2(Context context, List<AppInfo> appInfoList) {
        this.context = context;
        this.appInfoList = appInfoList;
    }

    @Override
    public int getCount() {
        return appInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return appInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_gridview2, null);
            viewHolder = new ViewHolder();
            viewHolder.img_appIcon = convertView.findViewById(R.id.img_appIcon);
            viewHolder.tv_appName = convertView.findViewById(R.id.tv_appName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AppInfo appInfo = appInfoList.get(position);
        viewHolder.img_appIcon.setImageDrawable(appInfo.getAppIcon());
        viewHolder.tv_appName.setText(appInfo.getAppName() + " " + appInfo.getVersionName());



        return convertView;
    }

    public class ViewHolder {
        ImageView img_appIcon;
        TextView tv_appName;
    }
}
