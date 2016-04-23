package com.ohdroid.zbmaster.application.data

import android.app.Activity
import android.os.Bundle
import com.facebook.binaryresource.FileBinaryResource
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory
import com.facebook.imagepipeline.core.ImagePipelineFactory
import com.facebook.imagepipeline.request.ImageRequest
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.utils.FileUtils
import com.ohdroid.zbmaster.utils.SDCardUtils
import com.tencent.connect.share.QQShare
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ohdroid on 2016/4/22.
 *
 * 分享帮助类
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
    fun share2QQ(imageUrl: String, activity: Activity, baseListener: IUiListener) {

        val imageRequest = ImageRequest.fromUri(imageUrl);
        val cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(imageRequest);
        val resource = ImagePipelineFactory.getInstance()
                .mainDiskStorageCache.getResource(cacheKey);
        val file = (resource as FileBinaryResource ).file;
        if (!file.exists()) {
            baseListener.onError(UiError(-1, activity.resources.getString(R.string.hint_share_failed), "error"))
            return
        }
        var fileName = file.absolutePath
        var tFileName = SDCardUtils.SD_PIC + File.separator + "temp.gif"
        Observable
                .create(Observable.OnSubscribe<Boolean> { //先拷贝文件到临时文件,然后分享,这里修改成fresco的pipeline形式，后期该
                    //                    it.onNext(SDCardUtils.copyFile(fileName, tFileName))
                    it.onNext(FileUtils.copyFile(fileName, tFileName, true))
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it) {
                        println("拷贝成功")
                        //拷贝成功，然后进行分享到QQ
                        val params = Bundle();
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, tFileName);
                        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.resources.getString(R.string.app_name));
                        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
                        tencentManger?.shareToQQ(activity, params, baseListener);
                    } else {
                        baseListener.onError(UiError(-1, activity.resources.getString(R.string.hint_share_failed), "error"))
                    }
                }, {
                    baseListener.onError(UiError(-1, it.message, "error"))
                })

    }
}