package com.ohdroid.zbmaster.homepage.areaface.data.model

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.ohdroid.zbmaster.application.BaseApplication
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.application.di.exannotation.ForApplication
import com.tencent.connect.share.QQShare
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ohdroid on 2016/4/22.
 */
@Singleton
class ShareHelper {
    var tencentManger: Tencent? = null


    @Inject
    constructor(tencent: Tencent) {
        this.tencentManger = tencent
        println("$tencent<<==============")
    }

    /**
     * 以网页链接形式分享
     */
    fun share2QQByWeb(title: String? = "来自\"不无聊\"分享", url: String, iUiListener: IUiListener, activity: Activity) {
        //tencent 分享
        val params: Bundle = Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title)
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, QiniuApi.LOGO_IMAGE_URL);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "不无聊");

        println("is tencent get =========>>$tencentManger")
        tencentManger?.shareToQQ(activity, params, iUiListener)
    }

    /**
     * 直接分享图片到QQ
     */
    fun share2QQ() {

    }
}