package com.ohdroid.zbmaster.application.data

import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.application.rxbus.RxBus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import java.util.*

/**
 * Created by ohdroid on 2016/4/6.
 *
 * 网络请求的基类，请求的统一属性封装，以及解析JSON为指定对象
 */
abstract class BaseBusiness<T> {

    companion object {
        @JvmStatic val METHOD_GET = "GET"
        @JvmStatic val METHOD_POST = "POST"
    }

    /**
     * retrofit框架初始化
     */
    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(QiniuApi.QINIU_URL_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /**
     *用于通知网络数据结果
     */
    var rxBus: RxBus? = null

    /**
     *请求参数
     */
    var requestParams: MutableMap<String, String> = HashMap()


    /**
     * 查询指定列表数据
     * 请勿调用super.findList()
     */
    open fun findList(): Observable<MutableList<T>> {
        throw UnsupportedOperationException("this method is not implemented")
    }

    /**
     * 添加数据
     * 执行结果请使用rxbus返回
     */
    open fun addItem(t: T) {
        throw UnsupportedOperationException("this method is not implemented")
    }

    /**
     * 通过id号删除指定数据
     * 执行结果请使用rxbus返回
     */
    open fun removeItemById(id: Int) {
        throw UnsupportedOperationException("this method is not implemented")
    }


}