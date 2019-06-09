package com.example.memorydrawerpro;

import org.litepal.crud.LitePalSupport;

public class MemoryPoint extends LitePalSupport {
    private String title;
    private String content;
    private int isMarked = 0;
//    private int amount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMarked(){
        return isMarked;
    }

    public void setMarked(int marked) {
        isMarked = marked;
    }

//    public int getAmount() {
//        return amount;
//    }
//
//    public void setAmount(int amount) {
//        this.amount = amount;
//    }

    public String toString(){
        return this.getTitle() +"_"+ this.getContent() +"_\n";
    }
}
