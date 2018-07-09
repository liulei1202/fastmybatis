package com.gitee.fastmybatis.core.support;

import com.gitee.fastmybatis.core.query.param.PageSortParam;

/**
 * 支持easyui表格参数
 * @author tanghc
 */
public class EasyuiDatagridParam extends PageSortParam {
    private int page;
    private int rows;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        this.setPageIndex(page);
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
        this.setPageSize(rows);
    }

}