package com.fy.companylibrary.entity;

import java.util.List;

/**
 * 作者： ${PING} on 2018/8/17.
 */
public class PageBean<T> {

    private int dataCount;
    private int currentPage;
    private List<T> data;


    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
