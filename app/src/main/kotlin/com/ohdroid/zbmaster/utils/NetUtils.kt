package com.ohdroid.zbmaster.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by ohdroid on 2016/4/22.
 * 网络工具
 */
class NetUtils {
    companion object {

        /**
         * 判断网络是否连接
         *
         * @param context
         * @return
         */
        @JvmStatic fun isConnected(context: Context): Boolean {

            context.getSystemService(Context.CONNECTIVITY_SERVICE) ?: return false
            val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info: NetworkInfo? = connectivity.activeNetworkInfo;
            //            if (info.isConnected) {
            //                if (info.state == NetworkInfo.State.CONNECTED) {
            //                    return true;
            //                }
            //            }
            if (null != info) {
                println("网络连接判断~~~~~~${info.isAvailable}")
                return info.isAvailable
            }
            return false;
        }

        /**
         * 判断是否是wifi连接
         */
        @JvmStatic fun isWifi(context: Context): Boolean {
            val cm: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm?.activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI;

        }

        /**
         * 打开网络设置界面
         */
        @JvmStatic fun openSetting(activity: Activity) {
            val intent: Intent = Intent("/");
            val cm: ComponentName = ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.component = cm;
            intent.action = "android.intent.action.VIEW";
            activity.startActivityForResult(intent, 0);
        }

    }
}