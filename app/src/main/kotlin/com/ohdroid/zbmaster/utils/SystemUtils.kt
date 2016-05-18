package com.ohdroid.zbmaster.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * Created by ohdroid on 2016/5/18.
 */
class SystemUtils {
    companion object {
        @JvmField val PERMISSION_REQEUST_CODE = 12345

        /**
         * 检测当前权限是否拥有
         */
        @JvmStatic fun checkPermission(activity: Activity): Boolean {

            val storagePremisstionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (PackageManager.PERMISSION_GRANTED == storagePremisstionCheck) {
                return true
            } else {
                val stringArray = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(activity, stringArray, PERMISSION_REQEUST_CODE);
                return false
            }
        }
    }
}