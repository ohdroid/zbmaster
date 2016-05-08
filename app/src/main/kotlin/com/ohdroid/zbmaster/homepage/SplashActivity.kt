package com.ohdroid.zbmaster.homepage

import android.os.Bundle
import android.widget.ImageView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity
import org.jetbrains.anko.find

/**
 * Created by ohdroid on 2016/4/28.
 */
class SplashActivity : BaseActivity() {

    //    val mSimpleDraweeView by lazy { find<SimpleDraweeView>(R.id.star_view) }
    val splashView: ImageView by lazy { find<ImageView>(R.id.iv_splash) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //        val controller = Fresco.newDraweeControllerBuilder()
        //                .setUri(uri)
        //                .setControllerListener(controllerListener)
        //                .setAutoPlayAnimations(true)
        //                .build();
        //        mSimpleDraweeView.controller = controller;
    }
}