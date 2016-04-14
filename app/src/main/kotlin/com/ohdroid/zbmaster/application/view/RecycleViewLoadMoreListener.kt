package com.ohdroid.zbmaster.application.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by ohdroid on 2016/4/11.
 */
abstract class RecycleViewLoadMoreListener : RecyclerView.OnScrollListener() {
    /**
     * 当加载更多事件激活时候，调用此方法
     */
    abstract fun onLoadMoreData()

    /**
     * 是否正在加载更多数据
     */
    var isLoadingMore = false
    /**
     * 是否触发加载更多数据事件
     */
    var canLoadingMore = true

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        //加载更多的原理是:判断当前显示的内容是否是显示到了recycleview底部
        val linearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager//由于gridLayout是继承LinearLayout的所以这里可以强转成LinearLayout
        val itemCount = linearLayoutManager.itemCount
        val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

        if (!canLoadingMore || isLoadingMore || dy <= 0) {
            //不能加载更多，加载中，向上滑，都直接返回
            return
        }

        if (lastVisibleItemPosition >= itemCount - 1) {
            isLoadingMore = true
            //我这里只做了事件触发，而是否记载更多就具体类来实现了
            onLoadMoreData()
        }
    }
}