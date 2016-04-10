package com.ohdroid.zbmaster.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.homepage.areaface.view.AreaFaceActivity
import org.jetbrains.anko.support.v4.find

/**
 * Created by ohdroid on 2016/4/4.
 */
class HomePageFragment : BaseFragment() {

    val btnZb: Button by lazy { find<Button>(R.id.btn_area_zb) }
    val btnMovie: Button by lazy { find<Button>(R.id.btn_area_movie) }
    val btnFace: Button by lazy { find<Button>(R.id.btn_area_face) }
    val btnChat: Button by lazy { find<Button>(R.id.btn_area_chat) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home_page, container, false)

    }

    val onClickListener: View.OnClickListener = View.OnClickListener { v ->
        when (v?.id) {
            R.id.btn_area_face -> showAreaFace()
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnZb.setOnClickListener(onClickListener)
        btnMovie.setOnClickListener(onClickListener)
        btnFace.setOnClickListener(onClickListener)
        btnChat.setOnClickListener(onClickListener)
    }

    fun showAreaFace(): Unit {
        AreaFaceActivity.launch(activity)
    }

}