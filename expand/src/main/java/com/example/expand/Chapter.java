package com.example.expand;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组条目
 */

public class Chapter {
    private int id;
    private String name;

    //一对多的关系Chapter a& com.example.expand.ChapterItem
    private List<ChapterItem> children = new ArrayList<>();

    public Chapter(){

    }

    public Chapter(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addChild(ChapterItem chapterItem){
        chapterItem.setPid(getId());
        children.add(chapterItem);

    }

    public void addChild(int cid, String name) {
        ChapterItem chapterItem = new ChapterItem(cid, name);
        chapterItem.setPid(getId());
        children.add(chapterItem);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChapterItem> getChildren() {
        return children;
    }

    public void setChildren(List<ChapterItem> children) {
        this.children = children;
    }
}
