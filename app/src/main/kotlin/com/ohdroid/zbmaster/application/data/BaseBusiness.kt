package com.ohdroid.zbmaster.application.data

import com.ohdroid.zbmaster.application.rxbus.RxBus
import com.ohdroid.zbmaster.utils.UrlUtils
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
            .baseUrl(UrlUtils.getInstance().baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    var listener: BaseResultListener<T>? = null

    var rxBus: RxBus? = null

    /**
     * 开始的位置,用于分页查询时，过滤掉的数据项
     */
    var startIndex: Int = 0

    /**
     *请求参数
     */
    var requestParams: MutableMap<String, String> = HashMap()


    open fun execute(method: String, listener: BaseResultListener<T>?) {
        this.listener = listener
        when (method) {
            METHOD_GET -> byGet()
            METHOD_POST -> byPost()
        }
    }

    /**
     * 返回observable的执行方法
     *
     */
    abstract fun execute(method: String? = METHOD_GET): Observable<MutableList<T>>

    /**
     * GET 请求数据
     */
    abstract fun byGet()

    /**
     * POST 请求数据
     */
    abstract fun byPost()


    /**
     * 数据结果监听器
     */
    interface BaseResultListener<T> {
        fun onSuccess(results: MutableList<T>?)
        fun onFailed(state: Int, errorMessage: String?)
    }


}