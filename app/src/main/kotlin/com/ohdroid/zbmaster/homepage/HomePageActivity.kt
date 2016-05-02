package com.ohdroid.zbmaster.homepage

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.ex.showToast
import com.ohdroid.zbmaster.base.view.BaseActivity
import com.ohdroid.zbmaster.homepage.areaface.view.fragment.AreaFaceFragment
import com.ohdroid.zbmaster.homepage.areamovie.view.fragment.AreaMovieFragment
import com.ohdroid.zbmaster.utils.SPUtils
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor

/**
 * Created by ohdroid on 2016/4/4.
 */
class HomePageActivity : BaseActivity() {

    val menuProof: View by lazy { find<View>(R.id.menu_spoof) }
    val menuMovie: View by lazy { find<View>(R.id.menu_movie) }
    val toolbar: Toolbar by lazy { find<Toolbar>(R.id.tool_bar) }

    var mCurrentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE)

        menuProof.setOnClickListener(menuOnClickListener)
        menuMovie.setOnClickListener(menuOnClickListener)
        showSpoofPage()
    }

    override fun onStart() {
        super.onStart()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.home_page_menu, menu)

        //根据当前设置设置menu的文字提示
        val isFastMode: Boolean = SPUtils.get(this, SPUtils.FAST_MODE_KEY, true) as Boolean//默认是节流模式
        if (isFastMode) {
            toolbar.menu.getItem(0).title = resources.getString(R.string.menu_quality_mode)
        } else {
            toolbar.menu.getItem(0).title = resources.getString(R.string.menu_fast_mode)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_fast_mode -> switchFastMode()
        }

        return true
    }

    fun switchFastMode() {
        val isFastMode: Boolean = SPUtils.get(this, SPUtils.FAST_MODE_KEY, true) as Boolean//默认是节流模式
        if (isFastMode) {
            //切换到装逼模式
            SPUtils.put(this, SPUtils.FAST_MODE_KEY, false)//设置为装逼模式，也就是加载高清图模式
            toolbar.menu.getItem(0).title = resources.getString(R.string.menu_fast_mode)
            showToast(resources.getString(R.string.hint_quality_mode))
        } else {
            SPUtils.put(this, SPUtils.FAST_MODE_KEY, true)//设置为节流模式
            toolbar.menu.getItem(0).title = resources.getString(R.string.menu_quality_mode)
            showToast(resources.getString(R.string.hint_fast_mode))
        }

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

    fun setMenuChecked(tvName: TextView, ivImage: ImageView, imageId: Int) {
        resetBottomMenu()
        tvName.textColor = Color.BLACK
        ivImage.setImageDrawable(resources.getDrawable(imageId))
    }

    fun resetBottomMenu() {
        menuProof.find<TextView>(R.id.menu_spoof_tv).textColor = Color.GRAY
        menuProof.find<ImageView>(R.id.menu_spoof_iv).setImageDrawable(resources.getDrawable(R.mipmap.bottom_menu_eye))
        menuMovie.find<TextView>(R.id.menu_movie_tv).textColor = Color.GRAY
        menuMovie.find<ImageView>(R.id.menu_movie_iv).setImageDrawable(resources.getDrawable(R.mipmap.bottom_menu_post))
    }

    fun showMoviePage() {
        setMenuChecked(menuMovie.find<TextView>(R.id.menu_movie_tv), menuMovie.find<ImageView>(R.id.menu_movie_iv), R.mipmap.bottom_menu_pressed_post)
        if (null != mCurrentFragment) {
            hideFragment(mCurrentFragment!!)
        }
        mCurrentFragment = AreaMovieFragment.launch(supportFragmentManager, R.id.fragment_container)
    }

    fun showSpoofPage() {
        setMenuChecked(menuProof.find<TextView>(R.id.menu_spoof_tv), menuProof.find<ImageView>(R.id.menu_spoof_iv), R.mipmap.bottom_menu_pressed_eye)
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