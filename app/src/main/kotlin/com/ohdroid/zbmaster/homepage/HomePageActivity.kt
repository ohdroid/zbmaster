package com.ohdroid.zbmaster.homepage

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity
import com.ohdroid.zbmaster.homepage.areaface.view.fragment.AreaFaceFragment
import com.ohdroid.zbmaster.homepage.areamovie.view.AreaMovieFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor

/**
 * Created by ohdroid on 2016/4/4.
 */
class HomePageActivity : BaseActivity() {

    val menuProof: View by lazy { find<View>(R.id.menu_spoof) }
    val menuMovie: View by lazy { find<View>(R.id.menu_movie) }

    var mCurrentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        menuProof.setOnClickListener(menuOnClickListener)
        menuMovie.setOnClickListener(menuOnClickListener)
        showSpoofPage()
    }

    override fun onStart() {
        super.onStart()
    }

    val menuOnClickListener = View.OnClickListener {
        when (it.id) {
            R.id.menu_movie -> {
                showMoviePage()
            }
            R.id.menu_spoof -> {
                showSpoofPage()
            }
        }
    }

    fun setMenuCheckd(tvName: TextView, ivImage: ImageView, imageId: Int) {
        resetBottomMenu()
        tvName.textColor = Color.BLACK
        ivImage.setImageDrawable(resources.getDrawable(imageId))
    }

    fun resetBottomMenu() {
        menuProof.find<TextView>(R.id.menu_spoof_tv).textColor = Color.GRAY
        menuProof.find<ImageView>(R.id.menu_spoof_iv).setImageDrawable(resources.getDrawable(R.mipmap.sproof_icon))
        menuMovie.find<TextView>(R.id.menu_movie_tv).textColor = Color.GRAY
        menuMovie.find<ImageView>(R.id.menu_movie_iv).setImageDrawable(resources.getDrawable(R.mipmap.movie_icon))
    }

    fun showMoviePage() {
        setMenuCheckd(menuMovie.find<TextView>(R.id.menu_movie_tv), menuMovie.find<ImageView>(R.id.menu_movie_iv), R.mipmap.ic_launcher)
        if (null != mCurrentFragment) {
            hideFragment(mCurrentFragment!!)
        }
        mCurrentFragment = AreaMovieFragment.launch(supportFragmentManager, R.id.fragment_container)
    }

    fun showSpoofPage() {
        setMenuCheckd(menuProof.find<TextView>(R.id.menu_spoof_tv), menuProof.find<ImageView>(R.id.menu_spoof_iv), R.mipmap.ic_launcher)
        if (null != mCurrentFragment) {
            hideFragment(mCurrentFragment!!)
        }
        mCurrentFragment = AreaFaceFragment.launch(supportFragmentManager, R.id.fragment_container)
    }

    fun hideFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .hide(fragment)
                .commit()
    }
}