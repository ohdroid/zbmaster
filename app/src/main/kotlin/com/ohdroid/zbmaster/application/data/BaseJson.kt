package com.ohdroid.zbmaster.application.data

import org.json.JSONObject

/**
 * Created by ohdroid on 2016/4/6.
 *
 * JSON数据基本解析类
 */
abstract class BaseJson {

    /**
     * 返回状态
     */
    var state: Int = 0;
    /**
     * 返回的消息
     */
    var message: String = "";


}