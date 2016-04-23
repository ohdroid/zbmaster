package com.ohdroid.zbmaster.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by ohdroid on 2016/4/22.
 */
class SPUtils {

    companion object {

        /**
         * 是否是节流模式
         */
        val FAST_MODE_KEY = "fastMode"

        /**
         * 保存在手机里面的文件名
         */
        val FILE_NAME = "zb_share_data";

        /**
         * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
         *
         * @param context
         * @param key
         * @param object
         */
        fun put(context: Context, key: String, obj: Any) {

            val sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
            val editor: SharedPreferences.Editor = sp.edit();

            when (obj) {
                is String -> editor.putString(key, obj)
                is Int -> editor.putInt(key, obj)
                is Boolean -> editor.putBoolean(key, obj)
                is Float -> editor.putFloat(key, obj)
                is Long -> editor.putLong(key, obj)
                else -> editor.putString(key, obj.toString())
            }
            editor.apply()
        }

        /**
         * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
         *
         * @param context
         * @param key
         * @param defaultObject
         * @return
         */
        fun get(context: Context, key: String, defaultValue: Any): Any? {
            val sp: SharedPreferences = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
            when (defaultValue) {
                is Long -> return sp.getLong(key, defaultValue)
                is Float -> return sp.getFloat(key, defaultValue)
                is String -> return sp.getString(key, defaultValue)
                is Int -> return sp.getInt(key, defaultValue)
                is Boolean -> return sp.getBoolean(key, defaultValue)
            }

            return defaultValue
        }


        /**
         * 移除某个key值已经对应的值
         * @param context
         * @param key
         */
        fun remove(context: Context, key: String) {
            val sp: SharedPreferences = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
            val editor: SharedPreferences.Editor = sp.edit();
            editor.remove(key);
            editor.apply()

        }

        /**
         * 清除所有数据
         */
        fun clear(context: Context) {
            val sp: SharedPreferences = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
            val editor = sp.edit();
            editor.clear();
            editor.apply()
        }

        /**
         * 查看是否包括某个键
         */
        fun contains(context: Context, key: String): Boolean {
            val sp: SharedPreferences = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
            return sp.contains(key);
        }


        /**
         * 返回所有的键值对
         *
         * @param context
         * @return
         */
        fun getAll(context: Context): Map<String, *> {
            val sp: SharedPreferences = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
            return sp.all;
        }


    }


}