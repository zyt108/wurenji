package com.md.basePlatform.common;

import com.github.pagehelper.PageInfo;
import java.util.List;

/**
 * 分页结果封装类
 * <p>
 * 用于封装分页查询的返回结果，包含总记录数、当前页数据等。
 * </p>
 *
 * @param <T> 数据类型
 */
public class PageResult<T> {

    /**
     * 总记录数
     */
    private long total;

    /**
     * 当前页数据列表
     */
    private List<T> list;

    /**
     * 当前页码
     */
    private int pageNum;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;

    /**
     * 是否有上一页
     */
    private boolean hasPreviousPage;

    public PageResult() {}

    /**
     * 根据 PageInfo 创建分页结果
     *
     * @param pageInfo PageInfo 对象
     * @param <T> 数据类型
     * @return 分页结果对象
     */
    public static <T> PageResult<T> of(PageInfo<T> pageInfo) {
        PageResult<T> result = new PageResult<>();
        result.total = pageInfo.getTotal();
        result.list = pageInfo.getList();
        result.pageNum = pageInfo.getPageNum();
        result.pageSize = pageInfo.getPageSize();
        result.pages = pageInfo.getPages();
        result.hasNextPage = pageInfo.isHasNextPage();
        result.hasPreviousPage = pageInfo.isHasPreviousPage();
        return result;
    }

    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }

    public List<T> getList() { return list; }
    public void setList(List<T> list) { this.list = list; }

    public int getPageNum() { return pageNum; }
    public void setPageNum(int pageNum) { this.pageNum = pageNum; }

    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public boolean isHasNextPage() { return hasNextPage; }
    public void setHasNextPage(boolean hasNextPage) { this.hasNextPage = hasNextPage; }

    public boolean isHasPreviousPage() { return hasPreviousPage; }
    public void setHasPreviousPage(boolean hasPreviousPage) { this.hasPreviousPage = hasPreviousPage; }
}
