package com.ohdroid.zbmaster.homepage

import android.content.ContentResolver
import android.graphics.drawable.Animatable
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.image.ImageInfo
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity
import org.jetbrains.anko.find

/**
 * Created by ohdroid on 2016/4/28.
 */
class SplashActivity : BaseActivity() {

    //    val mSimpleDraweeView by lazy { find<SimpleDraweeView>(R.id.star_view) }

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