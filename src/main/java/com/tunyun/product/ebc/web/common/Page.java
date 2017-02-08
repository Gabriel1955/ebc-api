package com.tunyun.product.ebc.web.common;

import java.util.List;

/**
 * Created by shiliang on 2016/12/27.
 */
public class Page {
    private long total = 0;
    private int pageSize = 20;
    private int pageNo = 1;
    private long pages = 0;
    private List list;

    public Page(int pageSize, int pageNo, long total, List list) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
        if (total == 0) {
            pages = 0;
        } else {
            if (total % pageSize == 0) {
                pages = total / pageSize;
            } else {
                pages = (long) (total / pageSize + 1);
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("pageNo=").append(this.pageNo)
                .append(",pageSize=").append(this.pageSize)
                .append(",pages=").append(this.pages)
                .append(",total=").append(this.total);
        return sb.toString();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}