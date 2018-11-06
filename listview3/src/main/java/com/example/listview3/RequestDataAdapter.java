package com.example.listview3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RequestDataAdapter extends BaseAdapter {

    private List<LessonInfo> mLessonInfos = new ArrayList<>();
    private Context mcContext;
    public RequestDataAdapter(Context context, List<LessonInfo> infos) {
        mLessonInfos = infos;
        mcContext = context;

    }

//    列表数目, 从上面的参数来的
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mLessonInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//      ViewHolder 相当于缓存
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mcContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_app_list_view, null);

            viewHolder.mAppIconImageView = convertView.findViewById(R.id.icon_image_view);
            viewHolder.mAppNameTextView = convertView.findViewById(R.id.app_name_text_view);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        Name就放上去了
        viewHolder.mAppNameTextView.setText(mLessonInfos.get(position).getmName());



        return convertView;
    }


    public class ViewHolder{
        // TODO: 2018/11/5 放视图的
        public ImageView mAppIconImageView;
        public TextView mAppNameTextView;
    }
}
