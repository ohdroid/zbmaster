package com.ohdroid.zbmaster.login.data.bmob

import android.content.Context
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.SaveListener
import com.ohdroid.zbmaster.application.di.exannotation.ForApplication
import com.ohdroid.zbmaster.login.data.AccountManager
import com.ohdroid.zbmaster.login.data.LoginManager
import com.ohdroid.zbmaster.login.model.AccountInfo
import javax.inject.Inject


/**
 * Created by ohdroid on 2016/3/14.
 */
class BmobLoginManager constructor(val context: Context) : AccountManager {

    var userInfo: AccountInfo? = null

    override fun regist(accountInfo: AccountInfo, registerListener: LoginManager.LoginListener) {
        accountInfo.signUp(context, object : SaveListener() {
            override fun onFailure(p0: Int, p1: String?) {
                userInfo = null
                registerListener.onFailed(p0, p1 ?: "")
            }

            override fun onSuccess() {
                userInfo = accountInfo
                registerListener.onSuccess()
            }

        })
    }

    override fun login(accountInfo: AccountInfo, saveListener: SaveListener) {
        accountInfo.login(context, object : SaveListener() {
            override fun onSuccess() {
                userInfo = accountInfo
                saveListener.onSuccess()
            }

            override fun onFailure(p0: Int, p1: String?) {
                userInfo = null
                saveListener.onFailure(p0, p1)
            }

        })
    }

    override fun exit() {
        BmobUser.logOut(context)
    }


    override fun getUserAccount(): AccountInfo? {
        return BmobUser.getCurrentUser(context, AccountInfo::class.java)
    }


}