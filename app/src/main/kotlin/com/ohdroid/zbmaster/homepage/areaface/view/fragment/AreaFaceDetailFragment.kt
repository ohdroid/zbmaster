package com.ohdroid.zbmaster.homepage.areaface.view.fragment

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.application.ex.showToast
import com.ohdroid.zbmaster.application.view.progress.CircleProgress
import com.ohdroid.zbmaster.application.view.progress.ImageViewProgressController
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.homepage.areaface.data.model.ShareHelper
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.utils.NetUtils
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import org.jetbrains.anko.support.v4.find
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
        faceInfo.faceUrl = QiniuApi.getDynamicURL(faceInfo.faceUrl, faceInfo.fileSize, NetUtils.isWifi(context))


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
            }

            override fun onCancel() {
            }

            override fun onError(p0: UiError?) {
                this@AreaFaceDetailFragment.showToast(p0?.errorMessage ?: "share failed")
            }

        }
        shareHelper?.share2QQByWeb(faceInfo.faceTitle, faceInfo.faceUrl, listener, activity)
    }


}