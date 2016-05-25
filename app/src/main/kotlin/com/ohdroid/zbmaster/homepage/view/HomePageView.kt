package com.ohdroid.zbmaster.homepage.view

import com.ohdroid.zbmaster.application.BaseView
import com.ohdroid.zbmaster.homepage.areamovie.event.ListScrollEvent
import com.ohdroid.zbmaster.login.model.AccountInfo

/**
 * Created by ohdroid on 2016/5/16.
 */
interface HomePageView : BaseView {

    /**
     * 切换浏览模式
     */
    fun updateModeSwitchUI(imageId: Int, hintStringId: Int)

    /**
     * 更新显示用户信息
     */
    fun updateUserInfo(userInfo: AccountInfo?)

    /**
     * 更新显示FAB按钮显示
     */
    fun updateFabUi(it: ListScrollEvent)

    /**
     * 显示提示
     */
    fun showMsgHint(msg: String)

    /**
     * 显示退出登录确认对话框
     */
    fun showQuitLoginConfirmDialog()
}