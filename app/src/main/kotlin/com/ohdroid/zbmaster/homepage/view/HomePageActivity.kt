package com.ohdroid.zbmaster.homepage.view

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.about.view.AboutActivity
import com.ohdroid.zbmaster.application.di.exannotation.PerActivity
import com.ohdroid.zbmaster.application.ex.showToast
import com.ohdroid.zbmaster.application.rxbus.RxBus
import com.ohdroid.zbmaster.base.view.BaseActivity
import com.ohdroid.zbmaster.homepage.areaface.view.fragment.AreaFaceFragment
import com.ohdroid.zbmaster.homepage.areamovie.event.ListScrollEvent
import com.ohdroid.zbmaster.homepage.areamovie.view.fragment.AreaMovieFragment
import com.ohdroid.zbmaster.homepage.presenter.HomePagePresenter
import com.ohdroid.zbmaster.login.model.AccountInfo
import com.ohdroid.zbmaster.utils.SPUtils
import com.ohdroid.zbmaster.utils.SystemUtils
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textColor
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by ohdroid on 2016/4/4.
 */
class HomePageActivity : BaseActivity(), HomePageView {

    val menuProof: View by lazy { find<View>(R.id.menu_spoof) }
    val menuMovie: View by lazy { find<View>(R.id.menu_movie) }
    //    val toolbar: Toolbar by lazy { find<Toolbar>(R.id.tool_bar) }
    val btnLogin: View by lazy { find<View>(R.id.layout_quit_login) }
    val btnAbout: View by lazy { find<View>(R.id.layout_about_us) }
    val btnContactUs: View by lazy { find<View>(R.id.layout_contact_us) }
    val leftMenu: View by lazy { find<View>(R.id.left_menu) }
    val mUserPhoto: SimpleDraweeView by lazy { find<SimpleDraweeView>(R.id.user_photo) }
    val mUserName: TextView by lazy { find<TextView>(R.id.tv_name) }
    val mFabModelSwitch: FloatingActionButton by lazy { find<FloatingActionButton>(R.id.fab_mode_switch) }

    var mCurrentFragment: Fragment? = null
    lateinit var rxBus: RxBus
        @Inject set

    lateinit var presetner: HomePagePresenter
        @PerActivity @Inject set

    val subscriptions: CompositeSubscription = CompositeSubscription()
    var isFabShowing: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
//        setSupportActionBar(toolbar);
        component.inject(this)
        presetner.attachView(this)
        initFab()

//        toolbar.setTitleTextColor(Color.WHITE)

        menuProof.setOnClickListener(menuOnClickListener)
        menuMovie.setOnClickListener(menuOnClickListener)
//        btnLogin.setOnClickListener(menuOnClickListener)
//        btnAbout.setOnClickListener(menuOnClickListener)

        btnLogin.setOnTouchListener(onTouchListener)
        btnAbout.setOnTouchListener(onTouchListener)
        btnContactUs.setOnTouchListener(onTouchListener)

