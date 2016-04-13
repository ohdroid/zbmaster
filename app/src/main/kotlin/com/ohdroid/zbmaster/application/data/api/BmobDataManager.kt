package com.ohdroid.zbmaster.application.data.api

import android.content.Context
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

    fun <T : BmobObject> addItem(context: Context, t: T, saveListener: SaveListener?) {
        t.save(context, saveListener)
    }

    fun <T : BmobObject> findItemList(context: Context, requestParameters: MutableMap<String, String>?, findListener: FindListener<T>) {
        val query = BmobQuery<T>()
        //        query.addQueryKeys("skip", requestParameters.get("skip") ?: 0)
        query.findObjects(context, findListener)
    }
}