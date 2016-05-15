package com.ohdroid.zbmaster.homepage

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.rxbinding.widget.RxRadioGroup
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.ex.showToast
import com.ohdroid.zbmaster.application.rxbus.RxBus
import com.ohdroid.zbmaster.base.view.BaseActivity
import com.ohdroid.zbmaster.homepage.areaface.view.fragment.AreaFaceFragment
import com.ohdroid.zbmaster.homepage.areamovie.event.ListScrollEvent
import com.ohdroid.zbmaster.homepage.areamovie.view.fragment.AreaMovieFragment
import com.ohdroid.zbmaster.utils.SPUtils
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textColor
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by ohdroid on 2016/4/4.
 */
class HomePageActivity : BaseActivity() {

    val menuProof: View by lazy { find<View>(R.id.menu_spoof) }
    val menuMovie: View by lazy { find<View>(R.id.menu_movie) }
    val toolbar: Toolbar by lazy { find<Toolbar>(R.id.tool_bar) }
    val mFabModelSwitch: FloatingActionButton by lazy { find<FloatingActionButton>(R.id.fab_mode_switch) }

    var mCurrentFragment: Fragment? = null
    lateinit var rxBus: RxBus
        @Inject set

    val subscriptions: CompositeSubscription = CompositeSubscription()
    var isFabShowing: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        setSupportActionBar(toolbar);
        component.inject(this)

        initFab()

        toolbar.setTitleTextColor(Color.WHITE)

        menuProof.setOnClickListener(menuOnClickListener)
        menuMovie.setOnClickListener(menuOnClickListener)
        showSpoofPage()
    }

    fun initFab() {
        //根据当前设置设置menu的文字提示
        val isFastMode: Boolean = SPUtils.get(this, SPUtils.FAST_MODE_KEY, true) as Boolean//默认是节流模式
        if (isFastMode) {
            mFabModelSwitch.setImageResource(R.mipmap.mode_ds)
        } else {
            mFabModelSwitch.setImageResource(R.mipmap.mode_money)
        }

        mFabModelSwitch.onClick { toggleMode() }

        subscriptions.add(rxBus.toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it is ListScrollEvent) {
                        //滚动事件
                        if (it.direction > 0) {
                            if (isFabShowing) {
                                //隐藏fab
                                hideFab()
                                isFabShowing = false
                            }
                        } else {
                            //显示fab
                            if (!isFabShowing) {
                                showFab()
                                isFabShowing = true
                            }
                        }
                    }
                }))
    }

    var showSet: AnimatorSet? = null

    fun showFab() {
        if (showSet != null && showSet!!.isRunning) {
            return
        }
        val showAnimX: ObjectAnimator = ObjectAnimator.ofFloat(mFabModelSwitch, "scaleX", 0f, 1f)
        val showAnimY: ObjectAnimator = ObjectAnimator.ofFloat(mFabModelSwitch, "scaleY", 0f, 1f)
        showSet = AnimatorSet()
        showSet!!.playTogether(showAnimX, showAnimY)
        showSet!!.duration = 200
        showSet!!.start()
    }

    var hideSet: AnimatorSet? = null


    fun hideFab() {
        if (hideSet != null && hideSet!!.isRunning) {
            return
        }

        val hideAnimX: ObjectAnimator = ObjectAnimator.ofFloat(mFabModelSwitch, "scaleX", 1f, 0f)
        val hideAnimY: ObjectAnimator = ObjectAnimator.ofFloat(mFabModelSwitch, "scaleY", 1f, 0f)
        hideAnimX.setAutoCancel(true)
        hideAnimY.setAutoCancel(true)

        hideSet = AnimatorSet()
        hideSet!!.duration = 200
        hideSet!!.interpolator = DecelerateInterpolator()
        hideSet!!.playTogether(hideAnimX, hideAnimY)
        hideSet!!.start()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        subscriptions.clear()
        super.onDestroy()
    }

    /**
     * gif图加载模式切换，
     */
    fun toggleMode() {

        val isFastMode: Boolean = SPUtils.get(this, SPUtils.FAST_MODE_KEY, true) as Boolean//默认是节流模式
        if (isFastMode) {
            //切换到装逼模式
            SPUtils.put(this, SPUtils.FAST_MODE_KEY, false)//设置为装逼模式，也就是加载高清图模式
            mFabModelSwitch.setImageResource(R.mipmap.mode_money)
            showToast(resources.getString(R.string.hint_quality_mode))
        } else {
            SPUtils.put(this, SPUtils.FAST_MODE_KEY, true)//设置为节流模式
            showToast(resources.getString(R.string.hint_fast_mode))
            mFabModelSwitch.setImageResource(R.mipmap.mode_ds)
        }
    }


    val menuOnClickListener = View.OnClickListener {
        when (it.id) {
            R.id.menu_movie -> showMoviePage()
            R.id.menu_spoof -> showSpoofPage()
        }
    }


    fun setMenuChecked(tvName: TextView, ivImage: ImageView, imageId: Int, hintView: TextView) {
        resetBottomMenu()
        tvName.textColor = Color.WHITE
        ivImage.setImageDrawable(resources.getDrawable(imageId))
        hintView.setBackgroundColor(resources.getColor(R.color.colorPrimary))
    }

    fun resetBottomMenu() {

        menuProof.find<TextView>(R.id.menu_spoof_tv).textColor = resources.getColor(R.color.bottom_menu_text_normal_color)
        menuProof.find<ImageView>(R.id.menu_spoof_iv).setImageDrawable(resources.getDrawable(R.mipmap.bottom_menu_eye))
        find<TextView>(R.id.hint_spoof_view).setBackgroundColor(R.color.bottom_menu_bg_color)

        menuMovie.find<TextView>(R.id.menu_movie_tv).textColor = resources.getColor(R.color.bottom_menu_text_normal_color)
        menuMovie.find<ImageView>(R.id.menu_movie_iv).setImageDrawable(resources.getDrawable(R.mipmap.bottom_menu_post))
        find<TextView>(R.id.hint_post_view).setBackgroundColor(R.color.bottom_menu_bg_color)

    }

    fun showMoviePage() {
        setMenuChecked(menuMovie.find<TextView>(R.id.menu_movie_tv), menuMovie.find<ImageView>(R.id.menu_movie_iv), R.mipmap.bottom_menu_pressed_post, find<TextView>(R.id.hint_post_view))
        if (null != mCurrentFragment) {
            hideFragment(mCurrentFragment!!)
        }
        mCurrentFragment = AreaMovieFragment.launch(supportFragmentManager, R.id.fragment_container)
    }

    fun showSpoofPage() {
        setMenuChecked(menuProof.find<TextView>(R.id.menu_spoof_tv), menuProof.find<ImageView>(R.id.menu_spoof_iv), R.mipmap.bottom_menu_pressed_eye, find<TextView>(R.id.hint_spoof_view))
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

    var countNum: Int = 1
    override fun onBackPressed() {
        if (countNum > 0) {
            countNum--
            showToast(resources.getString(R.string.hint_quit_app))
            menuMovie.postDelayed({
                countNum++
            }, 2000)
        } else {
            //TODO 添加activity manager 来完全退出应用
            this.finish()
        }
    }
}