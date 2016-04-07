package com.ohdroid.zbmaster.homepage.areaface.view

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseFragment
import org.jetbrains.anko.support.v4.find

/**
 * Created by ohdroid on 2016/4/7.
 */
class AreaFaceDetailFragment : BaseFragment() {

    val ivFaceDetail: SimpleDraweeView by lazy { find<SimpleDraweeView>(R.id.iv_face_detail) }

    val bgLayout: View by lazy { find<View>(R.id.fragment_face_detail) }

    companion object {
        fun launch(manager: FragmentManager, containerId: Int, imageUrl: String) {
            val fragment: AreaFaceDetailFragment = AreaFaceDetailFragment()
            val args: Bundle = Bundle()
            args.putString("imageUrl", imageUrl)
            fragment.arguments = args

            manager.beginTransaction()
                    .replace(containerId, fragment)
                    .addToBackStack(null)//加入到回退栈
                    .commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_area_face_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val imageUrl = arguments.get("imageUrl") as String
        if (TextUtils.isEmpty(imageUrl)) {
            return
        }
        bgLayout.setOnClickListener { }//消耗点击事件

        //设置为自动播放
        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(imageUrl))
                .setTapToRetryEnabled(true)//点击重播
                .setAutoPlayAnimations(true)//自动播放
                .build()
        ivFaceDetail.controller = controller
        //        ivFaceDetail.setImageURI(Uri.parse(imageUrl), null)
    }

}