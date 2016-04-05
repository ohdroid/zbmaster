package com.ohdroid.zbmaster.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.homepage.areaface.AreaFaceActivity
import org.jetbrains.anko.support.v4.find

/**
 * Created by ohdroid on 2016/4/4.
 */
class HomePageFragment : BaseFragment(), View.OnClickListener {

    val btnZb: Button by lazy { find<Button>(R.id.btn_area_zb) }
    val btnMovie: Button by lazy { find<Button>(R.id.btn_area_movie) }
    val btnFace: Button by lazy { find<Button>(R.id.btn_area_face) }
    val btnChat: Button by lazy { find<Button>(R.id.btn_area_chat) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home_page, container, false)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnZb.setOnClickListener(this)
        btnMovie.setOnClickListener(this)
        btnFace.setOnClickListener(this)
        btnChat.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_area_face -> showAreaFace()
        }
    }

    fun showAreaFace(): Unit {
        AreaFaceActivity.launch(activity)
    }

}