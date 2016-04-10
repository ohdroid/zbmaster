package com.ohdroid.zbmaster.homepage

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity
import com.ohdroid.zbmaster.homepage.areaface.view.AreaFaceFragment
import org.jetbrains.anko.find

/**
 * Created by ohdroid on 2016/4/4.
 */
class HomePageActivity : BaseActivity() {

    val menuProof: TextView by lazy { find<TextView>(R.id.menu_spoof) }
    val menuMovie: TextView by lazy { find<TextView>(R.id.menu_movie) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        menuProof.setOnClickListener(menuOnClickListener)
        menuMovie.setOnClickListener(menuOnClickListener)
    }

    val menuOnClickListener = View.OnClickListener {
        when (it.id) {
            R.id.menu_movie -> showMoviePage()
            R.id.menu_spoof -> showSpoofPage()
        }
    }

    fun setCheckedMenuItem(checkMenu: TextView) {

    }

    fun showMoviePage() {
    }

    fun showSpoofPage() {
        println("$supportFragmentManager:${R.id.fragment_container}")
        AreaFaceFragment.launch(supportFragmentManager, R.id.fragment_container)

    }
}