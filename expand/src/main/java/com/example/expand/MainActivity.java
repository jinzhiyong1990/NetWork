package com.example.expand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

//ExpandableListView:
//可以扩展收缩的ListView

public class MainActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private BaseExpandableListAdapter mAdapter;
    private List<Chapter> mDate = new ArrayList<>();

    private static final String TAG = "immoc-ex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        
        initEvent();
    }

    private void initEvent() {

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.d(TAG, "onChildClick groupPosition: " + groupPosition +
                ", childPosition" + childPosition);
                return false;
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d(TAG, "onGroupClick groupPosition: " + groupPosition);

                return false;
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Log.d(TAG, "onGroupCollapse groupPosition: " + groupPosition);

            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Log.d(TAG, "onGroupExpand groupPosition: " + groupPosition);

            }
        });
    }

    private void initView() {
        expandableListView = findViewById(R.id.expandableListView2);
        mDate.clear();
        mDate.addAll(ChapterLab.generateMockDate());

        mAdapter = new ChapterAdapter(this, mDate);
        expandableListView.setAdapter(mAdapter);

    }

}
