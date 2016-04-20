package com.ohdroid.zbmaster.homepage.areaface.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.homepage.areaface.view.fragment.AreaFaceDetailFragment

/**
 * Created by ohdroid on 2016/4/10.
 */
class AreaFaceDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_face_detail)

        val faceInfo: FaceInfo = intent.getSerializableExtra("faceInfo") as FaceInfo
        AreaFaceDetailFragment.launch(supportFragmentManager, R.id.detail_fragment_container, faceInfo)
    }

    companion object {
        fun launch(context: Context, faceInfo: FaceInfo) {
            val intent: Intent = Intent(context, AreaFaceDetailActivity::class.java)
            intent.putExtra("faceInfo", faceInfo)
            context.startActivity(intent)
        }
    }


}