        showSpoofPage()
        checkIsLogin()
    }

    val onTouchListener: View.OnTouchListener = View.OnTouchListener { v, event ->
        if (event?.action == MotionEvent.ACTION_DOWN) {
            when (v?.id) {
                R.id.layout_about_us -> changeLeftMenuColor(v, R.id.about_us_tv, R.color.colorPrimary, R.id.iv_about_us, R.mipmap.about_us_pressed)
                R.id.layout_contact_us -> changeLeftMenuColor(v, R.id.tv_contact_us, R.color.colorPrimary, R.id.iv_contact_us, R.mipmap.contact_us_pressed)
                R.id.layout_quit_login -> changeLeftMenuColor(v, R.id.tv_quit_login, R.color.colorPrimary, R.id.iv_quit_login, R.mipmap.quit_login_pressed)
            }
        }

        if (event?.action == MotionEvent.ACTION_UP) {
            when (v?.id) {
                R.id.layout_about_us -> {
                    changeLeftMenuColor(v, R.id.about_us_tv, R.color.left_menu_text_color, R.id.iv_about_us, R.mipmap.about_us)
                    showAboutPage()
                }
                R.id.layout_contact_us -> {
                    changeLeftMenuColor(v, R.id.tv_contact_us, R.color.left_menu_text_color, R.id.iv_contact_us, R.mipmap.contact_us)
                    showAboutPage()
                }
                R.id.layout_quit_login -> {
                    changeLeftMenuColor(v, R.id.tv_quit_login, R.color.left_menu_text_color, R.id.iv_quit_login, R.mipmap.quit_login)
                    presetner.loginOrQuit()
                }
            }
        }

        if (event?.action == MotionEvent.ACTION_CANCEL) {//取消只还原颜色
            when (v?.id) {
                R.id.layout_about_us -> {
                    changeLeftMenuColor(v, R.id.about_us_tv, R.color.left_menu_text_color, R.id.iv_about_us, R.mipmap.about_us)
                }
                R.id.layout_contact_us -> {
                    changeLeftMenuColor(v, R.id.tv_contact_us, R.color.left_menu_text_color, R.id.iv_contact_us, R.mipmap.contact_us)
                }
                R.id.layout_quit_login -> {
                    changeLeftMenuColor(v, R.id.tv_quit_login, R.color.left_menu_text_color, R.id.iv_quit_login, R.mipmap.quit_login)
                }
            }
        }


        true
    }

    fun changeLeftMenuColor(v: View, tvId: Int, colorId: Int, ivId: Int, ivSrcId: Int) {
        v.find<TextView>(tvId).setTextColor(resources.getColor(colorId))
        v.find<ImageView>(ivId).setImageResource(ivSrcId)

    }

    /**
     * 检测是否登录
     */
    fun checkIsLogin() {
        presetner.getUserInfo()
    }

    fun initFab() {
        //根据当前设置设置menu的文字提示
        val isFastMode: Boolean = SPUtils.get(this, SPUtils.FAST_MODE_KEY, true) as Boolean//默认是节流模式
        if (isFastMode) {
            mFabModelSwitch.setImageResource(R.mipmap.mode_ds)
        } else {
            mFabModelSwitch.setImageResource(R.mipmap.mode_money)
        }

        mFabModelSwitch.onClick { presetner.toggleMode() }
    }

    override fun updateFabUi(it: ListScrollEvent) {
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
        showSet!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
//                mFabModelSwitch.onClick { presetner.toggleMode() }
            }

            override fun onAnimationCancel(animation: Animator?) {
//                mFabModelSwitch.onClick { presetner.toggleMode() }
            }

            override fun onAnimationStart(animation: Animator?) {
                mFabModelSwitch.visibility = View.VISIBLE
            }

        })
        showSet!!.start()
    }

    var hideSet: AnimatorSet? = null


    fun hideFab() {
        if (hideSet != null && hideSet!!.isRunning) {
            return
        }

        val hideAnimX: ObjectAnimator = ObjectAnimator.ofFloat(mFabModelSwitch, "scaleX", 1f, 0f)
        val hideAnimY: ObjectAnimator = ObjectAnimator.ofFloat(mFabModelSwitch, "scaleY", 1f, 0f)
//        hideAnimX.setAutoCancel(true)
//        hideAnimY.setAutoCancel(true)

        hideSet = AnimatorSet()
        hideSet!!.duration = 200
        hideSet!!.interpolator = DecelerateInterpolator()
        hideSet!!.playTogether(hideAnimX, hideAnimY)
        hideSet!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                mFabModelSwitch.visibility = View.GONE
//                mFabModelSwitch.setOnClickListener(null)

            }

            override fun onAnimationCancel(animation: Animator?) {
//                mFabModelSwitch.setOnClickListener(null)
                mFabModelSwitch.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animator?) {
                mFabModelSwitch.visibility = View.VISIBLE
            }

        })
        hideSet!!.start()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        subscriptions.clear()
        super.onDestroy()
    }

    val menuOnClickListener = View.OnClickListener {
        when (it.id) {
            R.id.menu_movie -> showMoviePage()
            R.id.menu_spoof -> showSpoofPage()
        }
    }

    fun contactUs() {
        //显示联系方式页面，目前是跳转关于页面
    }

    /**
     * 跳转关于页面
     */
    fun showAboutPage() {
        showToast("关于页面还在完善中~~")
        //调用方法，Activity页面写一个launch的静态方法，传入context就可以跳转了，intent请在launcher方法中构建
        AboutActivity.launch(this)
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


    val PERMISSION_REQEUST_CODE: Int = 123

    //----------------------------------presneter层操作接口--------------
    override fun updateUserInfo(userInfo: AccountInfo?) {
        if (null == userInfo) {
            //表示退出登录，这里视图效果还原
            mUserPhoto.setImageURI(null, null)
            btnLogin.find<TextView>(R.id.tv_quit_login).text = getText(R.string.menu_login)
            mUserName.text = getText(R.string.menu_no_login)
            return
        }

        //登录成功,更新显示用户信息
        if (TextUtils.isEmpty(userInfo.nickName)) {
            mUserName.text = userInfo.username
        } else {
            mUserName.text = userInfo.nickName
        }

        if (!TextUtils.isEmpty(userInfo.photoUrl)) {
            mUserPhoto.setImageURI(Uri.parse(userInfo.photoUrl), null)
        }

        btnLogin.find<TextView>(R.id.tv_quit_login).text = getText(R.string.menu_quit_login)
    }

    override fun updateModeSwitchUI(imageId: Int, hintStringId: Int) {
        mFabModelSwitch.setImageResource(imageId)
        showToast(resources.getString(hintStringId))
    }

    override fun showMsgHint(msg: String) {
        showToast(msg)
    }

}