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
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.BaseApplication
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.tencent.connect.share.QQShare
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.jetbrains.anko.support.v4.find

/**
 * Created by ohdroid on 2016/4/7.
 */
class AreaFaceDetailFragment : BaseFragment(), View.OnClickListener {


    val ivFaceDetail: SimpleDraweeView by lazy { find<SimpleDraweeView>(R.id.iv_face_detail) }

    val bgLayout: View by lazy { find<View>(R.id.fragment_face_detail) }
    val btnShare: Button by lazy { find<Button>(R.id.btn_share) }

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
        faceInfo = arguments.getSerializable("faceInfo") as FaceInfo
        //从传递过来的数据中获取动态图的api，目前功能比较少所以就写在这里，如果功能功能增多那么写在presenter层中
        faceInfo.faceUrl = QiniuApi.getDynamicURL(faceInfo.faceUrl, faceInfo.fileSize)

        return inflater?.inflate(R.layout.fragment_area_face_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        if (TextUtils.isEmpty(faceInfo.faceUrl)) {
            return
        }
        bgLayout.setOnClickListener { }//消耗点击事件

        //设置为自动播放
        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(faceInfo.faceUrl))
                .setTapToRetryEnabled(true)//点击重播
                .setAutoPlayAnimations(true)//自动播放
                .build()
        ivFaceDetail.controller = controller
        //        ivFaceDetail.setImageURI(Uri.parse(imageUrl), null)

        btnShare.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_share -> shareImage()
        }
    }

    fun shareImage() {
        //tencent 分享
        val params: Bundle = Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        if (!TextUtils.isEmpty(faceInfo.faceTitle)) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, faceInfo.faceTitle)
        } else {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "来自\"不无聊\"分享")
        }
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, faceInfo.faceUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, QiniuApi.LOGO_IMAGE_URL);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "ZBMaster");

        val application: BaseApplication = activity.application as BaseApplication
        val mTencent: Tencent = application.applicationComponent.tencentManager()
        mTencent.shareToQQ(activity, params, object : IUiListener {
            override fun onComplete(p0: Any?) {
                println(p0)
            }

            override fun onCancel() {
            }

            override fun onError(p0: UiError?) {
                println(p0?.errorMessage)
            }

        });
    }

}