package com.ohdroid.zbmaster.application

/**
 * Created by ohdroid on 2016/3/5.
 */
interface BasePresenter<T : BaseView> {

    /**
     * 绑定view
     */
    fun attachView(view: T)

    /**
     * 分离view
     */
    fun detachView()
}