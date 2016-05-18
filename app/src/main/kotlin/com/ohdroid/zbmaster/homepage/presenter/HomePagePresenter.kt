package com.ohdroid.zbmaster.homepage.presenter

import android.app.Activity
import android.content.Context
import android.os.Build
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.BasePresenter
import com.ohdroid.zbmaster.application.data.DataManager
import com.ohdroid.zbmaster.application.di.exannotation.PerActivity
import com.ohdroid.zbmaster.application.rxbus.RxBus
import com.ohdroid.zbmaster.homepage.areamovie.event.ListScrollEvent
import com.ohdroid.zbmaster.homepage.view.HomePageView
import com.ohdroid.zbmaster.login.event.UserInfoUpdateEvent
import com.ohdroid.zbmaster.login.view.LoginActivity
import com.ohdroid.zbmaster.utils.SPUtils
import com.ohdroid.zbmaster.utils.SystemUtils
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by ohdroid on 2016/5/16.
 */
@PerActivity
class HomePagePresenter : BasePresenter<HomePageView> {
    lateinit var activity: Activity
    lateinit var rxBus: RxBus
    lateinit var dataManager: DataManager
    val subscriptions: CompositeSubscription = CompositeSubscription()

    @Inject
    constructor(@PerActivity activity: Activity, dataManager: DataManager, rxBus: RxBus) {
        this.activity = activity
        this.rxBus = rxBus
        this.dataManager = dataManager
    }

    lateinit var uiView: HomePageView
    override fun attachView(view: HomePageView) {
        if (Build.VERSION.SDK_INT >= 23) {
            SystemUtils.checkPermission(activity)
        }

        uiView = view

        //添加对event事件的监听
        subscriptions.add(rxBus.toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it is ListScrollEvent) {
                        handleListScrollEvent(it)
                    }

                    if (it is UserInfoUpdateEvent) {
                        handleUserinfoUpdateEvent()
                    }
                }))
    }

    /**
     * 更新用户显示信息
     */
    fun handleUserinfoUpdateEvent() {
        uiView.updateUserInfo(dataManager.loginManger.getUserAccount())
    }

    private fun handleListScrollEvent(it: ListScrollEvent) {
        //滚动事件
        uiView.updateFabUi(it)
    }

    override fun detachView() {
        //解除RxBus的绑定
    }

    /**
     * 获取用户信息，然后更新显示
     */
    fun getUserInfo() {
        val userAccount = dataManager.loginManger.getUserAccount()
        uiView.updateUserInfo(userAccount)
    }

    /**
     * gif图加载模式切换，
     */
    fun toggleMode() {

        val isFastMode: Boolean = SPUtils.get(activity, SPUtils.FAST_MODE_KEY, true) as Boolean//默认是节流模式
        if (isFastMode) {
            //切换到装逼模式
            SPUtils.put(activity, SPUtils.FAST_MODE_KEY, false)//设置为装逼模式，也就是加载高清图模式
            uiView.updateModeSwitchUI(R.mipmap.mode_money, R.string.hint_quality_mode)
        } else {
            SPUtils.put(activity, SPUtils.FAST_MODE_KEY, true)//设置为节流模式
            uiView.updateModeSwitchUI(R.mipmap.mode_ds, R.string.hint_fast_mode)
        }
    }

    /**
     * 登录或者退出登录
     */
    fun loginOrQuit() {

        //如果目前已经登录那么退出登录
        if (null != dataManager.loginManger.getUserAccount()) {
            dataManager.loginManger.exit()
            uiView.updateUserInfo(null)
            uiView.showMsgHint("已退出登录")
        } else {
            uiView.showMsgHint("小弟正在找登陆页面，骚等~")
            LoginActivity.launch(activity)
        }
    }

}