package com.ohdroid.zbmaster.homepage.areaface.view.fragment

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.facebook.binaryresource.BinaryResource
import com.facebook.binaryresource.FileBinaryResource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory
import com.facebook.imagepipeline.core.ImagePipelineFactory
import com.facebook.imagepipeline.request.ImageRequest
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.application.ex.showToast
import com.ohdroid.zbmaster.application.view.progress.CircleProgress
import com.ohdroid.zbmaster.application.view.progress.ImageViewProgressController
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.application.data.ShareHelper
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.utils.NetUtils
import com.ohdroid.zbmaster.utils.SDCardUtils
import com.ohdroid.zbmaster.utils.SPUtils
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import org.jetbrains.anko.support.v4.find
import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

/**
 * Created by ohdroid on 2016/4/7.
 */
class AreaFaceDetailFragment : BaseFragment(), View.OnClickListener {


    val ivFaceDetail: SimpleDraweeView by lazy { find<SimpleDraweeView>(R.id.iv_face_detail) }
    val mLoadingView: CircleProgress by lazy { find<CircleProgress>(R.id.loading_view) }

    val bgLayout: View by lazy { find<View>(R.id.fragment_face_detail) }
    val btnShare: Button by lazy { find<Button>(R.id.btn_share) }

    var shareHelper: ShareHelper? = null
        @Inject set


    companion object {
        fun launch(manager: FragmentManager, containerId: Int, faceInfo: FaceInfo) {
            val fragment: AreaFaceDetailFragment = AreaFaceDetailFragment()
            val args: Bundle = Bundle()
            args.putSerializable("faceInfo", faceInfo)
            fragment.arguments = args

            manager.beginTransaction()
                    .replace(containerId, fragment)
                    .commit()
        }
    }

    lateinit var faceInfo: FaceInfo

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        component.inject(this)

        faceInfo = arguments.getSerializable("faceInfo") as FaceInfo
        //从传递过来的数据中获取动态图的api，目前功能比较少所以就写在这里，如果功能功能增多那么写在presenter层中
        val isFastMode = SPUtils.get(context, SPUtils.FAST_MODE_KEY, true) as Boolean

        faceInfo.faceUrl = QiniuApi.getDynamicURL(faceInfo.faceUrl, faceInfo.fileSize, isFastMode)


        return inflater?.inflate(R.layout.fragment_area_face_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        if (TextUtils.isEmpty(faceInfo.faceUrl)) {
            return
        }
        bgLayout.setOnClickListener { }//消耗点击事件
        btnShare.setOnClickListener(this)

        mLoadingView.startAnim()

        //图像加载进度控制
        val builder: GenericDraweeHierarchyBuilder = GenericDraweeHierarchyBuilder(activity.resources)
        builder.progressBarImage = object : ImageViewProgressController() {
            override fun onLevelChange(level: Int): Boolean {
                if (10000 == level) {
                    mLoadingView.postDelayed({
                        println("-------hide loading view---------")
                        mLoadingView.stopAnim()
                        mLoadingView.visibility = View.GONE
                    }, 3600)
                }
                return super.onLevelChange(level)
            }
        }
        builder.actualImageScaleType = ScalingUtils.ScaleType.FIT_CENTER
        ivFaceDetail.hierarchy = builder.build()
        //设置为自动播放
        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(faceInfo.faceUrl))
                .setTapToRetryEnabled(true)//点击重播
                .setAutoPlayAnimations(true)//自动播放
                .build()
        ivFaceDetail.controller = controller

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_share -> shareImage()
        }
    }

    fun shareImage() {
        val listener = object : IUiListener {
            override fun onComplete(p0: Any?) {
                showToast("share complete")
            }

            override fun onCancel() {

            }

            override fun onError(p0: UiError?) {
                this@AreaFaceDetailFragment.showToast(p0?.errorMessage ?: "share failed")
            }

        }

        //网页形式分享
        //                shareHelper?.share2QQByWeb(faceInfo.faceTitle, faceInfo.faceUrl, listener, activity)
        //直接分享图片
        shareHelper?.share2QQ(faceInfo.faceUrl, activity, listener)
        //        val imageRequest = ImageRequest.fromUri(faceInfo.faceUrl);
        //        val cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(imageRequest);
        //        val resource = ImagePipelineFactory.getInstance()
        //                .mainDiskStorageCache.getResource(cacheKey);
        //        val file = (resource as FileBinaryResource ).file;
        //        if (!file.exists()) {
        //            showToast("暂时不能分享")
        //            return
        //        }
        //        var fileName = file.absolutePath
        //        var tFileName = SDCardUtils.SD_PIC + File.separator + "test.gif"
        //        Observable
        //                .create(Observable.OnSubscribe<Boolean> {
        //                    it.onNext(SDCardUtils.copyFile(fileName, tFileName))
        //                    it.onNext(true)
        //                })
        //                .subscribeOn(Schedulers.io())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .subscribe({
        //                    if (it) {
        //                        shareHelper?.share2QQ(tFileName, activity, listener)
        //                    }
        //                }, {
        //                    showToast(it.message ?: "分享失败")
        //                })

    }


}