package com.ohdroid.zbmaster.homepage.areaface.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity

/**
 * Created by ohdroid on 2016/4/4.
 */
class AreaFaceActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_face)
    }

    companion object {
        fun launch(context: Context) {
            val intent: Intent = Intent(context, AreaFaceActivity::class.java);
            context.startActivity(intent)
        }
    }
}