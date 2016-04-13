package com.ohdroid.zbmaster.login.data

import cn.bmob.v3.listener.SaveListener
import com.ohdroid.zbmaster.login.model.AccountInfo

/**
 * Created by ohdroid on 2016/3/14.
 *
 * 用户对象管理接口，第三方或自己管理需要实现的接口，方便移植
 */
interface AccountManager {
    /**
     * 登录
     */
    fun login(accountInfo: AccountInfo, saveListener: SaveListener)

    /**
     * 注册
     */
    fun regist(accountInfo: AccountInfo, registerListener: LoginManager.LoginListener)

    /**
     * 获取账号信息
     */
    fun getUserAccount(): AccountInfo?
}