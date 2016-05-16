package com.ohdroid.zbmaster.login.data

import android.content.Context
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.SaveListener
import com.ohdroid.zbmaster.application.di.exannotation.ForApplication
import com.ohdroid.zbmaster.application.di.exannotation.PerActivity
import com.ohdroid.zbmaster.login.data.bmob.BmobLoginManager
import com.ohdroid.zbmaster.login.model.AccountInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ohdroid on 2016/3/14.
 */
@Singleton
class LoginManager : AccountManager {

    lateinit var context: Context
    var accountManager: AccountManager? = null

    @Inject
    constructor(@ForApplication context: Context) {
        this.context = context
        accountManager = BmobLoginManager(context)
    }


    override fun login(accountInfo: AccountInfo, saveListener: SaveListener) {
        accountManager?.login(accountInfo, saveListener)
    }

    override fun exit() {
        accountManager?.exit()
    }

    override fun regist(accountInfo: AccountInfo, registerListener: LoginListener) {
        accountManager?.regist(accountInfo, registerListener)
    }

    override fun getUserAccount(): AccountInfo? {
        return accountManager?.getUserAccount()
    }


    interface LoginListener {
        fun onSuccess()
        fun onFailed(state: Int, msg: String)
    }

}