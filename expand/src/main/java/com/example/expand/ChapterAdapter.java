package com.example.expand;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChapterAdapter extends BaseExpandableListAdapter {

    private List<Chapter> mDate;
    private LayoutInflater layoutInflater;
    private Context context;

    public ChapterAdapter(Context context, List<Chapter> mDate) {
        this.context = context;
        this.mDate = mDate;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getGroupCount() {
        return mDate.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDate.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDate.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDate.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //稳定id影响: 进入选择模式的时候, 还有进入缓存的时候
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //决定group的样子

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_parent_chapter,
                    parent, false);
            parentViewHolder = new ParentViewHolder();
            parentViewHolder.textView = convertView.findViewById(R.id.id_tv_name);
            parentViewHolder.imageView = convertView.findViewById(R.id.id_iv_indicator);
            convertView.setTag(parentViewHolder);

        } else {
            parentViewHolder = (ParentViewHolder) convertView.getTag();
        }
        Chapter chapter = mDate.get(groupPosition);

        parentViewHolder.textView.setText(chapter.getName());

        //设置图片
        parentViewHolder.imageView.setImageResource(R.drawable.group_indicator);
        //设置图片状态
        parentViewHolder.imageView.setSelected(isExpanded);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_child_chapter, parent
                    , false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.textView = convertView.findViewById(R.id.id_tv_name);
            convertView.setTag(childViewHolder);

        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        ChapterItem chapterItem = mDate.get(groupPosition).getChildren().get(childPosition);
        childViewHolder.textView.setText(chapterItem.getName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public static class ParentViewHolder{
        TextView textView;
        ImageView imageView;

    }
    public static class ChildViewHolder{
        TextView textView;

    }



}
