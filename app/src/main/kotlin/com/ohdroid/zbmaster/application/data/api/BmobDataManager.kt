package com.ohdroid.zbmaster.application.data.api

import android.content.Context
import android.text.TextUtils
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener

/**
 * Created by ohdroid on 2016/4/12.
 */
class BmobDataManager {
    companion object {
        private val manager: BmobDataManager = BmobDataManager()
        @JvmStatic fun getInstance(): BmobDataManager {
            return manager
        }
    }

    /**
     * bmob 添加数据
     */
    fun <T : BmobObject> addItem(context: Context, t: T, saveListener: SaveListener?) {
        t.save(context, saveListener)
    }

    /**
     * bmob 查找列表
     */
    fun <T : BmobObject> findItemList(context: Context, requestParameters: MutableMap<String, String>?, findListener: FindListener<T>) {
        val query = BmobQuery<T>()
        //排序,默认按照时间先后
        var order = requestParameters?.get("order")
        if (TextUtils.isEmpty(order)) {
            order = "-createdAt"
        }
        query.order(order)

        //分页查询
        val skip = requestParameters?.get("skip")?.toInt()
        query.setSkip(skip ?: 0)
        val limit = requestParameters?.get("limit")?.toInt()
        query.setLimit(limit ?: 10)

        //固定条件下的数据
        val equalToKey = requestParameters?.get("equalToKey")
        val equalToValue = requestParameters?.get("equalToValue")
        if (!TextUtils.isEmpty(equalToKey) && !TextUtils.isEmpty(equalToValue)) {
            query.addWhereEqualTo(equalToKey, equalToValue)
        }

        //包含查询
        val includeInfo = requestParameters?.get("includeInfo")
        if (!TextUtils.isEmpty(includeInfo)) {
            query.include(includeInfo)
        }

        query.findObjects(context, findListener)
    }
}