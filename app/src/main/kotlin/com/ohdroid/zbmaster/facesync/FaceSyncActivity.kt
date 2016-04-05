package com.ohdroid.zbmaster.facesync

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity
import com.ohdroid.zbmaster.facesync.view.FaceSyncView

/**
 * Created by ohdroid on 2016/3/22.
 */
class FaceSyncActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_sync)
    }

    companion object {
        fun launch(context: Context) {
            val intent: Intent = Intent(context, FaceSyncActivity::class.java)
            context.startActivity(intent)
        }
    }
}