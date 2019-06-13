package com.lt.personal_stadiumbookingsystem.util;

/**
 * @作者: LinTan
 * @日期: 2019/5/13 15:20
 * @版本: 1.0
 * @描述: //分页工具类。
 * 源址: https://www.cnblogs.com/bignew/p/6594734.html
 * 1.0: Initial Commit
 */

public class PageUtil {
    private int mTotalCount;//总数
    private int mPageSize = 20;//每页显示数量
    private int mCurrentPageNum;//当前页
    private int mPageCount;//总页数
    private int mPrePage;//上一页
    private int mNextPage;//下一页
    private boolean mHasPrePage;//是否有上一页
    private boolean mHasNextPage;//是否有下一页
    private int mFirstPage;//第一页
    private int mLastPage;//最后一页
    private int mCurrentCount;//当前从第多少条数据开始显示

    public PageUtil() {
    }

    public PageUtil(int totalCount, int pageNum) {
        mTotalCount = totalCount;
        mCurrentPageNum = pageNum;
        mPageCount = (int) Math.ceil(1.0 * totalCount / mPageSize);
        mCurrentCount = (mPageCount - 1) * mPageSize;
        if (pageNum > 1) {  //判断是不是第一页   
            /*--不是第一页 则有上一页 ，也有第一页--*/
            mHasPrePage = true;
            mPrePage = pageNum - 1;
            mFirstPage = 1;
        }

        if (pageNum < mPageCount) {//判断是不是最后一页   
            /*--不是最后一页 则有上一页 ，也有最后一页--*/
            mHasNextPage = true;
            mNextPage = pageNum + 1;
            mLastPage = mPageCount;
        }
    }

    public int getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(int totalCount) {
        mTotalCount = totalCount;
        mPageCount = (int) Math.ceil(1.0 * totalCount / mPageSize);
        if (mCurrentPageNum < 1) {
            mCurrentPageNum = 1;
        }
        mCurrentCount = (mCurrentPageNum - 1) * mPageSize;
        if (mCurrentPageNum > 1) {  //判断是不是第一页
            /*--不是第一页 则有上一页 ，也有第一页--*/
            mHasPrePage = true;
            mPrePage = mCurrentPageNum - 1;
            mFirstPage = 1;
        }

        if (mCurrentPageNum < mPageCount) {//判断是不是最后一页
            /*--不是最后一页 则有上一页 ，也有最后一页--*/
            mHasNextPage = true;
            mNextPage = mCurrentPageNum + 1;
            mLastPage = mPageCount;
        }
    }

    public int getPageSize() {
        return mPageSize;
    }

    public void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }

    public int getPrePage() {
        return mPrePage;
    }

    public void setPrePage(int prePage) {
        mPrePage = prePage;
    }

    public int getNextPage() {
        return mNextPage;
    }

    public void setNextPage(int nextPage) {
        mNextPage = nextPage;
    }

    public boolean isHasPrePage() {
        return mHasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        mHasPrePage = hasPrePage;
    }

    public boolean isHasNextPage() {
        return mHasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        mHasNextPage = hasNextPage;
    }

    public int getFirstPage() {
        return mFirstPage;
    }

    public void setFirstPage(int firstPage) {
        mFirstPage = firstPage;
    }

    public int getLastPage() {
        return mLastPage;
    }

    public void setLastPage(int lastPage) {
        mLastPage = lastPage;
    }

    public int getCurrentPageNum() {
        return mCurrentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        mCurrentPageNum = currentPageNum;
    }

    public int getPageCount() {
        return mPageCount;
    }

    public void setPageCount(int pageCount) {
        mPageCount = pageCount;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public void setCurrentCount(int currentCount) {
        mCurrentCount = currentCount;
    }
}